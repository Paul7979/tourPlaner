package at.technikum.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class AppPropertiesTest {
    String DB_URL = "DB_URL";
    String DB_PASSWORD = "DB_PASSWORD";
    String DB_USER = "DB_USER";
    String CONNECTION_POOL_SIZE = "CONNECTION_POOL_SIZE";


    @Test
    void validAppPropertiesWhenLoadingPropertiesThenTrueReturned() throws IOException {
        Properties properties = new Properties();
        properties.load(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("application_valid.properties"));
        var valid = AppProperties.initializeProperties(properties);
        assertThat(valid).isTrue();
    }

    @Test
    void validAppPropertiesWhenLoadingPropertiesMissingDBPASSWORDThenFalseReturned() throws IOException {
        Properties properties = new Properties();
        properties.load(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("application_valid.properties"));
        properties.remove(DB_PASSWORD);
        var valid = AppProperties.initializeProperties(properties);
        assertThat(valid).isFalse();
    }

    @Test
    void validAppPropertiesWhenLoadingPropertiesMissingDBURLThenFalseReturned() throws IOException {
        Properties properties = new Properties();
        properties.load(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("application_valid.properties"));
        properties.remove(DB_URL);
        var valid = AppProperties.initializeProperties(properties);
        assertThat(valid).isFalse();
    }

    @Test
    void validAppPropertiesWhenLoadingPropertiesMissingCONNECTION_POOL_SIZEThenFalseReturned() throws IOException {
        Properties properties = new Properties();
        properties.load(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("application_valid.properties"));
        properties.remove(CONNECTION_POOL_SIZE);
        var valid = AppProperties.initializeProperties(properties);
        assertThat(valid).isFalse();
    }

    @Test
    void validAppPropertiesWhenLoadingPropertiesMissingDBUserThenFalseReturned() throws IOException {
        Properties properties = new Properties();
        properties.load(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("application_valid.properties"));
        properties.remove(DB_USER);
        var valid = AppProperties.initializeProperties(properties);
        assertThat(valid).isFalse();
    }

    @Test
    void validAppPropertiesWhenLoadingInvalidPropertiesThenFalseReturned() throws IOException {
        Properties properties = new Properties();
        properties.load(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("application_invalid.properties"));
        var valid = AppProperties.initializeProperties(properties);
        assertThat(valid).isFalse();
    }

}