package org.rspeer.ui.script;

/*
    Author: zScorpio
    Time: 10:11 (UTC+2)
    Date: Tuesday - 06/02/2020
*/

import org.rspeer.ui.layout.WrapLayout;

import javax.swing.*;
import java.awt.*;

public class ScriptSelectorViewport extends JPanel {

    public static final int HGAP = 4;
    public static final int VGAP = 4;

    public ScriptSelectorViewport() {
        setLayout(new WrapLayout(WrapLayout.LEFT, HGAP, VGAP));
        setBackground(Color.BLACK);

        for (int i = 0; i < 14; i++) {
            addScript();
        }
    }

    public void addScript() {
        ScriptBox scriptBox = new ScriptBox();
        add(scriptBox);
    }
}
