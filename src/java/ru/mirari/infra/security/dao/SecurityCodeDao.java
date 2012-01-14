package ru.mirari.infra.security.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.mirari.infra.mongo.BaseDao;
import ru.mirari.infra.mongo.MorphiaDriver;
import ru.mirari.infra.security.SecurityCode;
import ru.mirari.infra.security.repo.SecurityCodeRepo;

/**
 * @author alari
 * @since 1/4/12 4:34 PM
 */
public class SecurityCodeDao extends BaseDao<SecurityCode> implements SecurityCodeRepo {
    @Autowired
    SecurityCodeDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver);
    }

    public SecurityCode getByToken(String token) {
        return createQuery().filter("token", token).get();
    }
}