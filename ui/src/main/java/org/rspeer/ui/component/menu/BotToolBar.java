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

    public BotToolBar(Environment environment) {
        environment.getInternalDispatcher().subscribe(this);

        setFloatable(false);

        add(Box.createHorizontalGlue());

        reload = new ReloadButton(environment);
        add(reload);

        start = new StartButton();
        add(start);

        start.addActionListener(e -> {
            if (start.getText().equals("Start")) {
                ScriptSelector selector = new ScriptSelector(environment.getBotContext().getFrame(), environment);
                selector.display();
            } else {
                environment.getInternalDispatcher().dispatch(new ScriptChangeEvent(
                        environment.getScriptController().getSource(),
                        Script.State.STOPPED,
                        Script.State.RUNNING
                ));
                environment.getScriptController().stop();
            }
        });
    }

    @Override
    public void notify(ScriptChangeEvent e) {
        SwingUtilities.invokeLater(() -> {
                switch (e.getNewState()) {
                    case RUNNING: {
                        start.setText("Stop");
                        reload.setVisible(true);
                        break;
                    }
                    case STOPPED: {
                        start.setText("Start");
                        reload.setVisible(false);
                        break;
                    }
                }
        });
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

        public ReloadButton(Environment environment) {
            setText("Reload");
            setVisible(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(true);
            setFocusable(false);
            addActionListener(e -> {
                ScriptSource source = environment.getScriptController().getSource();
                if(source == null) {
                    return;
                }
                Script currentScript = environment.getScriptController().getActive();
                if(currentScript == null) {
                    return;
                }
                ScriptProvider loader = new ScriptLoaderProvider().getLoader();
                Script.State currentState = currentScript.getState();
                environment.getInternalDispatcher().dispatch(new ScriptChangeEvent(source, Script.State.STOPPED, currentState));
                environment.getScriptController().stop();
                ScriptBundle bundle = loader.load();
                bundle.stream().filter(s -> s.shallowEquals(source)).findFirst().ifPresent(reloaded -> {
                    environment.getScriptController().start(loader.define(reloaded), reloaded);
                    environment.getInternalDispatcher().dispatch(new ScriptChangeEvent(reloaded, Script.State.RUNNING, Script.State.STOPPED));
                });
            });
        }
    }
}
