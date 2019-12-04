package org.mvnsearch.r2dbcdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;

public class R2dbcDemoApplicationTests extends BaseTest {
    @Autowired
    private DatabaseClient databaseClient;

    @Test
    public void testQueryTimestamp() throws Exception {
        databaseClient.execute("SELECT current_timestamp() as now")
                .map(result -> result.get("now"))
                .all()
                .subscribe(System.out::println);
        Thread.sleep(1000);
    }

}
