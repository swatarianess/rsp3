package org.rspeer.ui;

import javax.swing.*;

public abstract class SubWindow extends Window {

    protected final Window parent;

    protected SubWindow(JFrame frame, Window parent) {
        super(frame);
        this.parent = parent;
    }
}
