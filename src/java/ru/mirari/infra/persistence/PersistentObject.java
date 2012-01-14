package ru.mirari.infra.persistence;

/**
 * @author alari
 * @since 1/9/12 3:22 PM
 */
public interface PersistentObject {
    public String getStringId();

    public boolean isPersisted();
}
