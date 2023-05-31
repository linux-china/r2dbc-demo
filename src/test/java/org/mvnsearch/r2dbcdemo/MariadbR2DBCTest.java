package org.mvnsearch.r2dbcdemo;

import io.r2dbc.spi.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mvnsearch.r2dbcdemo.domain.Account;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.MySqlDialect;
import org.springframework.data.relational.core.query.Query;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.springframework.data.relational.core.query.Criteria.where;

/**
 * Mariadb R2DBC test
 *
 * @author linux_china
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MariadbR2DBCTest {
    private Connection connection;
    private DatabaseClient databaseClient;
    private R2dbcEntityTemplate r2dbcEntityTemplate;


    @BeforeAll
    public void beforeAll() {
        String r2dbcUrl = "r2dbc:mariadb://root:123456@localhost:3306/r2dbc?allowPublicKeyRetrieval=true&useSSL=false";
        ConnectionFactory connectionFactory = ConnectionFactories.get(r2dbcUrl);
        this.connection = Mono.from(connectionFactory.create()).block();
        this.databaseClient = DatabaseClient.create(connectionFactory);
        this.r2dbcEntityTemplate = new R2dbcEntityTemplate(databaseClient, MySqlDialect.INSTANCE);
    }

    @AfterAll
    public void afterAll() throws Exception {
        Thread.sleep(1000);
        Mono.just(this.connection.close()).subscribe();
        Thread.sleep(1000);
    }

    @Test
    public void testDatabaseClient() throws Exception {
        databaseClient.sql("select * from account").filter((statement, next) -> next.execute(statement.fetchSize(100))).fetch().all()
                .subscribe(rows -> {
                    System.out.println(rows.get("email"));
                });
        Thread.sleep(1000);
    }

    @Test
    public void testEntityFind() {
        Account account = this.r2dbcEntityTemplate.select(Account.class).matching(Query.query(where("id").is(1))).first().block();
        System.out.println(account.getEmail());
    }

    @Test
    public void testOperation() throws Exception {
        Publisher<? extends Result> result = connection.createStatement("SELECT * FROM account").execute();
        Flux.from(result)
                .concatMap(r -> r.map((row, metadata) -> {
                    return row2Account(row);
                }))
                .subscribe(account -> {
                    System.out.println(account.getEmail());
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
