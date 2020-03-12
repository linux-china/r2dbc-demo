package org.mvnsearch.r2dbcdemo.r2dbc;

import org.junit.jupiter.api.Test;
import org.mvnsearch.r2dbcdemo.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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

    @Autowired
    private AccountReactiveRepo2 repo2;

    @Test
    public void testDatabaseClient() throws Exception {
        Flux<Account> accounts = databaseClient.select()
                .from(Account.class)
                .orderBy(Sort.by(desc("id")))
                .fetch()
                .all();
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
