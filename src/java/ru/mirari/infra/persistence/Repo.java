package ru.mirari.infra.persistence;

/**
 * @author alari
 * @since 1/4/12 4:05 PM
 */
public interface Repo<T> {
    public Object save(T o);

    public Object delete(T o);

    public T getById(String id);
}
