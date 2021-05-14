package at.technikum.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tour {
    private long id;
    private String name;
    private String start;
    private String destination;
    private int distance;
    private String description;
    //private List<Object> logs;
}
