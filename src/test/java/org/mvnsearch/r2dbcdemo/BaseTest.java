package org.mvnsearch.r2dbcdemo;

import com.github.database.rider.core.api.configuration.DBUnit;
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
@DBUnit(cacheConnection = false, leakHunter = true, schema = "r2dbc")
public abstract class BaseTest {
}
