package org.mvnsearch.r2dbcdemo.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Account repository
 *
 * @author linux_china
 */
public interface AccountRepo {
    public Flux<Account> findAll();

    public Mono<Integer> save(Account account);
}
