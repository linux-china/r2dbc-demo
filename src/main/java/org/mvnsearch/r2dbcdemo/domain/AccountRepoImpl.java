package org.mvnsearch.r2dbcdemo.domain;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
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
                .map(this::row2Account).all();
    }

    @Override
    public Mono<Long> updatePassword(Long id, String newPassword) {
        return databaseClient.sql("update account set passwd = $1 where id = $2")
                .bind("$1", newPassword)
                .bind("$2", id)
                .fetch()
                .rowsUpdated();
    }

    public Account row2Account(Row row, RowMetadata metadata) {
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
