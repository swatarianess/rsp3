package org.rspeer.environment;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.rspeer.commons.Configuration;
import org.rspeer.environment.preferences.BotPreferences;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;

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

    @SuppressWarnings("JavaReflectionMemberAccess")
    private BotPreferences loadPreferences() {
        File preferencesFile = Configuration.Paths.PREFERENCES_LOCATION.toFile();
        if (preferencesFile.exists()) {
            Gson gson = new Gson();
            try (JsonReader reader = new JsonReader(new FileReader(preferencesFile))) {
                BotPreferences preferences = gson.fromJson(reader, BotPreferences.class);
                if (preferences != null) {

                    /*
                        We have to manually set 'this' reference for nested classes to point at parent
                        since they were created using Gson and return null by default
                     */

                    Field debugThis = BotPreferences.Debug.class.getDeclaredField("this$0");
                    debugThis.setAccessible(true);
                    debugThis.set(preferences.getDebug(), preferences);
                    debugThis.setAccessible(false);

                    Field windowThis = BotPreferences.Window.class.getDeclaredField("this$0");
                    windowThis.setAccessible(true);
                    windowThis.set(preferences.getWindow(), preferences);
                    windowThis.setAccessible(false);

                    return preferences;
                }
            } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return new BotPreferences();
    }
}
