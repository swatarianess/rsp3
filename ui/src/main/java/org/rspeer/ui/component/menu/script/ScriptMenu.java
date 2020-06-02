package org.rspeer.ui.component.menu.script;

import org.rspeer.environment.Environment;
import org.rspeer.ui.locale.Message;
import org.rspeer.ui.script.ScriptSelector;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ScriptMenu extends JMenu {

    private final Environment environment;

    private ScriptSelector scriptSelector;

    public ScriptMenu(Environment environment) {
        super(Message.SCRIPT.getActive(environment.getPreferences()));
        this.environment = environment;
        this.initializeSelector();
    }

    private void initializeSelector() {
        JMenuItem selector = new JMenuItem(Message.SELECTOR.getActive(environment.getPreferences()));
        selector.addActionListener(this::openScriptSelector);
        add(selector);
    }

    private void openScriptSelector(ActionEvent actionEvent) {
        if (scriptSelector == null) {
            scriptSelector = new ScriptSelector(environment, this);
            scriptSelector.setVisible(true);
        } else {
            scriptSelector.setLocationRelativeTo(environment.getBotContext().getFrame());
            scriptSelector.setState(JFrame.ICONIFIED);
            scriptSelector.setState(JFrame.NORMAL);
        }
    }

    public void nullifyScriptSelector() {
        scriptSelector = null;
    }

    private JMenuItem createStopItem() {
        JMenuItem stop = new JMenuItem(Message.STOP.getActive(environment.getPreferences()));
        stop.addActionListener((act) -> {
            environment.getScriptController().stop();
            for (int i = getItemCount() - 1; i >= 0; i--) {
                JMenuItem item = getItem(i);
                remove(i);
                if (item.getLabel().equals("-")) {
                    break;
                }
            }
        });
        return stop;
    }
}
