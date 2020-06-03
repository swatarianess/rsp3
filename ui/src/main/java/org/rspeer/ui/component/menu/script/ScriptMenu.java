package org.rspeer.ui.component.menu.script;

import org.rspeer.environment.Environment;
import org.rspeer.game.script.ScriptMeta;
import org.rspeer.ui.locale.Message;
import org.rspeer.ui.component.script.ScriptSelector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ScriptMenu extends JMenu {

    private final Environment environment;

    private JMenuItem selectorMenuItem;
    private ScriptSelector selector;

    public ScriptMenu(Environment environment) {
        super(Message.SCRIPT.getActive(environment.getPreferences()));
        this.environment = environment;
        this.initializeMenuItems();
    }

    public void destroyScriptSelector() {
        selector = null;
    }

    public ScriptSelector getSelector() {
        return selector;
    }

    public void onStart() {
        selector.dispose();
        selectorMenuItem.setEnabled(false);

        ScriptMeta meta = environment.getScriptController().getCurrent().getMeta();
        String scriptText = String.format("%s v%s by %s", meta.name(), meta.version(), meta.developer());
        selectorMenuItem.setText(scriptText);

        JMenuItem stop = createStopMenuItem();

        add(new JSeparator());
        add(stop);
    }

    private JMenuItem createStopMenuItem() {
        JMenuItem stop = new JMenuItem("Stop");
        stop.addActionListener(act -> {
            environment.getScriptController().stop();

            Component[] components = getMenuComponents();
            for (int i = 1; i < components.length; i++) {
                remove(components[i]);
            }

            selectorMenuItem.setText(Message.SELECTOR.getActive(environment.getPreferences()));
            selectorMenuItem.setEnabled(true);
        });
        return stop;
    }

    private void initializeMenuItems() {
        selectorMenuItem = new JMenuItem(Message.SELECTOR.getActive(environment.getPreferences()));
        selectorMenuItem.addActionListener(this::openScriptSelector);
        add(selectorMenuItem);
    }

    private void openScriptSelector(ActionEvent actionEvent) {
        if (selector == null) {
            selector = new ScriptSelector(environment, this);
        } else {
            selector.setLocationRelativeTo(environment.getBotContext().getFrame());
            selector.setState(JFrame.ICONIFIED);
            selector.setState(JFrame.NORMAL);
        }
    }
}
