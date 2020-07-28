package org.rspeer.ui.component.menu;

import com.google.inject.Inject;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rspeer.commons.Pair;
import org.rspeer.environment.preferences.BotPreferences;
import org.rspeer.environment.preferences.type.AlwaysOnTopPreference;
import org.rspeer.environment.preferences.type.SceneRenderPreference;
import org.rspeer.game.Game;
import org.rspeer.ui.BotFrame;
import org.rspeer.ui.debug.Debug;
import org.rspeer.ui.debug.GameDebug;
import org.rspeer.ui.debug.explorer.itf.InterfaceExplorer;
import org.rspeer.ui.locale.Message;

import javax.swing.*;
import java.util.function.Consumer;

public class BotMenuBar extends JMenuBar {

    private static final Logger logger = LogManager.getLogger("UI");

    private static final DebugEntry[] DEBUG_ENTRIES = new DebugEntry[]{
            new DebugEntry(new JCheckBoxMenuItem("Game"), new GameDebug())
    };

    private final BotPreferences preferences;

    private JCheckBoxMenuItem renderScene;

    @Inject
    public BotMenuBar(BotPreferences preferences, BotFrame frame) {
        this.preferences = preferences;
        add(createFileMenu());
        add(createDebugMenu(preferences, frame));
        add(createWindowMenu(frame));
        add(createDebugMenu(preferences, frame));
        add(createWindowMenu(frame));
        add(createSettingsMenu(preferences));
    }

    private JMenu createFileMenu() {
        JMenu file = new JMenu(Message.FILE.getActive(preferences));

        JMenuItem fileExit = new JMenuItem("Exit");
        fileExit.addActionListener(e -> System.exit(0));

        file.add(fileExit);

        return file;
    }

    private JMenu createWindowMenu(BotFrame frame) {
        JMenu window = new JMenu(Message.WINDOW.getActive(preferences));

        JMenuItem onTop = new JCheckBoxMenuItem(Message.ALWAYS_ON_TOP.getActive(preferences),
                preferences.valueOf(AlwaysOnTopPreference.class));
        onTop.addItemListener(act -> {
            frame.getFrame().setAlwaysOnTop(onTop.isSelected());
            preferences.set(AlwaysOnTopPreference.class, onTop.isSelected());
        });

        window.add(onTop);
        return window;
    }

    private JMenu createDebugMenu(BotPreferences preferences, BotFrame frame) {
        JMenu debug = new JMenu(Message.DEBUG.getActive(preferences));

        JMenuItem itfs = new JMenuItem("Interfaces");
        itfs.addActionListener(act -> new InterfaceExplorer(preferences, frame));

        renderScene = new JCheckBoxMenuItem("Render Scene", true);
        renderScene.addItemListener(evt -> {
            Game.getClient().setSceneRenderingDisabled(!renderScene.isSelected());
            preferences.set(SceneRenderPreference.class, renderScene.isSelected());
        });

        for (DebugEntry entry : DEBUG_ENTRIES) {
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

    private JMenu createSettingsMenu(BotPreferences preferences) {
        JMenu settings = new JMenu(Message.SETTINGS.getActive(preferences));

        JCheckBoxMenuItem loggingMenuItem = new JCheckBoxMenuItem("Logging");
        loggingMenuItem.setSelected(true);

        loggingMenuItem.addActionListener(e -> {
            logger.trace("Logging set to: " + loggingMenuItem.isSelected());
        });

        JComboBox<Level> loggingPopupMenu = new JComboBox<>(Level.values());

        loggingPopupMenu.addActionListener(e -> {
            logger.debug("Current level: " + logger.getLevel().name());

            System.out.println("STUFFFFF");
        });

        settings.add(loggingMenuItem);
        settings.add(loggingPopupMenu);

        return settings;
    }

    public JCheckBoxMenuItem getRenderScene() {
        return renderScene;
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
