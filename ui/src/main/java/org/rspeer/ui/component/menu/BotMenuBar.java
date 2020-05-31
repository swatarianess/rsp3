package org.rspeer.ui.component.menu;

import org.rspeer.commons.Pair;
import org.rspeer.environment.Environment;
import org.rspeer.game.Game;
import org.rspeer.ui.component.menu.script.ScriptMenu;
import org.rspeer.ui.debug.Debug;
import org.rspeer.ui.debug.GameDebug;
import org.rspeer.ui.debug.explorer.itf.InterfaceExplorer;
import org.rspeer.ui.locale.Message;

import javax.swing.*;

public class BotMenuBar extends JMenuBar {

    private static final DebugEntry[] DEBUG_ENTRIES = {
            new DebugEntry(new JCheckBoxMenuItem("Game"), new GameDebug())
    };

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

        JMenuItem onTop = new JCheckBoxMenuItem(Message.ALWAYS_ON_TOP.getActive(environment.getPreferences()));
        onTop.addItemListener(act -> environment.getBotContext().getFrame().setAlwaysOnTop(onTop.isSelected()));

        window.add(onTop);
        return window;
    }

    private JMenu createDebugMenu() {
        JMenu debug = new JMenu(Message.DEBUG.getActive(environment.getPreferences()));

        JMenuItem itfs = new JMenuItem("Interfaces");
        itfs.addActionListener(act -> new InterfaceExplorer(environment));

        JMenuItem renderScene = new JCheckBoxMenuItem("Render Scene", true);
        renderScene.addActionListener(act -> Game.getClient().setSceneRenderingDisabled(!renderScene.isSelected()));

        for (DebugEntry entry : DEBUG_ENTRIES) {
            JMenuItem option = entry.getLeft();
            Debug debugger = entry.getRight();
            option.addActionListener(act -> {
                if (option.isSelected()) {
                    Game.getClient().getEventDispatcher().subscribe(debugger);
                } else {
                    Game.getClient().getEventDispatcher().unsubscribe(debugger);
                }
            });
            debug.add(option);
        }

        debug.add(renderScene);
        debug.add(itfs);

        return debug;
    }

    public Environment getEnvironment() {
        return environment;
    }

    private static class DebugEntry extends Pair<JMenuItem, Debug> {

        private DebugEntry(JMenuItem option, Debug debug) {
            super(option, debug);
        }
    }
}
