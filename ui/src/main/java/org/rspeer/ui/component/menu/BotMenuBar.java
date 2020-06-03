package org.rspeer.ui.component.menu;

import org.rspeer.commons.Pair;
import org.rspeer.environment.Environment;
import org.rspeer.environment.preferences.type.AlwaysOnTopPreference;
import org.rspeer.environment.preferences.type.SceneRenderPreference;
import org.rspeer.game.Game;
import org.rspeer.ui.component.menu.script.ScriptMenu;
import org.rspeer.ui.debug.Debug;
import org.rspeer.ui.debug.GameDebug;
import org.rspeer.ui.debug.explorer.itf.InterfaceExplorer;
import org.rspeer.ui.locale.Message;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class BotMenuBar extends JMenuBar {

    private final Environment environment;
    private final DebugEntry[] debugEntries;

    private final JCheckBoxMenuItem renderGameDebug;

    //TODO: Move renderScene to another JMenu
    private JCheckBoxMenuItem renderScene;
    private InterfaceExplorer interfaceExplorer;

    public BotMenuBar(Environment environment) {
        this.environment = environment;

        debugEntries = new DebugEntry[]{
                new DebugEntry(renderGameDebug = new JCheckBoxMenuItem("Game"), new GameDebug())
        };

        add(createFileMenu());
        add(createDebugMenu());
        add(createWindowMenu());
        ScriptMenu sm = new ScriptMenu(environment);
        add(sm);
    }

    public void openInterfaceExplorer(ActionEvent actionEvent) {
        if (interfaceExplorer == null) {
            interfaceExplorer = new InterfaceExplorer(environment, this);
        } else {
            interfaceExplorer.setLocationRelativeTo(environment.getBotContext().getFrame());
            interfaceExplorer.setState(JFrame.ICONIFIED);
            interfaceExplorer.setState(JFrame.NORMAL);
        }
    }

    public void destroyInterfaceExplorer() {
        interfaceExplorer = null;
    }

    private JMenu createFileMenu() {
        JMenu file = new JMenu(Message.FILE.getActive(environment.getPreferences()));
        //TODO: stuff
        return file;
    }

    private JMenu createWindowMenu() {
        JMenu window = new JMenu(Message.WINDOW.getActive(environment.getPreferences()));

        JMenuItem onTop = new JCheckBoxMenuItem(Message.ALWAYS_ON_TOP.getActive(environment.getPreferences()),
                                                environment.getPreferences().valueOf(AlwaysOnTopPreference.class));
        onTop.addItemListener(act -> {
            environment.getBotContext().getFrame().setAlwaysOnTop(onTop.isSelected());
            environment.getPreferences().set(AlwaysOnTopPreference.class, onTop.isSelected());
        });

        window.add(onTop);
        return window;
    }

    private JMenu createDebugMenu() {
        JMenu debug = new JMenu(Message.DEBUG.getActive(environment.getPreferences()));

        JMenuItem itfs = new JMenuItem("Interfaces");
        itfs.addActionListener(this::openInterfaceExplorer);

        renderScene = new JCheckBoxMenuItem("Render Scene", true);
        renderScene.addItemListener(evt -> {
            Game.getClient().setSceneRenderingDisabled(!renderScene.isSelected());
            environment.getPreferences().set(SceneRenderPreference.class, renderScene.isSelected());
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
