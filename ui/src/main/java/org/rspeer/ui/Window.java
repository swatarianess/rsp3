package org.rspeer.ui;

import org.rspeer.event.Event;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public abstract class Window {

    protected final JFrame frame;
    protected final List<SubWindow> children;

    protected Window(JFrame frame) {
        this.frame = frame;
        this.children = new LinkedList<>();
    }

    public abstract void display();

    public abstract void dispose();

    public abstract <T extends Event<?>> void accept(T event);
}
