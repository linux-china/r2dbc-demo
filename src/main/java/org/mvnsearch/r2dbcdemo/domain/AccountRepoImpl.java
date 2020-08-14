package org.mvnsearch.r2dbcdemo.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * account repository implementation
 *
 * @author linux_china
 */
@Repository
public class AccountRepoImpl implements AccountRepo {
    @Autowired
    private DatabaseClient databaseClient;

    @Override
    public Flux<Account> findAll() {
        return databaseClient.sql("select * from account order by id desc")
                .fetch()
                .all()
                .map(row -> {
                    Account account = new Account();
                    account.setId((Integer) row.get("id"));
                    account.setNick((String) row.get("nick"));
                    account.setEmail((String) row.get("email"));
                    account.setPhone((String) row.get("phone"));
                    account.setPassword((String) row.get("passwd"));
                    account.setCreatedAt((LocalDateTime) row.get("created_at"));
                    account.setUpdatedAt((LocalDateTime) row.get("updated_at"));
                    return account;
                });
    }

    @Override
    public Mono<Integer> updatePassword(Integer id, String newPassword) {
        return databaseClient.sql("update account set passwd = $1 where id = $2")
                .bind("$1", newPassword)
                .bind("$2", id)
                .fetch()
                .rowsUpdated();
    }
}
