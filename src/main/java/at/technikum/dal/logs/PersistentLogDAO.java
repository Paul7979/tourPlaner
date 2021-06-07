package at.technikum.dal.logs;

import at.technikum.model.logs.Log;
import at.technikum.model.tours.Tour;
import at.technikum.util.SQLConnectionProvider;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class PersistentLogDAO implements LogDAO {

    private static final String INSERT_LOG_FOR_TOUR = """
            INSERT into logs (tour_id, report, distance, totaltime, rating, averagespeed, typeoftransport, difficulty, recommendedpeoplecount, date) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";

    private static final String GET_TOTAL_DISTANCE_FOR_TOUR = """
            SELECT sum(distance::integer) from logs where tour_id = ?""";

    private static final String GET_AVG_RATING_FOR_TOUR = """
            SELECT avg(rating) from logs where tour_id = ?""";

    private static final String GET_AVG_DIFFICULTY_FOR_TOUR = """
            SELECT avg(difficulty::integer) from logs where tour_id = ?""";

    private static final String UPDATE_LOG = """
            UPDATE logs SET report = ?, distance = ?, totaltime = ?,  rating = ?, averagespeed = ?, typeoftransport = ?, difficulty = ?, recommendedpeoplecount = ?, date = ? 
            WHERE id = ?;""";

    private static final String GET_LOGS_FOR_TOUR = """
            SELECT id, tour_id, report, distance, totaltime, rating, averagespeed, typeoftransport, difficulty, recommendedpeoplecount, date
            from logs where tour_id = ? ORDER BY date""";

    private static final String DELETE_LOG = """
            DELETE from logs where id = ?""";

    @Override
    public void addLogForTour(Tour tour, Log log_in) {
        var connection = SQLConnectionProvider.getConnection();
        try {
            var preparedStatement = connection.prepareStatement(INSERT_LOG_FOR_TOUR);
            setValues(log_in, tour, preparedStatement);
            var i = preparedStatement.executeUpdate();
            if (i == 0) {
                throw new SQLException("No Rows affected");
            }
        } catch (SQLException throwables) {
            log.error("Error creating log for tour", throwables);
        }
        SQLConnectionProvider.releaseConnection(connection);
    }

    @Override
    public int getAvgRatingFor(Tour tour) {
        var connection = SQLConnectionProvider.getConnection();
        try {
            var preparedStatement = connection.prepareStatement(GET_AVG_RATING_FOR_TOUR);
            preparedStatement.setLong(1, tour.getId());
            var resultSet = preparedStatement.executeQuery();
            int sum = 0;
            if (resultSet.next()) {
                sum = resultSet.getInt(0);
            } else {
                sum = 0;
            }
            SQLConnectionProvider.releaseConnection(connection);
            return sum;
        } catch (SQLException throwables) {
            log.error("Error creating log for tour", throwables);
            SQLConnectionProvider.releaseConnection(connection);
        }
        return 0;
    }

    @Override
    public int getAvgDifficultyFor(Tour tour) {
        var connection = SQLConnectionProvider.getConnection();
        try {
            var preparedStatement = connection.prepareStatement(GET_AVG_DIFFICULTY_FOR_TOUR);
            preparedStatement.setLong(1, tour.getId());
            var resultSet = preparedStatement.executeQuery();
            int sum = 0;
            if (resultSet.next()) {
                sum = resultSet.getInt(0);
            } else {
                sum = 0;
            }
            SQLConnectionProvider.releaseConnection(connection);
            return sum;
        } catch (SQLException throwables) {
            log.error("Error creating log for tour", throwables);
            SQLConnectionProvider.releaseConnection(connection);
        }
        return 0;
    }

    @Override
    public int getTotalDistanceFor(Tour tour) {
        var connection = SQLConnectionProvider.getConnection();
        try {
            var preparedStatement = connection.prepareStatement(GET_TOTAL_DISTANCE_FOR_TOUR);
            preparedStatement.setLong(1, tour.getId());
            var resultSet = preparedStatement.executeQuery();
            int sum = 0;
            if (resultSet.next()) {
                sum = resultSet.getInt(0);
            } else {
                sum = 0;
            }
            SQLConnectionProvider.releaseConnection(connection);
            return sum;
        } catch (SQLException throwables) {
            log.error("Error creating log for tour", throwables);
            SQLConnectionProvider.releaseConnection(connection);
        }
        return 0;
    }



    private void setValues(Log log_in, Tour tour, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, (int) tour.getId());
        preparedStatement.setString(2, log_in.getReport());
        preparedStatement.setString(3, log_in.getDistance());
        preparedStatement.setString(4, log_in.getTotalTime());
        preparedStatement.setInt(5, log_in.getRating());
        preparedStatement.setString(6, log_in.getAverageSpeed());
        preparedStatement.setString(7, log_in.getTypeOfTransport());
        preparedStatement.setString(8, log_in.getDifficulty());
        preparedStatement.setInt(9, log_in.getRecommendedPeopleCount());
        preparedStatement.setDate(10, Date.valueOf(log_in.getDate()));
    }

    @Override
    public Optional<List<Log>> getLogsFor(Tour tour) {
        var connection = SQLConnectionProvider.getConnection();
        try {
            var preparedStatement = connection.prepareStatement(GET_LOGS_FOR_TOUR);
            preparedStatement.setInt(1, (int) tour.getId());
            var resultSet = preparedStatement.executeQuery();
            List<Log> logs = new ArrayList<>();
            while (resultSet.next()) {
                logs.add(logFromResultSet(resultSet));
            }
            SQLConnectionProvider.releaseConnection(connection);
            return Optional.of(logs);
        } catch (SQLException throwables) {
            log.error("Could not get logs for tour {}", tour, throwables);
            SQLConnectionProvider.releaseConnection(connection);
            return Optional.empty();
        }
    }

    private Log logFromResultSet(ResultSet resultSet) throws SQLException {
        return Log.builder()
                .id(resultSet.getInt("id"))
                .report(resultSet.getString("report"))
                .distance(resultSet.getString("distance"))
                .totalTime(resultSet.getString("totaltime"))
                .rating(resultSet.getInt("rating"))
                .averageSpeed(resultSet.getString("averagespeed"))
                .typeOfTransport(resultSet.getString("typeoftransport"))
                .difficulty(resultSet.getString("difficulty"))
                .recommendedPeopleCount(resultSet.getInt("recommendedpeoplecount"))
                .date(resultSet.getDate("date").toLocalDate())
                .build();
    }

    @Override
    public void removeLog(Log log_in) {
        var connection = SQLConnectionProvider.getConnection();
        try {
            var preparedStatement = connection.prepareStatement(DELETE_LOG);
            preparedStatement.setInt(1, log_in.getId());
            var i = preparedStatement.executeUpdate();
            if (i == 0) {
                throw new SQLException("Failed to delete log with id " + log_in.getId());
            }
            SQLConnectionProvider.releaseConnection(connection);
        } catch (SQLException throwables) {
            log.error("Could not delete log {}", log_in, throwables);
            SQLConnectionProvider.releaseConnection(connection);
        }
    }

    public void updateLog (Log log_in) {
        var connection = SQLConnectionProvider.getConnection();
        try {
            var preparedStatement = connection.prepareStatement(UPDATE_LOG);
            preparedStatement.setString(1, log_in.getReport());
            preparedStatement.setString(2, log_in.getDistance());
            preparedStatement.setString(3, log_in.getTotalTime());
            preparedStatement.setInt(4, log_in.getRating());
            preparedStatement.setString(5, log_in.getAverageSpeed());
            preparedStatement.setString(6, log_in.getTypeOfTransport());
            preparedStatement.setString(7, log_in.getDifficulty());
            preparedStatement.setInt(8, log_in.getRecommendedPeopleCount());
            preparedStatement.setDate(9, Date.valueOf(log_in.getDate()));
            preparedStatement.setInt(10, log_in.getId());
            var i = preparedStatement.executeUpdate();
            if (i == 0) {
                throw new SQLException("No row affected when updating log");
            }
            SQLConnectionProvider.releaseConnection(connection);
        } catch (SQLException throwables) {
            log.error("Could not update log {}", log_in, throwables);
            SQLConnectionProvider.releaseConnection(connection);
        }

    }
}
