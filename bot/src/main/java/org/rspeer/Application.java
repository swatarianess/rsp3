package org.rspeer;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import org.rspeer.environment.Environment;
import org.rspeer.environment.preferences.BotPreferences;
import org.rspeer.game.Game;
import org.rspeer.game.loader.GameLoader;
import org.rspeer.ui.BotFrame;
import org.rspeer.ui.debug.GameDebug;

import javax.swing.*;
import java.io.IOException;

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

    public void start() throws IOException {
        BotPreferences.Debug debugPref = environment.getPreferences().getDebug();

        GameDebug gameDebug;
        if (debugPref.isGameDebugRenderingEnabled()) {
            gameDebug = new GameDebug();
        } else {
            gameDebug = null;
        }

        GameLoader.load(true, client -> {
            Game.setClient(client);
            //TODO: Remove boolean inversion once the modscript has been updated
            client.setSceneRenderingEnabled(!debugPref.isSceneRenderingEnabled());
            if (debugPref.isGameDebugRenderingEnabled()) {
                client.getEventDispatcher().subscribe(gameDebug);
            }
        });

        SwingUtilities.invokeLater(() -> {
            try {
                FlatLaf laf = new FlatLightLaf();
                FlatLaf.install(laf);
                UIManager.setLookAndFeel(laf);
                System.setProperty("sun.awt.noerasebackground", "true");
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);

                BotFrame ui = new BotFrame(environment, gameDebug);
                ui.pack();
                ui.validate();
                ui.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
