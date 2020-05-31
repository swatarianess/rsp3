package org.rspeer.ui;

import org.rspeer.commons.Configuration;
import org.rspeer.environment.Environment;
import org.rspeer.environment.preferences.BotPreferences;
import org.rspeer.game.Game;
import org.rspeer.ui.component.menu.BotMenuBar;
import org.rspeer.ui.debug.GameDebug;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BotFrame extends JFrame {

    private final Environment environment;

    public BotFrame(Environment environment, GameDebug gameDebug) {
        super(Configuration.getApplicationTitle());
        this.environment = environment;
        applyComponents(gameDebug);
    }

    private void applyComponents(GameDebug gameDebug) {
        try {
            setIconImage(ImageIO.read(getClass().getResource("/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BotPreferences.Window windowPref = environment.getPreferences().getWindow();

        setAlwaysOnTop(windowPref.isAlwaysOnTop());

        /*
            Turns Lightweight popup components into Heavyweight to prevent Applet from drawing over them.
            Has to be done before the creation of any component that utilises a Lightweight component (Ex: JPopupMenu).
         */
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);

        add(Game.getClient().asApplet(), BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(new BotMenuBar(environment, gameDebug));
        environment.getBotContext().setFrame(this);
        pack();
        setMinimumSize(getSize());
        //TODO: Implement logger & save its show/hide state in a settings file
    }
}
