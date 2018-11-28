package org.mvnsearch.r2dbcdemo.domain;

import io.r2dbc.client.R2dbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * account repository implementation
 *
 * @author linux_china
 */
@Repository
public class AccountRepoImpl implements AccountRepo {
    @Autowired
    private R2dbc r2dbc;

    @Override
    public Flux<Account> findAll() {
        return r2dbc.withHandle(handle ->
                handle.select("SELECT * from account")
                        .mapResult(result -> result.map((row, rowMetadata) -> {
                            Account account = new Account();
                            account.setId(row.get("id", Integer.class));
                            account.setEmail(row.get("email", String.class));
                            account.setNick(row.get("nick", String.class));
                            account.setPhone(row.get("phone", String.class));
                            account.setPassword(row.get("passwd", String.class));
                            account.setCreatedAt(row.get("created_at", Date.class));
                            account.setUpdatedAt(row.get("updated_at", Date.class));
                            return account;
                        })));

    }

    @Override
    public Mono<Integer> save(Account account) {
        return r2dbc.inTransaction(handle ->
                handle.execute("INSERT INTO  account(nick,email,phone,passwd,created_at, updated_at) values($1,$2,'18667135137','123456',current_timestamp(),current_timestamp())", account.getNick(), account.getEmail()))
                .reduce(0, (x1, x2) -> x1 + x2);
    }
}
