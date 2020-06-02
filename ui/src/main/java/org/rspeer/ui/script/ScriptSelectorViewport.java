package org.rspeer.ui.script;

/*
    Author: zScorpio
    Time: 10:11 (UTC+2)
    Date: Tuesday - 06/02/2020
*/

import org.rspeer.game.script.loader.ScriptSource;
import org.rspeer.ui.layout.WrapLayout;

import javax.swing.*;

public class ScriptSelectorViewport extends JPanel {

    public static final int HGAP = 4;
    public static final int VGAP = 4;

    public ScriptSelectorViewport() {
        setLayout(new WrapLayout(WrapLayout.LEFT, HGAP, VGAP));
    }

    public void addScript(ScriptSource script) {
        for (int i = 0; i < 10; i++) {
            ScriptBox scriptBox = new ScriptBox(script);
            add(scriptBox);
        }
    }
}
