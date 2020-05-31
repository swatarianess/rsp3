package org.rspeer;

import org.rspeer.environment.Environment;
import org.rspeer.game.Game;
import org.rspeer.game.loader.GameLoader;
import org.rspeer.ui.BotFrame;

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
        GameLoader.load(true, Game::setClient);

        SwingUtilities.invokeLater(() -> {
            BotFrame ui = new BotFrame(environment);
            ui.setVisible(true);
        });
    }
}
