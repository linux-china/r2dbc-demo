package org.mvnsearch.r2dbcdemo;

import io.r2dbc.client.R2dbc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class R2dbcDemoApplicationTests extends BaseTest {
    @Autowired
    private R2dbc r2dbc;

    @Test
    public void testQueryTimestamp() throws Exception {
        r2dbc.inTransaction(handle ->
                handle.select("SELECT current_timestamp() as now")
                        .mapResult(result -> result.map((row, rowMetadata) -> row.get("now", Date.class))))
                .subscribe(System.out::println);
        Thread.sleep(1000);
    }

}
