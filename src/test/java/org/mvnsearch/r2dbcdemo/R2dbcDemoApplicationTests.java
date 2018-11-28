package org.mvnsearch.r2dbcdemo;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import io.r2dbc.client.R2dbc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

@SpringBootTest(classes = R2dbcDemoApplication.class)
@ActiveProfiles("test")
@DBRider
@DataSet("/dataset/account.xml")
public class R2dbcDemoApplicationTests {
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

    @Test
    public void testQueryAccount() throws Exception {
        r2dbc.inTransaction(handle ->
                handle.select("SELECT * from account")
                        .mapResult(result -> result.map((row, rowMetadata) -> row.get("nick", String.class))))
                .subscribe(System.out::println);
        Thread.sleep(1000);
    }

}
