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

    public void setLocale(Locale locale) {
        this.locale = locale;
        save();
    }

    public Debug getDebug() {
        return debug;
    }

    public Window getWindow() {
        return window;
    }

    private void save() {
        Gson gson = new Gson();
        try (JsonWriter writer = new JsonWriter(new FileWriter(Configuration.Paths.PREFERENCES_LOCATION.toFile()))) {
            gson.toJson(gson.toJsonTree(this), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class Debug {

        private boolean renderGameDebug = false;
        private boolean renderScene = true;

        public boolean isGameDebugRenderingEnabled() {
            return renderGameDebug;
        }

        public void setGameDebugRenderingEnabled(boolean enabled) {
            this.renderGameDebug = enabled;
            save();
        }

        public boolean isSceneRenderingEnabled() {
            return renderScene;
        }

        public void setSceneRenderingEnabled(boolean enabled) {
            this.renderScene = enabled;
            save();
        }
    }

    public class Window {

        private boolean alwaysOnTop = false;

        public boolean isAlwaysOnTop() {
            return alwaysOnTop;
        }

        public void setAlwaysOnTop(boolean alwaysOnTop) {
            this.alwaysOnTop = alwaysOnTop;
            save();
        }
    }
}
