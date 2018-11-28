package org.mvnsearch.r2dbcdemo.domain;

/**
 * Account
 *
 * @author linux_china
 */
public class Account {
    private Integer id;
    private String nick;
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
