package org.rspeer.ui;

import org.rspeer.commons.Configuration;
import org.rspeer.environment.Environment;
import org.rspeer.ui.component.menu.BotMenuBar;
import org.rspeer.ui.component.welcome.WelcomePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.io.IOException;

public class BotFrame extends JFrame {

    private final Environment environment;

    private BotMenuBar botMenuBar;
    private WelcomePanel welcomePanel;

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

        setAlwaysOnTop(environment.getPreferences().getWindow().isAlwaysOnTop());

        /*
            Turns Lightweight popup components into Heavyweight to prevent Applet from drawing over them.
            Has to be done before the creation of any component that utilises a Lightweight component (Ex: JPopupMenu).
         */
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        welcomePanel = new WelcomePanel();
        add(welcomePanel, BorderLayout.CENTER);

        botMenuBar = new BotMenuBar(environment);
        setJMenuBar(botMenuBar);

        environment.getBotContext().setFrame(this);

        pack();
        setMinimumSize(getSize());
        //TODO: Implement logger & save its show/hide state in the preferences file
    }

    public void setApplet(Applet applet) {
        if (welcomePanel != null) {
            welcomePanel = null;
        }
        BorderLayout layout = (BorderLayout) getContentPane().getLayout();
        Component previousComp = layout.getLayoutComponent(BorderLayout.CENTER);
        remove(previousComp);
        add(applet, BorderLayout.CENTER);
    }

    public BotMenuBar getBotMenuBar() {
        return botMenuBar;
    }

    public WelcomePanel getWelcomePanel() {
        return welcomePanel;
    }
}
