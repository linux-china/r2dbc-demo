package org.mvnsearch.r2dbcdemo;

import org.mvnsearch.r2dbcdemo.domain.Account;
import org.mvnsearch.r2dbcdemo.domain.AccountReactiveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements ApplicationRunner {

    private AccountReactiveRepo accountReactiveRepo;

    @Autowired
    public DataLoader(AccountReactiveRepo accountRepo) {
        this.accountReactiveRepo = accountRepo;
    }

    public void run(ApplicationArguments args) {
        accountReactiveRepo.findByNick("007").hasElement().subscribe(founded -> {
            if (!founded) {
                LocalDateTime now = LocalDateTime.now();
                Account account = new Account();
                account.setEmail("007@gmail.com");
                account.setNick("007");
                account.setPassword("123456");
                account.setPhone("001007");
                account.setCreatedAt(now);
                account.setUpdatedAt(now);
                accountReactiveRepo.save(account).subscribe();
                System.out.println("Account imported.");
            }
        });

    }
}
