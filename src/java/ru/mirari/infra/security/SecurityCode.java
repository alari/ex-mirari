package ru.mirari.infra.security;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mirari.infra.mongo.BaseDao;
import ru.mirari.infra.mongo.Domain;
import ru.mirari.infra.mongo.MorphiaDriver;

import java.util.Date;
import java.util.UUID;

/**
 * @author alari
 * @since 11/28/11 8:07 PM
 */
@Entity("security.code")
public class SecurityCode extends Domain{
    @Indexed(unique = true, dropDups = true)
    String token = UUID.randomUUID().toString().replaceAll("-", "");

    @Reference private Account account;

    private String email;

    private Date dateCreated = new Date();

    static public class Dao extends BaseDao<SecurityCode> implements SecurityCodeRepository{
        @Autowired
        Dao(MorphiaDriver morphiaDriver) {
            super(morphiaDriver);
        }

        public SecurityCode getByToken(String token) {
            return createQuery().filter("token", token).get();
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Account getAccount(){
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
