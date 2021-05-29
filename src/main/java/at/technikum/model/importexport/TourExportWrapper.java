package at.technikum.model.importexport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourExportWrapper {

    private List<TourExportContainer> containers = new ArrayList<>();

    public void addContainer(TourExportContainer container) {
        containers.add(container);
    }

}
