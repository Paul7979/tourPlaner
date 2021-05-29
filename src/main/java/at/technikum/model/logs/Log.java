package at.technikum.model.logs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    @JsonIgnore
    private Integer id;
    private String report;
    private String distance;
    private String totalTime;
    private Integer rating;
    private String averageSpeed;
    private String typeOfTransport;
    private String difficulty;
    private int recommendedPeopleCount;
    //private boolean toiletOnThePath;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;
}
