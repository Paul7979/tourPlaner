package at.technikum.model.logs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Log {

    private Integer id;
    private String report;
    private String distance;
    private String totalTime;
    private Integer rating;
    private String averageSpeed;
    private String typeOfTransport;
    private String difficulty;
    private int recommendedPeopleCount;
    private boolean toiletOnThePath;
}
