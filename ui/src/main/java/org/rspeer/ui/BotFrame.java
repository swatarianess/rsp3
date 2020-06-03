package org.rspeer.ui;

import org.rspeer.commons.Configuration;
import org.rspeer.environment.Environment;
import org.rspeer.environment.preferences.type.AlwaysOnTopPreference;
import org.rspeer.event.Event;
import org.rspeer.ui.component.menu.BotMenuBar;
import org.rspeer.ui.component.menu.BotToolBar;
import org.rspeer.ui.component.splash.Splash;
import org.rspeer.ui.event.SetAppletEvent;
import org.rspeer.ui.event.SplashEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.io.IOException;

public class BotFrame extends Window<JFrame> {

    private final Environment environment;

    private BotMenuBar menu;
    private Splash splash;

    public BotFrame(Environment environment) {
        super(new JFrame(Configuration.getApplicationTitle()));
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
            frame.setIconImage(ImageIO.read(getClass().getResource("/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setAlwaysOnTop(environment.getPreferences().valueOf(AlwaysOnTopPreference.class));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        splash = new Splash();
        frame.add(splash, BorderLayout.CENTER);

        menu = new BotMenuBar(environment);
        frame.setJMenuBar(menu);
        frame.add(new BotToolBar(environment), BorderLayout.NORTH);

        environment.getBotContext().setFrame(frame);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(frame.getSize());
        frame.validate();
        //TODO: Implement logger & save its show/hide state in the preferences file
    }

    public void setApplet(Applet applet) {
        if (splash != null) {
            splash = null;
        }
        BorderLayout layout = (BorderLayout) frame.getContentPane().getLayout();
        Component previousComp = layout.getLayoutComponent(BorderLayout.CENTER);
        frame.remove(previousComp);
        frame.add(applet, BorderLayout.CENTER);
    }

    @Override
    public void display() {
        frame.setVisible(true);
    }

    @Override
    public void dispose() {
        frame.dispose();
    }

    @Override
    public <T extends Event<?>> void accept(T e) {
        if (e instanceof SplashEvent) {
            SplashEvent event = (SplashEvent) e;
            splash.setMessage(event.getMessage());
        } else if (e instanceof SetAppletEvent) {
            SetAppletEvent event = (SetAppletEvent) e;
            setApplet(event.getApplet());
        }
    }
}
