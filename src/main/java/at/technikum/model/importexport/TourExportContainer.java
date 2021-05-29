package at.technikum.model.importexport;

import at.technikum.model.logs.Log;
import at.technikum.model.tours.Tour;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class TourExportContainer {

    public Tour tour;

    public List<Log> logs;

}
