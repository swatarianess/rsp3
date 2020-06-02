package org.rspeer.ui.script;

/*
    Author: zScorpio
    Time: 09:25 (UTC+2)
    Date: Tuesday - 06/02/2020
*/

import org.rspeer.game.script.loader.ScriptSource;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ScriptBox extends JPanel {

    public static final int DEFAULT_WIDTH = 200;
    public static final int DEFAULT_HEIGHT = 100;

    public ScriptBox(ScriptSource script) {
        super();
        setLayout(new GridBagLayout());

        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setMaximumSize(getMinimumSize());
        setPreferredSize(getMinimumSize());

        initializeComponents(script);
    }

    private void initializeComponents(ScriptSource script) {
        setBorder(new TitledBorder(script.getName()));
        ScriptButton button = new ScriptButton(script);
        button.setBorder(null);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;

        add(button, constraints);
    }

    private class ScriptButton extends JButton {

        private final ScriptSource script;

        public ScriptButton(ScriptSource script) {
            this.script = script;
        }

        @Override
        public void paint(Graphics g2) {
            super.paint(g2);
            Graphics2D g = (Graphics2D) g2;
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            paintCustom(g);
        }

        private void paintCustom(Graphics2D g) {
            String developer = "Developer ".concat(script.getDeveloper());
            int width = g.getFontMetrics().stringWidth(developer);
            int height = g.getFontMetrics().getHeight();
            g.drawString(developer, (getWidth() / 2) - width / 2, (getHeight() / 2) + height / 2);
        }
    }
}
