package org.rspeer.ui;

import org.rspeer.event.Event;

public abstract class Window<T extends java.awt.Window> {

    protected final T frame;

    protected Window(T frame) {
        this.frame = frame;
    }

    public abstract void display();

    public abstract void dispose();

    public abstract <E extends Event<?>> void accept(E event);
}
