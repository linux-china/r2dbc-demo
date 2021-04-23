package org.mvnsearch.r2dbcdemo;

import org.junit.jupiter.api.Test;
import org.mvnsearch.r2dbcdemo.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;


public class R2dbcEntityTemplateTest extends BaseTest {
    @Autowired
    private R2dbcEntityTemplate template;

    @Test
    void testQuery() {
        Account account = template.selectOne(query(where("nick").is("linux_china")),
                Account.class).block();
        System.out.println(account.getEmail());
    }
}
