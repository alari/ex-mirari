package ru.mirari.infra.security.repo;

import com.google.code.morphia.Key;
import com.mongodb.WriteResult;
import ru.mirari.infra.security.SecurityCode;

public interface SecurityCodeRepo {
    public SecurityCode getByToken(String token);
    public Key<SecurityCode> save(SecurityCode o);
    public WriteResult delete(SecurityCode o);
}
