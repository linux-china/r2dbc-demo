package org.mvnsearch.r2dbcdemo;

import com.github.jasync.r2dbc.mysql.JasyncConnectionFactory;
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory;
import com.github.jasync.sql.db.mysql.util.URLParser;
import io.r2dbc.client.R2dbc;
import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.nio.charset.StandardCharsets;

/**
 * r2dbc auto configuration
 *
 * @author linux_china
 */
@Configuration
@EnableR2dbcRepositories
public class R2DBCAutoConfiguration extends AbstractR2dbcConfiguration {
   /* @Bean
    public R2dbc r2dbcH2() {
        H2ConnectionConfiguration.Builder builder = H2ConnectionConfiguration.builder();
        H2ConnectionConfiguration configuration = builder.inMemory("public").username("sa").build();
        return new R2dbc(new H2ConnectionFactory(configuration));
    }

    @Override
    public ConnectionFactory connectionFactory() {
        H2ConnectionConfiguration.Builder builder = H2ConnectionConfiguration.builder();
        H2ConnectionConfiguration configuration = builder.inMemory("public").username("sa").build();
        return new H2ConnectionFactory(configuration);
    }*/

    /*@Bean
    @Primary
    public R2dbc r2dbcPostgresql() {
        return new R2dbc(new PostgresqlConnectionFactory(postgresqlConnectionConfiguration()));
    }

    @Bean
    public PostgresqlConnectionConfiguration postgresqlConnectionConfiguration() {
        return PostgresqlConnectionConfiguration.builder()
                .host("127.0.0.1")
                .database("r2dbc")
                .username("postgres")
                .password("123456")
                .build();
    }

    @Override
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(postgresqlConnectionConfiguration());
    }*/

    @Bean
    public R2dbc r2dbcMySQL() {
        String url = "mysql://root:123456@30.56.164.234:3306/r2dbc";
        return new R2dbc(new JasyncConnectionFactory(new MySQLConnectionFactory(
                URLParser.INSTANCE.parseOrDie(url, StandardCharsets.UTF_8))));
    }

    @Override
    public ConnectionFactory connectionFactory() {
        String url = "mysql://root:123456@30.56.164.234:3306/r2dbc";
        return new JasyncConnectionFactory(new MySQLConnectionFactory(
                URLParser.INSTANCE.parseOrDie(url, StandardCharsets.UTF_8)));
    }
}
