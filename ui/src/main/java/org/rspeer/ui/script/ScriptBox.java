package org.rspeer.ui.script;

/*
    Author: zScorpio
    Time: 09:25 (UTC+2)
    Date: Tuesday - 06/02/2020
*/

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ScriptBox extends JPanel {

    public static final int DEFAULT_WIDTH = 200;
    public static final int DEFAULT_HEIGHT = 100;

    public ScriptBox() {
        super(new GridBagLayout());
        setBorder(new LineBorder(Color.GREEN));
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setMaximumSize(getMinimumSize());
        setPreferredSize(getMinimumSize());
        setBackground(Color.RED);
    }
}
