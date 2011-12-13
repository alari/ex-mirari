package ru.mirari.infra.security;

import com.google.code.morphia.Key;
import com.mongodb.WriteResult;

public interface SecurityCodeRepository {
    public SecurityCode getByToken(String token);
    public Key<SecurityCode> save(SecurityCode o);
    public WriteResult delete(SecurityCode o);
}
