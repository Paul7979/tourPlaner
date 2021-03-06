package at.technikum.client.forwardsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

    private String id;
    private String displayString;
    private String recordType;

}
