package org.rspeer.ui.event.listener;

import org.rspeer.event.listener.EventListener;
import org.rspeer.ui.event.SetAppletEvent;

public interface SetAppletListener extends EventListener {

    void notify(SetAppletEvent e);
}
