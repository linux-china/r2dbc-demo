package org.mvnsearch.r2dbcdemo.domain;

import reactor.core.publisher.Flux;

/**
 * Account repository
 *
 * @author linux_china
 */
public interface AccountRepo {
    public Flux<Account> findAll();
}
