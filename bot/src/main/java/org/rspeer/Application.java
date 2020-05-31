package org.rspeer;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import org.rspeer.commons.Configuration;
import org.rspeer.environment.Environment;
import org.rspeer.environment.preferences.BotPreferences;
import org.rspeer.environment.preferences.BotPreferencesLoader;
import org.rspeer.ui.BotFrame;
import org.rspeer.ui.worker.GameWorker;

import javax.swing.*;

/**
 * Entry point for the application
 */
public class Application {

    private final Environment environment;

    public Application() {
        environment = new Environment();
    }

    public static void main(String[] args) {
        try {
            Application application = new Application();
            application.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("Loading ".concat(Configuration.getApplicationTitle()).concat(" preferences"));
        BotPreferencesLoader.load(true, preferences -> {
            BotPreferences.Debug.setPreferences(preferences);
            BotPreferences.Window.setPreferences(preferences);
            environment.setPreferences(preferences);
            System.out.println("Successfully loaded ".concat(Configuration.getApplicationTitle()).concat(" preferences"));
        });

        SwingUtilities.invokeLater(() -> {
            FlatLaf laf = new FlatLightLaf();
            FlatLaf.install(laf);

            try {
                UIManager.setLookAndFeel(laf);
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            System.setProperty("sun.awt.noerasebackground", "true");
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);

            BotFrame ui = new BotFrame(environment);
            ui.pack();
            ui.validate();
            ui.setVisible(true);

            GameWorker gameWorker = new GameWorker(environment, ui);
            gameWorker.execute();
        });
    }
}
