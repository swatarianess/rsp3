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

    /*
        Static just to make it clear that we're referring to ScriptButton
                     when calling getWidth(), getHeight(), etc.
     */
    private static class ScriptButton extends JButton {

        private final ScriptSource script;
        private final Insets insets;

        private final static int gap = 3;
        private final static int inset = 6;
        private final static int vgap = gap - 3;

        public ScriptButton(ScriptSource script) {
            this.script = script;
            this.insets = new Insets(inset - 3, inset + 2, inset + 4, inset + 2);
        }

        @Override
        public void paint(Graphics g2) {
            super.paint(g2);
            Graphics2D g = (Graphics2D) g2;
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            paintCustom(g);
        }

        private void paintCustom(Graphics2D g) {
            int bottomY = getHeight() - insets.bottom;

            Font plain = g.getFont();
            Font bold = plain.deriveFont(Font.BOLD);

            g.setFont(bold);
            String developer = "Developer: ";

            int developerWidth = g.getFontMetrics().stringWidth(developer);
            int developerHeight = g.getFontMetrics().getHeight();

            g.drawString(developer, insets.left, bottomY);
            g.setFont(plain);
            g.drawString(script.getDeveloper(), developerWidth + insets.left, bottomY);

            g.setFont(bold);
            String version = "Version: ";
            int versionWidth = g.getFontMetrics().stringWidth(version);

            bottomY = bottomY - developerHeight - vgap;

            g.drawString(version, insets.left, bottomY);
            g.setFont(plain);
            g.drawString(String.valueOf(script.getVersion()), versionWidth + insets.left, bottomY);
        }
    }
}
