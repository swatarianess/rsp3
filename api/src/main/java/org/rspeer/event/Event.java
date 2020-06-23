package org.rspeer.event;

import java.util.EventObject;

public abstract class Event<T> extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public Event(T source) {
        super(source);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getSource() {
        return (T) super.getSource();
    }
}
