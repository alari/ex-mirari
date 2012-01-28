package ru.mirari.infra.changeable

import groovy.beans.Bindable

/**
 * @author alari
 * @since 1/28/12 1:27 PM
 */
@Bindable
class BasicChangeable implements ListenedChangeable{
    {
        ChangeableListener.getInstance().listen(this)
    }

    transient private modified = false

    @Override
    void setModified() {
        modified = true
    }

    @Override
    boolean isModified() {
        modified
    }

    @Override
    boolean setNotModified() {
        modified = false
    }
}
