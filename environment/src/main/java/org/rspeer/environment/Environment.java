package org.rspeer.environment;

import org.rspeer.environment.preferences.BotPreferences;
import org.rspeer.event.EventDispatcher;
import org.rspeer.game.script.ScriptController;

/**
 * Bot environment
 */
public class Environment {

    private final EventDispatcher eventDispatcher;
    private final ScriptController scriptController;
    private final BotContext botContext;
    private BotPreferences preferences;

    public Environment() {
        eventDispatcher = EventDispatcher.Factory.getDefault("bot");
        scriptController = new ScriptController();
        botContext = new BotContext();
    }

    public ScriptController getScriptController() {
        return scriptController;
    }

    public BotContext getBotContext() {
        return botContext;
    }

    public BotPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(BotPreferences preferences) {
        this.preferences = preferences;
    }

    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }
}
