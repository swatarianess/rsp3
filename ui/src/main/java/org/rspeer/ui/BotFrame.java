package org.rspeer.ui;

import org.rspeer.commons.Configuration;
import org.rspeer.environment.Environment;
import org.rspeer.game.Game;
import org.rspeer.ui.component.menu.BotMenuBar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BotFrame extends JFrame {

    private final Environment environment;

    public BotFrame(Environment environment) {
        super(Configuration.getApplicationTitle());
        this.environment = environment;
        applyComponents();
    }

    private void applyComponents() {
        try {
            setIconImage(ImageIO.read(getClass().getResource("/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
            Turns Lightweight popup components into Heavyweight to prevent Applet from drawing over them.
            Has to be done before the creation of any component that utilises a Lightweight component (Ex: JPopupMenu).
         */
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);

        add(Game.getClient().asApplet(), BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(new BotMenuBar(environment));
        environment.getBotContext().setFrame(this);
        pack();
        setMinimumSize(getSize());
        //TODO: Implement logger & save its show/hide state in a settings file
    }
}
