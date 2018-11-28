package org.mvnsearch.r2dbcdemo;

import org.mvnsearch.r2dbcdemo.domain.Account;
import org.mvnsearch.r2dbcdemo.domain.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * portal controller
 *
 * @author linux_china
 */
@RestController
public class PortalController {
    @Autowired
    private AccountRepo accountRepo;

    @RequestMapping("/accounts")
    public Flux<Account> allAccounts() {
        return accountRepo.findAll();
    }
}
