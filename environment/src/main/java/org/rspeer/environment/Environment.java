package org.rspeer.environment;

import org.rspeer.environment.preferences.BotPreferences;
import org.rspeer.event.EventDispatcher;
import org.rspeer.event.Processor;
import org.rspeer.event.Registry;
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
        eventDispatcher = new EventDispatcher.Factory("bot")
                .processor(new Processor.Immediate())
                .registry(new Registry.Reflective())
                .handler(Throwable::printStackTrace)
                .get();
        scriptController = new ScriptController(eventDispatcher);
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
