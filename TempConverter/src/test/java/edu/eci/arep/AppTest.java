package edu.eci.arep;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.closeTo;
import java.math.BigDecimal;
import java.util.HashMap;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.path.json.config.JsonPathConfig;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @BeforeAll
    public static void init() {
        String[] args = {};
        App.main(args);
    }

    @AfterAll
    public static void end() {
        App.stopServer();
    }

    @Test
    public void valuesTest() {
        HashMap<Float, Float> values = new HashMap<>();
        values.put(0f, -17.7778f);
        values.put(5f, -15f);
        values.put(8f, -13.3333f);
        values.put(20f, -6.66667f);
        values.put(55f, 12.7778f);
        values.put(78.152f, 25.64f);
        values.put(158f, 70f);
        values.put(2548.8556f, 1398.25311111f);

        values.forEach((key, value) -> {
            JsonConfig jsonConfig = JsonConfig.jsonConfig()
                    .numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL);

            given().config(RestAssured.config().jsonConfig(jsonConfig)).port(5000)
                    .param("value", key).when().get("/convert/farenheit/celsius").then()
                    .body("farenheitDegrees",
                            closeTo(BigDecimal.valueOf(key), BigDecimal.valueOf(0.01f)))
                    .body("celsiusDegrees",
                            closeTo(BigDecimal.valueOf(value), BigDecimal.valueOf(0.01f)));
        });
    }
}
