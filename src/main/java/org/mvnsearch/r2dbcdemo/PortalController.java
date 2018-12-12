package org.mvnsearch.r2dbcdemo;

import org.mvnsearch.r2dbcdemo.domain.Account;
import org.mvnsearch.r2dbcdemo.domain.AccountReactiveRepo;
import org.mvnsearch.r2dbcdemo.domain.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * portal controller
 *
 * @author linux_china
 */
@RestController
public class PortalController {
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    AccountReactiveRepo accountReactiveRepo;

    @RequestMapping("/accounts")
    public Flux<Account> allAccounts() {
        return accountRepo.findAll();
    }

    @RequestMapping("/account/{nick}")
    public Mono<Account> showAccountByNick(@PathVariable String nick) {
        return accountReactiveRepo.findByNick(nick);
    }
}
