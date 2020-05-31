package org.rspeer.ui.component.menu;

import org.rspeer.environment.Environment;
import org.rspeer.game.Game;
import org.rspeer.ui.debug.explorer.itf.InterfaceExplorer;
import org.rspeer.ui.locale.Message;
import org.rspeer.ui.component.menu.script.ScriptMenu;

import javax.swing.*;

public class BotMenuBar extends JMenuBar {

    private final Environment environment;

    public BotMenuBar(Environment environment) {
        this.environment = environment;
        add(createFileMenu());
        add(createDebugMenu());
        add(createWindowMenu());
        ScriptMenu sm = new ScriptMenu(environment);
        add(sm);
    }

    private JMenu createFileMenu() {
        JMenu file = new JMenu(Message.FILE.getActive(environment.getPreferences()));
        //TODO: stuff
        return file;
    }

    private JMenu createWindowMenu() {
        JMenu window = new JMenu(Message.WINDOW.getActive(environment.getPreferences()));

        JCheckBoxMenuItem onTop = new JCheckBoxMenuItem(Message.ALWAYS_ON_TOP.getActive(environment.getPreferences()));
        onTop.addItemListener(act -> {
            environment.getBotContext().getFrame().setAlwaysOnTop(onTop.getState());
        });

        window.add(onTop);
        return window;
    }

    private JMenu createDebugMenu() {
        JMenu debug = new JMenu(Message.DEBUG.getActive(environment.getPreferences()));

        JMenuItem itfs = new JMenuItem("Interfaces");
        itfs.addActionListener(act -> new InterfaceExplorer(environment));

        JCheckBoxMenuItem render = new JCheckBoxMenuItem("Render Scene", true);
        render.addActionListener(act -> Game.getClient().setSceneRenderingEnabled(!render.isSelected()));

        debug.add(render);
        debug.add(itfs);

        return debug;
    }
}
