package org.rspeer.ui.event;

import org.rspeer.ui.Window;

import java.applet.Applet;

public class SetAppletEvent extends UIEvent {

    private final Applet applet;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public SetAppletEvent(Window source, Applet applet) {
        super(source);
        this.applet = applet;
    }

    public Applet getApplet() {
        return applet;
    }
}
