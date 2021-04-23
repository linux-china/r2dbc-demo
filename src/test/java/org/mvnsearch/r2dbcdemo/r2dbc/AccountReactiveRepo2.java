package org.mvnsearch.r2dbcdemo.r2dbc;

import org.mvnsearch.r2dbcdemo.domain.Account;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * account reactive repository
 *
 * @author linux_china
 */
public interface AccountReactiveRepo2 extends ReactiveCrudRepository<Account, Integer> {

    Mono<Account> findByNick(String nick);
}
