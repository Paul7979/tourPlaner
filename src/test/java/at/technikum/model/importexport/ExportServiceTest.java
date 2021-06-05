package at.technikum.model.importexport;

import at.technikum.dal.logs.LogDAO;
import at.technikum.dal.tours.TourDAO;
import at.technikum.model.logs.Log;
import at.technikum.model.tours.Tour;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExportServiceTest {

    @Mock
    TourDAO tourDAO = Mockito.mock(TourDAO.class);

    @Mock
    LogDAO logDAO = Mockito.mock(LogDAO.class);

    File fileMock;

    ExportService exportService;


    @BeforeEach
    void setUp() throws IOException {
        fileMock = new File("./" + UUID.randomUUID().toString());
        var newFile = fileMock.createNewFile();
        exportService = new ExportService(logDAO, tourDAO);
    }

    @AfterEach
    void tearDown() {
        fileMock.delete();
    }

    @Test
    void givenExportServiceWhenExportToFileThenAllDataIsFetched() throws IOException {
        List<Tour> tours = new ArrayList<>();
        tours.add(Tour.builder().build());
        when(tourDAO.getAll()).thenReturn(tours);


        List<Log> logs = new ArrayList<>();
        logs.add(Log.builder().build());
        when(logDAO.getLogsFor(any())).thenReturn(Optional.of(logs));

        exportService.exportToFile(fileMock);

        verify(tourDAO, times(1)).getAll();
        verify(logDAO, times(1)).getLogsFor(eq(tours.get(0)));
    }

    @Test
    void givenExportServiceWhenExportToFileFileIsNotEmpty() throws IOException {
        List<Tour> tours = new ArrayList<>();
        tours.add(Tour.builder().build());
        when(tourDAO.getAll()).thenReturn(tours);


        List<Log> logs = new ArrayList<>();
        logs.add(Log.builder().build());
        when(logDAO.getLogsFor(any())).thenReturn(Optional.of(logs));

        exportService.exportToFile(fileMock);

        assertThat(Files.readAllLines(Path.of(fileMock.getPath()))).isNotEmpty();
    }

    @Test
    void givenExportingToFileWhenNoToursFoundThenNoLogsAreFetched() throws IOException {
        List<Tour> tours = new ArrayList<>();
        when(tourDAO.getAll()).thenReturn(tours);

        exportService.exportToFile(fileMock);

        verify(logDAO, times(0)).getLogsFor(any());
    }

    @Test
    void givenExportingToFileWhen10ToursFoundThenLogsFor10AreFetched() throws IOException {
        List<Tour> tours = new ArrayList<>();
        fillTours(tours);
        when(tourDAO.getAll()).thenReturn(tours);
        when(logDAO.getLogsFor(any())).thenReturn(Optional.of(List.of(Log.builder().build())));

        exportService.exportToFile(fileMock);

        verify(logDAO, times(10)).getLogsFor(any());
    }

    private void fillTours(List<Tour> tours) {
        for (int i = 0; i < 10; i++) {
            tours.add(Tour.builder().build());
        }
    }


}