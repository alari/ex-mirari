package ru.mirari.infra.changeable

/**
 * @author alari
 * @since 1/28/12 1:08 PM
 */
public interface ListenedChangeable {
    void setModified()
    boolean isModified()
    boolean setNotModified()
}