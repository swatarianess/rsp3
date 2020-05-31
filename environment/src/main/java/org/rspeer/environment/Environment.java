package org.rspeer.environment;

import org.rspeer.environment.preferences.BotPreferences;

/**
 * Bot environment
 */
public class Environment {

    //TODO naming maybe
    private final ScriptContext scriptContext;
    private final BotContext botContext;
    private BotPreferences preferences;

    public Environment() {
        scriptContext = new ScriptContext();
        botContext = new BotContext();
    }

    public ScriptContext getScriptContext() {
        return scriptContext;
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
}
