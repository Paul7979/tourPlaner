package at.technikum.model.tours;

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
    private String mapPath;
    //private List<Object> logs;

    public String getFullTextSearchString() {
        return (name + description + distance + destination + start).toLowerCase();
    }
}
