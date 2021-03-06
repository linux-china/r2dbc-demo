package org.mvnsearch.r2dbcdemo.domain;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * account reactive repository
 *
 * @author linux_china
 */
public interface AccountReactiveRepo extends ReactiveCrudRepository<Account, Integer> {

    Mono<Account> findByNick(String nick);
}
