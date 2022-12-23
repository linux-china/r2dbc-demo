package org.mvnsearch.r2dbcdemo.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Account repository
 *
 * @author linux_china
 */
public interface AccountRepo {
    
    Flux<Account> findAll();

    Mono<Long> updatePassword(Long id, String newPassword);
}
