package org.rspeer.ui.script;

/*
    Author: zScorpio
    Time: 09:22 (UTC+2)
    Date: Tuesday - 06/02/2020
*/

import org.rspeer.commons.Configuration;
import org.rspeer.environment.Environment;
import org.rspeer.game.script.loader.ScriptBundle;
import org.rspeer.game.script.loader.ScriptProvider;
import org.rspeer.game.script.loader.local.LocalScriptLoader;
import org.rspeer.ui.component.menu.script.ScriptMenu;
import org.rspeer.ui.locale.Message;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ScriptSelector extends JFrame {

    private final Environment environment;
    private final ScriptMenu menu;
    private final ScriptSelectorViewport viewport;
    private final LocalScriptLoader loader;

    public ScriptSelector(Environment environment, ScriptMenu menu) {
        super(Message.SCRIPT_SELECTOR.getActive(environment.getPreferences()));

        try {
            setIconImage(ImageIO.read(getClass().getResource("/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.environment = environment;
        this.menu = menu;
        this.viewport = initializeViewport();
        this.loader = new LocalScriptLoader(Configuration.Paths.SCRIPTS_LOCATION);

        onReload();

        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(environment.getBotContext().getFrame());
        setVisible(true);
    }

    public ScriptProvider getLoader() {
        return loader;
    }

    private ScriptSelectorViewport initializeViewport() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ScriptSelectorViewport viewport = new ScriptSelectorViewport(environment, menu);

        JScrollPane viewportScrollPane = new JScrollPane(viewport);
        viewportScrollPane.getVerticalScrollBar().setUnitIncrement(5);

        int scrollBarMinWidth = viewportScrollPane.getVerticalScrollBar().getMinimumSize().width + 2;
        int hgap = ScriptSelectorViewport.HGAP;
        int vgap = ScriptSelectorViewport.VGAP;
        Dimension scrollpaneMinSize = new Dimension((ScriptBox.DEFAULT_WIDTH + hgap) * 3 + hgap + scrollBarMinWidth,
                                                    (ScriptBox.DEFAULT_HEIGHT + vgap) * 3 + vgap);
        viewportScrollPane.setMinimumSize(scrollpaneMinSize);
        viewportScrollPane.setPreferredSize(scrollpaneMinSize);
        viewportScrollPane.setBorder(null);

        add(viewportScrollPane, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                menu.nullifyScriptSelector();
            }
        });

        return viewport;
    }

    private void onReload() {
        SwingWorker<ScriptBundle, Void> worker = new SwingWorker<ScriptBundle, Void>() {
            @Override
            protected ScriptBundle doInBackground() {
                ScriptBundle bundle = loader.load();
                bundle.addAll(loader.predefined());
                return bundle;
            }

            @Override
            protected void done() {
                try {
                    ScriptBundle bundle = get();

                    viewport.removeAll();
                    bundle.forEach(viewport::addScript);
                    viewport.revalidate();

                    JScrollPane scrollPane = (JScrollPane) viewport.getParent().getParent();
                    scrollPane.getVerticalScrollBar().setValue(0);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }
}
