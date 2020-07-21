package org.rspeer.environment.preferences.type;

import org.apache.logging.log4j.Level;

public class LoggerPreference extends BotPreference<Level> {
    @Override
    public Level getDefault() {
        return Level.DEBUG;
    }

    @Override
    public boolean isSupported(Level value) {
        return false;
    }



}
