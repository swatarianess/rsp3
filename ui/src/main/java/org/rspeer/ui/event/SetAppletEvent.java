package org.rspeer.ui.event;

import java.applet.Applet;
import org.rspeer.event.Event;
import org.rspeer.event.listener.EventListener;
import org.rspeer.ui.Window;
import org.rspeer.ui.event.listener.SetAppletListener;

public class SetAppletEvent extends Event<Window<?>, SetAppletListener> {

    private final Applet applet;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public SetAppletEvent(Window source, Applet applet) {
        super(source, SetAppletListener.class);
        this.applet = applet;
    }

    public Applet getApplet() {
        return applet;
    }

    @Override
    public void dispatch(EventListener listener) {
        ((SetAppletListener) listener).notify(this);
    }
}
