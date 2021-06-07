package at.technikum.client.route;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class RouteSearchServiceTest {


    //@Test
    void getDistanceFromTo() throws IOException, InterruptedException {
        var instance = RouteSearchService.getInstance();
        var distanceFor = instance.getDistanceFor("Wien", "Linz");
        assertThat(distanceFor).isPositive();
    }

}