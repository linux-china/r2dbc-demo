package org.mvnsearch.r2dbcdemo.domain;

import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;
import org.mvnsearch.r2dbcdemo.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * account repository implementation test
 *
 * @author linux_china
 */
@DataSet("/dataset/account.xml")
public class AccountRepoImplTest extends BaseTest {
    @Autowired
    private AccountRepo accountRepo;

    @Test
    public void testFindAll() throws Exception {
        accountRepo.findAll().subscribe(account -> {
            System.out.println(account.getNick());
        });
        Thread.sleep(2000);
    }
}
