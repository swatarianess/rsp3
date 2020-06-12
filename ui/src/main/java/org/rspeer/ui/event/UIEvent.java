package org.rspeer.ui.event;

import org.rspeer.event.Event;
import org.rspeer.event.listener.EventListener;

//TODO: Find a better implementation for this class
// Possible approaches:
// 1- Instantiate the EventMediator/EventDispatcher before the game is loaded then reference them in the client later on
// 2- Make EventMediator & EventDispatcher static instead of injecting them into the game
// 3- Refactor this class so that it doesn't require a generic type
// .
// NOTE: This class doesn't even relate to the EventMediator/EventDispatcher
public abstract class UIEvent<T> extends Event<T, T> {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public UIEvent(T source) {
        super(source, null);
    }

    @Override
    public final void dispatch(EventListener listener) {

    }
}
