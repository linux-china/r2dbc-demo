package org.mvnsearch.r2dbcdemo.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class R2dbcDemoApplicationTests {
    @Autowired
    private DatabaseClient databaseClient;

    @Test
    public void testQueryTimestamp() throws Exception {
        databaseClient.sql("SELECT nick from account where id = ?").bind(0, 2)
                .map(result -> result.get("nick"))
                .all()
                .subscribe(System.out::println);
        Thread.sleep(1000);
    }

}
