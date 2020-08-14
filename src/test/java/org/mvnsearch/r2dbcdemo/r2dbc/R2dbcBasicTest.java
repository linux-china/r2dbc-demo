package org.mvnsearch.r2dbcdemo.r2dbc;

import org.junit.jupiter.api.Test;
import org.mvnsearch.r2dbcdemo.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * R2DBC basic test
 *
 * @author linux_china
 */
@DataR2dbcTest(properties = {"spring.r2dbc.url=r2dbc:mariadb://127.0.0.1:3306/r2dbc", "spring.r2dbc.username=root"})
public class R2dbcBasicTest {

    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    private AccountReactiveRepo2 repo2;

    @Test
    public void testDatabaseClient() {
        Flux<Account> accounts = databaseClient.sql("select * from account order by id desc")
                .fetch()
                .all()
                .map(map -> {
                    Account account = new Account();
                    account.setId((Integer) map.get("id"));
                    return account;
                });
        StepVerifier.create(accounts).expectNextCount(2).verifyComplete();
    }

    @Test
    public void testReactiveRepo() {
        Mono<Account> account = repo2.findByNick("linux_china");
        StepVerifier.create(account).consumeNextWith(account1 -> {
            System.out.println(account1.getEmail());
        }).verifyComplete();
    }
}
