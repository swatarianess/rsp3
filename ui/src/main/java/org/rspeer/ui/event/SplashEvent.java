package org.rspeer.ui.event;

import org.rspeer.ui.Window;

public class SplashEvent extends UIEvent<Window> {

    private final String message;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public SplashEvent(Window source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
