package org.rspeer.ui.event.listener;

import org.rspeer.event.listener.EventListener;
import org.rspeer.ui.event.SplashEvent;

public interface SplashListener extends EventListener {

    void notify(SplashEvent e);
}
