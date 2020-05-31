package org.rspeer.environment.preferences;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import org.rspeer.commons.Configuration;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class BotPreferences {

    private final Debug debug = new Debug();
    private final Window window = new Window();

    private Locale locale = Locale.ENGLISH;

    public Locale getLocale() {
        return locale;
    }

    private static void save(BotPreferences preferences) {
        Gson gson = new Gson();
        try (JsonWriter writer = new JsonWriter(new FileWriter(Configuration.Paths.PREFERENCES_LOCATION.toFile()))) {
            gson.toJson(gson.toJsonTree(preferences), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Debug getDebug() {
        return debug;
    }

    public Window getWindow() {
        return window;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        save(this);
    }

    public static class Debug {

        private static BotPreferences preferences;

        private boolean renderGameDebug = false;
        private boolean renderScene = true;

        public boolean isGameDebugRenderingEnabled() {
            return renderGameDebug;
        }

        public static void setPreferences(BotPreferences preferences) {
            Debug.preferences = preferences;
        }

        public boolean isSceneRenderingEnabled() {
            return renderScene;
        }

        public void setSceneRenderingEnabled(boolean enabled) {
            this.renderScene = enabled;
            save(preferences);
        }

        public void setGameDebugRenderingEnabled(boolean enabled) {
            this.renderGameDebug = enabled;
            save(preferences);
        }
    }

    public static class Window {

        private static BotPreferences preferences;

        private boolean alwaysOnTop = false;

        public boolean isAlwaysOnTop() {
            return alwaysOnTop;
        }

        public static void setPreferences(BotPreferences preferences) {
            Window.preferences = preferences;
        }

        public void setAlwaysOnTop(boolean alwaysOnTop) {
            this.alwaysOnTop = alwaysOnTop;
            save(preferences);
        }
    }
}
