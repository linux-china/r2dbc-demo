package org.mvnsearch.r2dbcdemo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mariadb.r2dbc.MariadbConnectionConfiguration;
import org.mariadb.r2dbc.MariadbConnectionFactory;
import org.mariadb.r2dbc.api.MariadbConnection;
import org.mvnsearch.r2dbcdemo.domain.Account;

import java.time.LocalDateTime;

/**
 * Mariadb R2DBC test
 *
 * @author linux_china
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MariadbR2DBCTest {
    private MariadbConnection connection;

    @BeforeAll
    public void beforeAll() {
        MariadbConnectionConfiguration conf = MariadbConnectionConfiguration.builder()
                .host("localhost")
                .port(3306)
                .username("root")
                .database("r2dbc")
                .build();
        MariadbConnectionFactory factory = new MariadbConnectionFactory(conf);
        this.connection = factory.create().block();
    }

    @AfterAll
    public void afterAll() throws Exception {
        Thread.sleep(1000);
        this.connection.close().subscribe();
        Thread.sleep(1000);
    }

    @Test
    public void testOperation() throws Exception {
        connection.createStatement("SELECT * FROM account")
                .execute()
                .flatMap(r -> r.map((row, metadata) -> {
                    Account account = new Account();
                    account.setNick(row.get("nick", String.class));
                    account.setId(row.get("id", Integer.class));
                    account.setEmail(row.get("email", String.class));
                    account.setPhone(row.get("phone", String.class));
                    account.setCreatedAt(row.get("created_at", LocalDateTime.class));
                    account.setUpdatedAt(row.get("updated_at", LocalDateTime.class));
                    return account;
                }))
                .subscribe(account -> {
                    System.out.println(account.getCreatedAt());
                });
        Thread.sleep(1000);
    }
}
