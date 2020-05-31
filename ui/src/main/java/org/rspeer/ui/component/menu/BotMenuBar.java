package org.rspeer.ui.component.menu;

import org.rspeer.environment.Environment;
import org.rspeer.game.Game;
import org.rspeer.ui.component.menu.script.ScriptMenu;
import org.rspeer.ui.debug.GameDebug;
import org.rspeer.ui.debug.explorer.itf.InterfaceExplorer;
import org.rspeer.ui.locale.Message;

import javax.swing.*;

public class BotMenuBar extends JMenuBar {

    private final Environment environment;

    private JCheckBoxMenuItem renderScene;
    private JCheckBoxMenuItem renderGameDebug;

    private GameDebug gameDebug;

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

        JCheckBoxMenuItem onTop = new JCheckBoxMenuItem(Message.ALWAYS_ON_TOP.getActive(environment.getPreferences()),
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
            //TODO: Remove boolean inversion once the modscript has been updated
            Game.getClient().setSceneRenderingEnabled(!renderScene.isSelected());
            environment.getPreferences().getDebug().setSceneRenderingEnabled(renderScene.isSelected());
        });

        renderGameDebug = new JCheckBoxMenuItem("Render Game Debug");
        renderGameDebug.addItemListener(evt -> {
            if (renderGameDebug.isSelected()) {
                setGameDebug(new GameDebug());
                Game.getClient().getEventDispatcher().subscribe(getGameDebug());
            } else {
                Game.getClient().getEventDispatcher().unsubscribe(getGameDebug());
                setGameDebug(null);
            }
            environment.getPreferences().getDebug().setGameDebugRenderingEnabled(renderGameDebug.isSelected());
        });

        debug.add(renderGameDebug);
        debug.add(renderScene);
        debug.add(itfs);

        return debug;
    }

    public GameDebug getGameDebug() {
        return gameDebug;
    }

    public void setGameDebug(GameDebug gameDebug) {
        this.gameDebug = gameDebug;
    }

    public JCheckBoxMenuItem getRenderScene() {
        return renderScene;
    }

    public JCheckBoxMenuItem getRenderGameDebug() {
        return renderGameDebug;
    }
}
