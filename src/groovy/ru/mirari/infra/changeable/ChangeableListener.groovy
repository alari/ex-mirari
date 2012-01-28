package ru.mirari.infra.changeable

import java.beans.PropertyChangeListener

/**
 * @author alari
 * @since 1/28/12 1:08 PM
 */
@Singleton
class ChangeableListener implements PropertyChangeListener{
    void listen(ListenedChangeable o) {
        o.addPropertyChangeListener(this)
    }

    @Override
    void propertyChange(java.beans.PropertyChangeEvent evt) {
        ((ListenedChangeable)evt.source).setModified()
    }
}
