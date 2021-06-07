package at.technikum.util;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;


@Slf4j
public class AppProperties {

    @Getter
    private static String DB_URL;
    @Getter
    private static String DB_PASSWORD;
    @Getter
    private static String DB_USER;
    @Getter
    private static Integer CONNECTION_POOL_SIZE;


    public static boolean initializeProperties(Properties properties) {
        DB_URL = properties.getProperty("DB_URL");
        DB_PASSWORD = properties.getProperty("DB_PASSWORD");
        DB_USER = properties.getProperty("DB_USER");
        try {
            CONNECTION_POOL_SIZE = Integer.valueOf(properties.getProperty("CONNECTION_POOL_SIZE"));
        } catch (NumberFormatException e) {
            log.error("Wrong config for Connection Pool, set CONNECTION_POOL_SIZE to integer");
            return false;
        }
        if (!allParamsValid(DB_PASSWORD, DB_URL, DB_USER)) {
            var fields = Arrays.stream(AppProperties.class.getDeclaredFields()).map(Field::getName).collect(Collectors.joining(", "));
            fields = fields.replace("log, ", "");
            log.error("Missing Params. Required: {}",fields);
            return false;
        }
        log.info("Starting App with config: {},{}", DB_URL, DB_PASSWORD);
        return true;
    }

    private static boolean allParamsValid(String ... strings) {
        return Arrays.stream(strings).noneMatch(AppProperties::isInvalid);
    }

    private static boolean isInvalid(String prop) {
        return prop == null || prop.isBlank();
    }

}
