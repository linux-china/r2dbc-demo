package org.mvnsearch.r2dbcdemo.r2dbc;

import org.junit.jupiter.api.Test;
import org.mvnsearch.r2dbcdemo.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.DatabaseClient;

import static org.springframework.data.domain.Sort.Order.desc;

/**
 * R2DBC basic test
 *
 * @author linux_china
 */
@DataR2dbcTest(properties = {"spring.r2dbc.url=r2dbc:mysql://127.0.0.1:3306/r2dbc", "spring.r2dbc.username=root"})
public class R2dbcBasicTest {

    @Autowired
    private DatabaseClient databaseClient;

    @Test
    public void testDemo() throws Exception {
        databaseClient.select()
                .from(Account.class)
                .orderBy(Sort.by(desc("id")))
                .fetch()
                .all()
                .subscribe(account -> System.out.println(account.getNick()));
        Thread.sleep(1000);
    }
}
