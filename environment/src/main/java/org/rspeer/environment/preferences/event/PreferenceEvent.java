package org.rspeer.environment.preferences.event;

import org.rspeer.environment.preferences.type.BotPreference;
import org.rspeer.game.event.Event;
import org.rspeer.game.event.listener.EventListener;

public class PreferenceEvent extends Event<BotPreference<?>> {

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public PreferenceEvent(BotPreference<?> source) {
        super(source);
    }

    @Override
    public void dispatch(EventListener listener) {
        if (listener instanceof PreferenceListener) {
            ((PreferenceListener) listener).notify(this);
        }
    }
}
