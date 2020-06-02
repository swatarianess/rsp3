package org.rspeer.ui;

import org.rspeer.commons.Configuration;
import org.rspeer.environment.Environment;
import org.rspeer.environment.preferences.type.AlwaysOnTopPreference;
import org.rspeer.ui.component.menu.BotMenuBar;
import org.rspeer.ui.component.welcome.Splash;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.io.IOException;

public class BotFrame extends JFrame {

    private final Environment environment;

    private BotMenuBar menu;
    private Splash splash;

    public BotFrame(Environment environment) {
        super(Configuration.getApplicationTitle());
        this.environment = environment;
        applyDefaults();
        applyComponents();
    }

    private void applyDefaults() {
        System.setProperty("sun.awt.noerasebackground", "true");
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void applyComponents() {
        try {
            setIconImage(ImageIO.read(getClass().getResource("/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setAlwaysOnTop(environment.getPreferences().valueOf(AlwaysOnTopPreference.class));

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        splash = new Splash();
        add(splash, BorderLayout.CENTER);

        menu = new BotMenuBar(environment);
        setJMenuBar(menu);

        environment.getBotContext().setFrame(this);

        pack();
        setLocationRelativeTo(null);
        setMinimumSize(getSize());
        validate();
        //TODO: Implement logger & save its show/hide state in the preferences file
    }

    public void setApplet(Applet applet) {
        if (splash != null) {
            splash = null;
        }
        BorderLayout layout = (BorderLayout) getContentPane().getLayout();
        Component previousComp = layout.getLayoutComponent(BorderLayout.CENTER);
        remove(previousComp);
        add(applet, BorderLayout.CENTER);
    }

    public BotMenuBar getMenu() {
        return menu;
    }

    public Splash getSplash() {
        return splash;
    }
}
