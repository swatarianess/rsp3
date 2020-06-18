package org.rspeer.ui.component.menu;

import org.rspeer.environment.Environment;
import org.rspeer.game.Game;
import org.rspeer.game.script.Script;
import org.rspeer.game.script.event.listener.ScriptChangeEvent;
import org.rspeer.game.script.event.listener.ScriptChangeListener;
import org.rspeer.game.script.loader.ScriptBundle;
import org.rspeer.game.script.loader.ScriptLoaderProvider;
import org.rspeer.game.script.loader.ScriptProvider;
import org.rspeer.game.script.loader.ScriptSource;
import org.rspeer.ui.component.script.ScriptSelector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BotToolBar extends JToolBar implements ScriptChangeListener {

    private final StartButton start;
    private final ReloadButton reload;
    private ScriptSource scriptSource;

    public BotToolBar(Environment environment) {
        environment.getInternalDispatcher().subscribe(this);

        setFloatable(false);

        add(Box.createHorizontalGlue());

        reload = new ReloadButton(environment, scriptSource);
        add(reload);

        start = new StartButton();
        add(start);

        start.addActionListener(e -> {
            if (start.getText().equals("Start")) {
                ScriptSelector selector = new ScriptSelector(environment.getBotContext().getFrame(), environment);
                selector.display();
            } else {
                environment.getScriptController().stop();
                reload.setVisible(false);
                start.setText("Start");
            }
        });
    }

    @Override
    public void notify(ScriptChangeEvent e) {
        scriptSource = e.getSource();

        System.out.println("Script Changed: " + "New: " + e.getNewState().toString() + " Old: " + e.getOldState().toString());

        switch (e.getNewState()) {
            case RUNNING: {
                start.setText("Stop");
                reload.setVisible(true);
            }
            case STOPPED: {
                start.setText("Start");
                reload.setVisible(false);
            }
        }
    }

    public static class StartButton extends JButton {

        public StartButton() {
            setText("Start");
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(true);
            setFocusable(false);
        }
    }

    public static class ReloadButton extends JButton {

        public ReloadButton(Environment environment, ScriptSource source) {
            setText("Reload");
            setVisible(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(true);
            setFocusable(false);
            addActionListener(e -> {
                if(source == null) {
                    return;
                }
                Script currentScript = environment.getScriptController().getActive();
                if(currentScript == null) {
                    return;
                }
                ScriptProvider loader = new ScriptLoaderProvider().getLoader();
                Script.State currentState = currentScript.getState();
                environment.getScriptController().stop();
                Game.getEventDispatcher().dispatch(new ScriptChangeEvent(source, Script.State.STOPPED, currentState));
                ScriptBundle bundle = loader.load();
                bundle.stream().filter(s -> s.shallowEquals(source)).findFirst().ifPresent(reloaded -> {
                    environment.getScriptController().start(loader.define(reloaded));
                    environment.getInternalDispatcher().dispatch(new ScriptChangeEvent(reloaded, Script.State.RUNNING, Script.State.STOPPED));
                });
            });
        }
    }
}
