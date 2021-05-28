package at.technikum.model.tours;

import at.technikum.util.SQLConnectionProvider;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class PersistentTourDAO implements TourDAO {

    private static final String CREATE_TOUR = """
            INSERT into tour(name, start, destination, distance, description, path_to_picture) 
            VALUES (?,?,?,?,?,?)""";

    private static final String GET_TOUR_BY_ID = """
            SELECT id, name, start, destination, distance, description, path_to_picture 
            from tour where id = ?""";
    private static final String REMOVE_TOUR_BY_ID = """
            DELETE from tour where id = ?
            """;
    private static final String GET_ALL_TOURS = """
            SELECT id, name, start, destination, distance, description, path_to_picture 
            from tour""";

    private static final String FILTER_FOR_TERM = """
            WHERE name like ? or start like ? or destination like ? or description like ?""";

    @Override
    public Tour add(Tour tour) {
        var connection = SQLConnectionProvider.getConnection();
        try {
            var preparedStatement = connection.prepareStatement(CREATE_TOUR);
            preparedStatement.setString(1, tour.getName());
            preparedStatement.setString(2, tour.getStart());
            preparedStatement.setString(3, tour.getDestination());
            preparedStatement.setInt(4, tour.getDistance());
            preparedStatement.setString(5, tour.getDescription());
            preparedStatement.setString(6, tour.getMapPath());
            log.info("Adding tour {}", preparedStatement);
            var affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating failed, no rows affected");
            }
            //TODO:WHY NO ID?
            /*ResultSet generated = preparedStatement.getGeneratedKeys();
            if (generated.next()) {
                var id = generated.getInt(1);
                log.info("obtained id {}", id);
                tour.setId(id);
                SQLConnectionProvider.releaseConnection(connection);
                return tour;
            } else {
                throw new SQLException("Creating tour failed, no ID obtained.");
            }*/

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            SQLConnectionProvider.releaseConnection(connection);
        }
        SQLConnectionProvider.releaseConnection(connection);
        return null;
    }

    @Override
    public Optional<Tour> getTour(long id) {
        var connection = SQLConnectionProvider.getConnection();
        try {
            var preparedStatement = connection.prepareStatement(GET_TOUR_BY_ID);
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                var tour = tourFromDatabase(resultSet);
                SQLConnectionProvider.releaseConnection(connection);
                return Optional.ofNullable(tour);
            }
        } catch (SQLException throwables) {
            SQLConnectionProvider.releaseConnection(connection);
            log.error("Could not get Tour", throwables);
        }
        SQLConnectionProvider.releaseConnection(connection);
        return Optional.empty();
    }

    @Override
    public List<Tour> getAll() {
        var connection = SQLConnectionProvider.getConnection();
        try (var preparedStatement = connection.prepareStatement(GET_ALL_TOURS);
        ) {
            var resultSet = preparedStatement.executeQuery();
            List<Tour> tours = new ArrayList<>();
            while (resultSet.next()) {
                tours.add(tourFromDatabase(resultSet));
            }
            SQLConnectionProvider.releaseConnection(connection);
            return tours;
        } catch (SQLException throwables) {
            SQLConnectionProvider.releaseConnection(connection);
            log.error("Could not get all tours", throwables);
        }
        SQLConnectionProvider.releaseConnection(connection);
        return new ArrayList<>();
    }

    @Override
    public List<Tour> searchFor(String term) {
        List<Tour> tours = new ArrayList<>();
        var connection = SQLConnectionProvider.getConnection();
        try (var preparedStatement = connection.prepareStatement(GET_ALL_TOURS + " " + FILTER_FOR_TERM)) {
            setTerms(preparedStatement, term);
            log.debug("Search Query {}", preparedStatement);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tours.add(tourFromDatabase(resultSet));
            }
        } catch (SQLException throwables) {
            log.error("Error during full text search", throwables);
        }
        SQLConnectionProvider.releaseConnection(connection);
        return tours;
    }

    private void setTerms(PreparedStatement preparedStatement, String term) throws SQLException {
        for (int i = 1; i < 5; i++) {
            preparedStatement.setString(i, "%" + term + "%");
        }
    }

    @Override
    public void remove(Tour tour) {
        var connection = SQLConnectionProvider.getConnection();
        try {
            var preparedStatement = connection.prepareStatement(REMOVE_TOUR_BY_ID);
            preparedStatement.setLong(1, tour.getId());
            var affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Delete failed, no rows affected");
            }
        } catch (SQLException throwables) {
            log.error("Error deleting tour", throwables);
            SQLConnectionProvider.releaseConnection(connection);
        }
        SQLConnectionProvider.releaseConnection(connection);
    }

    private Tour tourFromDatabase(ResultSet resultSet) {
        try {
            return Tour.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .distance((int) resultSet.getLong("distance"))
                    .description(resultSet.getString("description"))
                    .start(resultSet.getString("start"))
                    .destination(resultSet.getString("destination"))
                    .mapPath(resultSet.getString("path_to_picture")).build();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }
}
