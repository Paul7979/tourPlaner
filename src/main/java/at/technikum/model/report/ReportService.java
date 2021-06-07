package at.technikum.model.report;

import at.technikum.dal.logs.LogDAO;
import at.technikum.dal.tours.TourDAO;
import at.technikum.model.tours.Tour;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Slf4j
public class ReportService {

    private final Font HEADER_FONT = new Font(Font.FontFamily.COURIER, 15, Font.BOLD);
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MM yyyy");
    private final TourDAO tourDAO;
    private final LogDAO logDAO;

    public void generateFullReport(String dest) throws FileNotFoundException, DocumentException {
        var document = new Document();
        var pdfWriter = PdfWriter.getInstance(document , new FileOutputStream(dest));
        document.open();
        document.add(new Paragraph("Total Report", HEADER_FONT));
        document.add(new Paragraph("Reports: ", HEADER_FONT));
        AtomicInteger counter = new AtomicInteger();

        Paragraph tours = new Paragraph();
        tourDAO.getAll().forEach(tour -> {
            counter.getAndIncrement();
            Paragraph tourParagraph = new Paragraph(counter.get() + ". " + tour.getName() + "\n");
            tourParagraph.setIndentationLeft(10);
            tourParagraph.add("From: " + tour.getStart() + "\n");
            tourParagraph.add("To: " + tour.getDestination() +"\n");
            tourParagraph.add("Distance : " + tour.getDistance() + "km\n");
            tourParagraph.add(addImage(tour));
            tourParagraph.add("\n");
            if (logDAO.getLogsFor(tour).isPresent()) {
                tourParagraph.add("Summary of logs: \n");
                tourParagraph.add("Total distance: " + logDAO.getTotalDistanceFor(tour)+ "km\n");
                tourParagraph.add("Average Rating: " + logDAO.getAvgRatingFor(tour)+ "\n");
                tourParagraph.add("Average Difficulty: " + logDAO.getAvgDifficultyFor(tour) +"\n");
            } else {
                tourParagraph.add("No logs added for this tour yet. \n");
            }
            log.info("Added {}", tour);
            tours.add(tourParagraph);
            if (counter.get() % 2 == 0) {
                tours.add(Chunk.NEXTPAGE);
            }
            //tours.setKeepTogether(true);
        });

        document.add(tours);
        document.close();
    }

    public void generateSingleReport(Tour tour, String dest) throws FileNotFoundException, DocumentException {
        var document = new Document();
        var pdfWriter = PdfWriter.getInstance(document , new FileOutputStream(dest));
        document.open();
        document.add(new Paragraph("Single Report", HEADER_FONT));
        Paragraph tourParagraph = new Paragraph("Tour: "  + tour.getName() + "\n");
        tourParagraph.add("From: " + tour.getStart() + "\n");
        tourParagraph.add("To: " + tour.getDestination() +"\n");
        tourParagraph.add("Distance : " + tour.getDistance() + "km\n");
        tourParagraph.add(addImage(tour));
        tourParagraph.add("Logs: \n");
        Paragraph logParagraph = new Paragraph();
        logParagraph.setIndentationLeft(10);
        AtomicInteger counter = new AtomicInteger(0);
        logDAO.getLogsFor(tour).ifPresent(logs -> {
            logs.forEach(singleLog -> {
                counter.getAndIncrement();
                logParagraph.add( counter.get() + " | " + singleLog.getDate().format(dateTimeFormatter));
                Paragraph logDetailsParagraph = new Paragraph();
                logDetailsParagraph.setIndentationLeft(25);
                logDetailsParagraph.add("speed: " + singleLog.getAverageSpeed() + "\n");
                logDetailsParagraph.add("report: " +  singleLog.getReport() + "\n");
                logDetailsParagraph.add("distance: " +  singleLog.getDistance() + "\n");
                logDetailsParagraph.add("totalTime: " +  singleLog.getTotalTime() + "\n");
                logDetailsParagraph.add("rating: " +  singleLog.getRating() + "\n");
                logDetailsParagraph.add("averageSpeed: " +  singleLog.getAverageSpeed() + "\n");
                logDetailsParagraph.add("typeOfTransport: " +  singleLog.getTypeOfTransport() + "\n");
                logDetailsParagraph.add("difficulty: " +  singleLog.getDifficulty() + "\n");
                logDetailsParagraph.add("recommendedPeopleCount: " +  singleLog.getRecommendedPeopleCount() + "\n");
                logParagraph.add(logDetailsParagraph);
                logParagraph.add("\n");
            });
        });
        tourParagraph.add(logParagraph);
        document.add(tourParagraph);
        document.close();
    }

    private Image addImage(Tour tour) {
        Image image = null;
        if (Files.exists(Path.of(tour.getMapPath()))) {
            try {
                image = Image.getInstance(tour.getMapPath());
            } catch (BadElementException | IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                image = Image.getInstance("src/main/resources/not_found.jpg");
            } catch (BadElementException | IOException e) {
                e.printStackTrace();
            }
        }

        assert image != null;
        image.scaleToFit(300, 600);
        return image;
    }

}
