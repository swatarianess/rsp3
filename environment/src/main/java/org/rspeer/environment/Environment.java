package org.rspeer.environment;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.rspeer.environment.preferences.BotPreferences;
import org.rspeer.event.EventDispatcher;
import org.rspeer.game.script.ScriptController;

/**
 * Bot environment
 */
public class Environment {

    @Inject
    @Named("BotDispatcher")
    private EventDispatcher eventDispatcher;

    @Inject
    private ScriptController scriptController;

    @Inject
    private BotContext botContext;

    @Inject
    private BotPreferences preferences;

    public ScriptController getScriptController() {
        return scriptController;
    }

    public BotContext getBotContext() {
        return botContext;
    }

    public BotPreferences getPreferences() {
        return preferences;
    }

    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }
}
