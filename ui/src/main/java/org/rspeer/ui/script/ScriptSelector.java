package org.rspeer.ui.script;

/*
    Author: zScorpio
    Time: 09:22 (UTC+2)
    Date: Tuesday - 06/02/2020
*/

import org.rspeer.commons.Configuration;
import org.rspeer.environment.Environment;
import org.rspeer.game.script.loader.ScriptBundle;
import org.rspeer.game.script.loader.local.LocalScriptLoader;
import org.rspeer.ui.locale.Message;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ScriptSelector extends JFrame {

    private final LocalScriptLoader loader;
    private final Environment environment;

    private final ScriptSelectorViewport viewport;

    public ScriptSelector(Environment environment) {
        super(Message.SCRIPT_SELECTOR.getActive(environment.getPreferences()));

        try {
            setIconImage(ImageIO.read(getClass().getResource("/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.environment = environment;
        this.loader = new LocalScriptLoader(Configuration.Paths.SCRIPTS_LOCATION);
        this.viewport = initializeViewport();

        onReload();

        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(environment.getBotContext().getFrame());
    }

    private ScriptSelectorViewport initializeViewport() {
        ScriptSelectorViewport viewport = new ScriptSelectorViewport();

        JScrollPane viewportScrollPane = new JScrollPane(viewport);
        viewportScrollPane.getVerticalScrollBar().setUnitIncrement(5);

        int scrollBarMinWidth = viewportScrollPane.getVerticalScrollBar().getMinimumSize().width + 2;
        int hgap = ScriptSelectorViewport.HGAP;
        int vgap = ScriptSelectorViewport.VGAP;
        Dimension scrollpaneMinSize = new Dimension((ScriptBox.DEFAULT_WIDTH + hgap) * 3 + hgap + scrollBarMinWidth,
                                                    (ScriptBox.DEFAULT_HEIGHT + vgap) * 3 + vgap);

        viewportScrollPane.setMinimumSize(scrollpaneMinSize);
        viewportScrollPane.setPreferredSize(scrollpaneMinSize);

        add(viewportScrollPane, BorderLayout.CENTER);

        return viewport;
    }

    private void onReload() {
        environment.getScriptController().stop();

        ScriptBundle bundle = loader.load();
        bundle.addAll(loader.predefined());

        viewport.removeAll();
        bundle.forEach(viewport::addScript);

        SwingUtilities.invokeLater(() -> {
            JScrollPane scrollPane = (JScrollPane) viewport.getParent().getParent();
            scrollPane.getVerticalScrollBar().setValue(0);
        });
    }
}
