package org.rspeer.ui.event;

import org.rspeer.event.Event;
import org.rspeer.event.listener.EventListener;

public abstract class UIEvent<T> extends Event<T> {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public UIEvent(T source) {
        super(source);
    }

    @Override
    public final void dispatch(EventListener listener) {

    }
}
