package org.mvnsearch.r2dbcdemo.domain;

import io.r2dbc.client.R2dbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

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
        return r2dbc.inTransaction(handle ->
                handle.select("SELECT * from account")
                        .mapResult(result -> result.map((row, rowMetadata) -> {
                            Account account = new Account();
                            account.setId(row.get("id", Integer.class));
                            account.setEmail(row.get("email", String.class));
                            account.setNick(row.get("nick", String.class));
                            return account;
                        })));

    }
}
