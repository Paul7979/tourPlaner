package at.technikum.client.route;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RouteSearchServiceTest {


    @Test
    void getDistanceFromTo() throws IOException, InterruptedException {
        var instance = RouteSearchService.getInstance();
        var distanceFor = instance.getDistanceFor("Wien", "Linz");
        assertThat(distanceFor).isPositive();
    }

}