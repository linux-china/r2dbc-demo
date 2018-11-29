package org.mvnsearch.r2dbcdemo;

import io.r2dbc.client.R2dbc;
import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class R2dbcDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(R2dbcDemoApplication.class, args);
    }

    @Bean
    public R2dbc r2dbcH2() {
        H2ConnectionConfiguration.Builder builder = H2ConnectionConfiguration.builder();
        H2ConnectionConfiguration configuration = builder.inMemory("public").username("sa").build();
        return new R2dbc(new H2ConnectionFactory(configuration));
    }

    @Bean
    @Primary
    public R2dbc r2dbcPostgresql() {
        PostgresqlConnectionConfiguration configuration = PostgresqlConnectionConfiguration.builder()
                .host("127.0.0.1")
                .database("r2dbc")
                .username("postgres")
                .password("123456")
                .build();
        return new R2dbc(new PostgresqlConnectionFactory(configuration));
    }
}
