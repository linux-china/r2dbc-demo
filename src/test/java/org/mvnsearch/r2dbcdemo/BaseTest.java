package org.mvnsearch.r2dbcdemo;

import com.github.database.rider.spring.api.DBRider;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * project base test
 *
 * @author linux_china
 */
@SpringBootTest(classes = R2dbcDemoApplication.class)
@ActiveProfiles("test")
@DBRider
public abstract class BaseTest {
}
