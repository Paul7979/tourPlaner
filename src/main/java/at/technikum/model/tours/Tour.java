package at.technikum.model.tours;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tour {
    @JsonIgnore
    private long id;
    private String name;
    private String start;
    private String destination;
    private int distance;
    private String description;
    private String mapPath;
    //private List<Object> logs;
}
