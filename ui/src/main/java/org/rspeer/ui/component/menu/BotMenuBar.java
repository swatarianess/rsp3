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
import java.util.function.Consumer;

public class BotMenuBar extends JMenuBar {

    private final Environment environment;
    private final DebugEntry[] debugEntries;

    private final JCheckBoxMenuItem renderGameDebug;

    //TODO: Move renderScene to another JMenu
    private JCheckBoxMenuItem renderScene;

    public BotMenuBar(Environment environment) {
        this.environment = environment;

        debugEntries = new DebugEntry[]{
                new DebugEntry(renderGameDebug = new JCheckBoxMenuItem("Game"), new GameDebug(),
                               selected -> environment.getPreferences().getDebug().setGameDebugRenderingEnabled(selected))
        };

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

        JMenuItem onTop = new JCheckBoxMenuItem(Message.ALWAYS_ON_TOP.getActive(environment.getPreferences()),
                                                environment.getPreferences().getWindow().isAlwaysOnTop());
        onTop.addItemListener(act -> {
            environment.getBotContext().getFrame().setAlwaysOnTop(onTop.isSelected());
            environment.getPreferences().getWindow().setAlwaysOnTop(onTop.isSelected());
        });

        window.add(onTop);
        return window;
    }

    private JMenu createDebugMenu() {
        JMenu debug = new JMenu(Message.DEBUG.getActive(environment.getPreferences()));

        JMenuItem itfs = new JMenuItem("Interfaces");
        itfs.addActionListener(act -> new InterfaceExplorer(environment));

        renderScene = new JCheckBoxMenuItem("Render Scene", true);
        renderScene.addItemListener(evt -> {
            //TODO: Remove the inversion once modscript gets updated
            Game.getClient().setSceneRenderingDisabled(!renderScene.isSelected());
            environment.getPreferences().getDebug().setSceneRenderingEnabled(renderScene.isSelected());
        });

        for (DebugEntry entry : debugEntries) {
            JMenuItem option = entry.getLeft();
            Debug debugger = entry.getRight();
            option.addItemListener(evt -> {
                if (option.isSelected()) {
                    Game.getClient().getEventDispatcher().subscribe(debugger);
                } else {
                    Game.getClient().getEventDispatcher().unsubscribe(debugger);
                }
                if (entry.onSelect != null) {
                    entry.onSelect.accept(option.isSelected());
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

    public JCheckBoxMenuItem getRenderScene() {
        return renderScene;
    }

    public JCheckBoxMenuItem getRenderGameDebug() {
        return renderGameDebug;
    }

    private static class DebugEntry extends Pair<JMenuItem, Debug> {

        private final Consumer<Boolean> onSelect;

        private DebugEntry(JMenuItem option, Debug debug) {
            this(option, debug, null);
        }

        private DebugEntry(JMenuItem option, Debug debug, Consumer<Boolean> onSelect) {
            super(option, debug);
            this.onSelect = onSelect;
        }
    }
}
