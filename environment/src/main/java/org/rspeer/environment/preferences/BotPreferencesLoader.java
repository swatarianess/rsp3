package org.rspeer.environment.preferences;

/*
    Author: zScorpio
    Time: 20:36 (UTC+2)
    Date: Sunday - 05/31/2020
*/

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.rspeer.commons.Configuration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Utility class providing functions to load bot preferences
 */
public class BotPreferencesLoader {

    public static void load(boolean local, Consumer<BotPreferences> later) {
        BotPreferences result = null;
        if (local) {
            result = fromLocalJson();
        }

        later.accept(result);
    }

    private static BotPreferences fromLocalJson() {
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
