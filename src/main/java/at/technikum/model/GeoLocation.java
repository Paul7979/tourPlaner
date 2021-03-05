package at.technikum.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GeoLocation {
    private Double longitude;
    private Double latitude;
}
