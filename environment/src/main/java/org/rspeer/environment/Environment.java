package org.rspeer.environment;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.rspeer.commons.Configuration;
import org.rspeer.environment.preferences.BotPreferences;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Bot environment
 */
public class Environment {

    //TODO naming maybe
    private final ScriptContext scriptContext;
    private final BotContext botContext;
    private final BotPreferences preferences;

    public Environment() {
        scriptContext = new ScriptContext();
        botContext = new BotContext();
        preferences = loadPreferences();
        BotPreferences.Debug.setPreferences(preferences);
        BotPreferences.Window.setPreferences(preferences);
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

    private BotPreferences loadPreferences() {
        File preferencesFile = Configuration.Paths.PREFERENCES_LOCATION.toFile();
        if (preferencesFile.exists()) {
            Gson gson = new Gson();
            try (JsonReader reader = new JsonReader(new FileReader(preferencesFile))) {
                BotPreferences preferences = gson.fromJson(reader, BotPreferences.class);
                if (preferences != null) {
                    return preferences;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new BotPreferences();
    }
}
