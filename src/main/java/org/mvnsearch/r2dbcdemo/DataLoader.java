package org.mvnsearch.r2dbcdemo;

import org.mvnsearch.r2dbcdemo.domain.Account;
import org.mvnsearch.r2dbcdemo.domain.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private AccountRepo accountRepo;

    @Autowired
    public DataLoader(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public void run(ApplicationArguments args) {
        Account account = new Account();
        account.setEmail("libing.chen@gmail.com");
        account.setNick("linux_china");
        accountRepo.save(account).subscribe();
    }
}
