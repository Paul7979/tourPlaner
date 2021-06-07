package at.technikum.model.report;

import at.technikum.dal.logs.LogDAO;
import at.technikum.dal.tours.TourDAO;
import at.technikum.model.logs.Log;
import at.technikum.model.tours.Tour;
import com.itextpdf.text.DocumentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ReportServiceTest {

    @Mock
    TourDAO tourDAO = Mockito.mock(TourDAO.class);

    @Mock
    LogDAO logDAO = Mockito.mock(LogDAO.class);

    ReportService reportService;

    String dest = "./test.pdf";

    @Test
    void givenReportServiceWhenGeneratingFullReportThenPDFIsGenerated() throws IOException, DocumentException {
        reportService = new ReportService(tourDAO, logDAO);

        File file = new File(dest);
        var newFile = file.createNewFile();
        List<Tour> tours = createTours();
        when(tourDAO.getAll()).thenReturn(tours);
        when(logDAO.getAvgDifficultyFor(any())).thenReturn(2);
        when(logDAO.getTotalDistanceFor(any())).thenReturn(2);
        when(logDAO.getAvgDifficultyFor(any())).thenReturn(2);
        reportService.generateFullReport(dest);
        assertThat(file.getTotalSpace()).isPositive();
        assertThat(newFile).isTrue();
    }

    @Test
    void givenReportServiceWhenGeneratingFullReportThenNothingIsFetched() throws IOException, DocumentException {
        reportService = new ReportService(tourDAO, logDAO);

        File file = new File(dest);
        var newFile = file.createNewFile();
        List<Tour> tours = createTours();
        when(tourDAO.getAll()).thenReturn(tours);
        when(logDAO.getAvgDifficultyFor(any())).thenReturn(2);
        when(logDAO.getTotalDistanceFor(any())).thenReturn(2);
        when(logDAO.getAvgDifficultyFor(any())).thenReturn(2);
        reportService.generateFullReport(dest);
        verify(logDAO, times(0)).getAvgDifficultyFor(any());
        verify(logDAO, times(0)).getTotalDistanceFor(any());
        verify(logDAO, times(0)).getAvgRatingFor(any());
    }

    @Test
    void givenReportServiceWhenGeneratingFullReportThenLogsFetched() throws IOException, DocumentException {
        reportService = new ReportService(tourDAO, logDAO);

        File file = new File(dest);
        var newFile = file.createNewFile();
        List<Tour> tours = createTours();
        when(tourDAO.getAll()).thenReturn(tours);
        when(logDAO.getAvgDifficultyFor(any())).thenReturn(2);
        when(logDAO.getTotalDistanceFor(any())).thenReturn(2);
        when(logDAO.getAvgDifficultyFor(any())).thenReturn(2);
        when(logDAO.getLogsFor(any())).thenReturn(Optional.of(createLogs()));
        reportService.generateFullReport(dest);
        verify(logDAO, times(tours.size())).getAvgDifficultyFor(any());
        verify(logDAO, times(tours.size())).getTotalDistanceFor(any());
        verify(logDAO, times(tours.size())).getAvgRatingFor(any());
    }

    @Test
    void givenReportServiceWhenGeneratingFullReportThenLogsFetchedWhenAvailable() throws IOException, DocumentException {
        reportService = new ReportService(tourDAO, logDAO);

        File file = new File(dest);
        var newFile = file.createNewFile();
        List<Tour> tours = createTours();
        when(tourDAO.getAll()).thenReturn(tours);
        when(logDAO.getAvgDifficultyFor(any())).thenReturn(2);
        when(logDAO.getTotalDistanceFor(any())).thenReturn(2);
        when(logDAO.getAvgDifficultyFor(any())).thenReturn(2);
        when(logDAO.getLogsFor(eq(tours.get(1)))).thenReturn(Optional.of(createLogs()));
        reportService.generateFullReport(dest);
        verify(logDAO, times( 1)).getAvgDifficultyFor(any());
        verify(logDAO, times(1)).getTotalDistanceFor(any());
        verify(logDAO, times(1)).getAvgRatingFor(any());
    }

    @Test
    void givenReportServiceWhenGeneratingFullReportThenToursAreFetched() throws IOException, DocumentException {
        reportService = new ReportService(tourDAO, logDAO);

        File file = new File(dest);
        var newFile = file.createNewFile();
        List<Tour> tours = createTours();
        when(tourDAO.getAll()).thenReturn(tours);
        when(logDAO.getAvgDifficultyFor(any())).thenReturn(2);
        when(logDAO.getTotalDistanceFor(any())).thenReturn(2);
        when(logDAO.getAvgDifficultyFor(any())).thenReturn(2);
        reportService.generateFullReport(dest);
        verify(tourDAO, times(1)).getAll();
    }

    @Test
    void givenReportServiceWhenGeneratingSingleReportThenPDFIsGenerated() throws IOException, DocumentException {
        reportService = new ReportService(tourDAO, logDAO);

        File file = new File(dest);
        Tour tour = Tour.builder()
                .id(2)
                .mapPath("Test")
                .start("Start").build();
        var newFile = file.createNewFile();
        List<Log> logs = createLogs();
        when(logDAO.getLogsFor(eq(tour))).thenReturn(Optional.of(logs));
        reportService.generateSingleReport(tour, dest);
        assertThat(file.getTotalSpace()).isPositive();
        assertThat(newFile).isTrue();
    }

    @Test
    void givenReportServiceWhenGeneratingSingleReportThenLogsAreFetched() throws IOException, DocumentException {
        reportService = new ReportService(tourDAO, logDAO);

        File file = new File(dest);
        Tour tour = Tour.builder()
                .id(2)
                .mapPath("Test")
                .start("Start").build();
        file.createNewFile();
        List<Log> logs = createLogs();
        when(logDAO.getLogsFor(eq(tour))).thenReturn(Optional.of(logs));
        reportService.generateSingleReport(tour, dest);
        verify(logDAO, times(1)).getLogsFor(eq(tour));
    }


    @AfterEach
    void clean() {
        File file = new File(dest);
        file.delete();
    }

    private List<Log> createLogs() {
        return List.of(
                Log.builder()
                        .date(LocalDate.now())
                        .averageSpeed("test")
                        .difficulty("diff")
                        .distance("10")
                        .report("rep")
                        .recommendedPeopleCount(1)
                        .typeOfTransport("car")
                        .totalTime("10")
                        .rating(4)
                        .build(),
                Log.builder()
                        .date(LocalDate.now())
                        .averageSpeed("test")
                        .difficulty("diff")
                        .distance("10")
                        .report("rep")
                        .recommendedPeopleCount(1)
                        .typeOfTransport("car")
                        .totalTime("10")
                        .rating(4)
                        .build(),
                Log.builder()
                        .date(LocalDate.now())
                        .averageSpeed("test")
                        .difficulty("diff")
                        .distance("10")
                        .report("rep")
                        .recommendedPeopleCount(1)
                        .typeOfTransport("car")
                        .totalTime("10")
                        .rating(4)
                        .build()
        );
    }

    private List<Tour> createTours() {
        return List.of(
                Tour.builder()
                        .destination("Dest")
                        .start("start")
                        .name("Testname")
                        .mapPath(UUID.randomUUID().toString())
                        .build(),
                Tour.builder()
                        .destination("Dest1")
                        .start("start1")
                        .name("Testname1")
                        .mapPath(UUID.randomUUID().toString())
                        .build(),
                Tour.builder()
                        .destination("Dest2")
                        .start("start2")
                        .name("Testname2")
                        .mapPath(UUID.randomUUID().toString())
                        .build()
        );
    }

}