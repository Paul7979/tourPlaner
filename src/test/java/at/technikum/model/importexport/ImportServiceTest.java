package at.technikum.model.importexport;

import at.technikum.client.mapsearch.MapSearchService;
import at.technikum.dal.logs.LogDAO;
import at.technikum.dal.tours.TourDAO;
import at.technikum.model.logs.Log;
import at.technikum.model.tours.Tour;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImportServiceTest {

    @Mock
    TourDAO tourDAO = Mockito.mock(TourDAO.class);

    @Mock
    LogDAO logDAO = Mockito.mock(LogDAO.class);

    @Mock
    MapSearchService mapSearchService = Mockito.mock(MapSearchService.class);

    ObjectMapper objectMapper = new ObjectMapper().configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);

    File fileMock;

    ImportService importService;

    @Captor
    ArgumentCaptor<Tour> tourCaptor;

    @Captor
    ArgumentCaptor<Log> logArgumentCaptor;

    @BeforeEach
    void setUp() throws IOException {
        objectMapper.findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));

        fileMock = new File("./" + UUID.randomUUID());
        var newFile = fileMock.createNewFile();
        importService = new ImportService(tourDAO, logDAO, mapSearchService);
    }

    @Test
    void givenFileWithDataWhenImportingThenDataIsStored() throws IOException {
        TourExportWrapper wrapper = new TourExportWrapper();
        var MAPPATH = "Invalid";
        Tour tour1 = Tour.builder()
                .start("")
                .destination("")
                .mapPath(MAPPATH)
                .build();
        Log log1 = Log.builder().build();
        wrapper.addContainer(new TourExportContainer(tour1, List.of(log1)));

        objectMapper.writeValue(fileMock, wrapper);

        when(mapSearchService.searchMapFullSizeBlocking(anyString(), anyString())).thenReturn(Optional.of(MAPPATH));

        importService.importFromFile(fileMock);


        verify(tourDAO).add(tourCaptor.capture());
        verify(logDAO, times(1)).addLogForTour(any(), eq(log1));
        assertThat(tourCaptor.getValue()).isEqualToComparingFieldByField(tour1);
    }

    @Test
    void givenFileWithInvalidMapPathsWhenImportingThenNewMapPathIsFetched() throws IOException {
        TourExportWrapper wrapper = new TourExportWrapper();
        Tour tour1 = Tour.builder()
                .start("")
                .destination("")
                .mapPath("Invalid")
                .build();
        Log log1 = Log.builder().build();
        wrapper.addContainer(new TourExportContainer(tour1, List.of(log1)));

        objectMapper.writeValue(fileMock, wrapper);

        String newMapPath = "NewMapPath";
        when(mapSearchService.searchMapFullSizeBlocking(anyString(), anyString())).thenReturn(Optional.of(newMapPath));

        importService.importFromFile(fileMock);


        verify(tourDAO).add(tourCaptor.capture());
        assertThat(tourCaptor.getValue().getMapPath()).isEqualTo(newMapPath);
    }


    @Test
    void givenFileWithInvalidMapPathWhenImportingThenNewMapPathForStartAndEndIsFetched() throws IOException {
        TourExportWrapper wrapper = new TourExportWrapper();

        var start = "START";
        var destination = "DESTINATION";
        Tour tour1 = Tour.builder()
                .start(start)
                .destination(destination)
                .mapPath("Invalid")
                .build();
        Log log1 = Log.builder().build();
        wrapper.addContainer(new TourExportContainer(tour1, List.of(log1)));

        objectMapper.writeValue(fileMock, wrapper);

        String newMapPath = "NewMapPath";
        when(mapSearchService.searchMapFullSizeBlocking(anyString(), anyString())).thenReturn(Optional.of(newMapPath));

        importService.importFromFile(fileMock);

        verify(mapSearchService, times(1)).searchMapFullSizeBlocking(start,destination);
    }

    @AfterEach
    void tearDown() {
        fileMock.delete();
    }

}