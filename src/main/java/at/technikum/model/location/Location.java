package at.technikum.model.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Location {

    private String displayName;

    private GeoLocation geoLocation;

}
