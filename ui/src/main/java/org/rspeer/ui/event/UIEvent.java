package org.rspeer.ui.event;

import org.rspeer.event.Event;
import org.rspeer.ui.Window;

public abstract class UIEvent extends Event<Window<?>> {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public UIEvent(Window<?> source) {
        super(source);
    }
}
