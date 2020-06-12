package org.rspeer.environment;

import org.rspeer.environment.preferences.BotPreferences;
import org.rspeer.event.EventDispatcher;
import org.rspeer.game.script.ScriptController;

/**
 * Bot environment
 */
public class Environment {

    private final ScriptController scriptController;
    private final BotContext botContext;
    private final EventDispatcher eventDispatcher;

    private BotPreferences preferences;

    public Environment() {
        scriptController = new ScriptController();
        botContext = new BotContext();
        eventDispatcher = new EventDispatcher();
    }

    public ScriptController getScriptController() {
        return scriptController;
    }

    public BotContext getBotContext() {
        return botContext;
    }

    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    public BotPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(BotPreferences preferences) {
        this.preferences = preferences;
    }
}
