package org.mvnsearch.r2dbcdemo;

import io.r2dbc.spi.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mvnsearch.r2dbcdemo.domain.Account;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Mariadb R2DBC test
 *
 * @author linux_china
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MariadbR2DBCTest {
    private Connection connection;


    @BeforeAll
    public void beforeAll() {
        String r2dbcUrl = "r2dbc:mariadb://root@localhost:3306/r2dbc";
        ConnectionFactory connectionFactory = ConnectionFactories.get(r2dbcUrl);
        this.connection = Mono.from(connectionFactory.create()).block();
    }

    @AfterAll
    public void afterAll() throws Exception {
        Thread.sleep(1000);
        Mono.just(this.connection.close()).subscribe();
        Thread.sleep(1000);
    }

    @Test
    public void testOperation() throws Exception {
        Publisher<? extends Result> result = connection.createStatement("SELECT * FROM account").execute();
        Flux.from(result)
                .concatMap(r -> r.map((row, metadata) -> {
                    return row2Account(row);
                }))
                .subscribe(account -> {
                    System.out.println(account.getCreatedAt());
                });
        Thread.sleep(1000);
    }

    @Test
    public void testBind() throws Exception {
        Publisher<? extends Result> result = connection
                .createStatement("SELECT * FROM account where id > ?")
                .bind(0, 1)
                .execute();
        Flux.from(result)
                .concatMap(r -> r.map((row, metadata) -> {
                    return row2Account(row);
                }))
                .subscribe(account -> {
                    System.out.println(account.getCreatedAt());
                });
        Thread.sleep(1000);
    }

    @Test
    public void testUpdate() {
        Publisher<? extends Result> result = connection.createStatement("update account set nick = ? where id =?")
                .bind(0, "tt")
                .bind(1, 1)
                .execute();
        Mono.from(result).map(Result::getRowsUpdated).subscribe(rowCount -> {
            System.out.println("Rows updated: " + rowCount);
        });
    }

    @Test
    public void testInsert() throws Exception {
        Publisher<? extends Result> result = connection.createStatement("insert into account(nick, created_at) values(?,now())")
                .bind(0, "myNick")
                .returnGeneratedValues("id")
                .execute();
        Flux.from(result).concatMap(r -> r.map((row, metadata) -> {
            return row.get("id");
        })).subscribe(accountId -> {
            System.out.println("Account ID: " + accountId);
        });
        Thread.sleep(2000);
    }


    public Account row2Account(Row row) {
        Account account = new Account();
        account.setNick(row.get("nick", String.class));
        account.setId(row.get("id", Integer.class));
        account.setEmail(row.get("email", String.class));
        account.setPhone(row.get("phone", String.class));
        account.setCreatedAt(row.get("created_at", LocalDateTime.class));
        account.setUpdatedAt(row.get("updated_at", LocalDateTime.class));
        return account;
    }
}
