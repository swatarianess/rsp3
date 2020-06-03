package org.rspeer.ui.component.script;

/*
    Author: zScorpio
    Time: 10:11 (UTC+2)
    Date: Tuesday - 06/02/2020
*/

import org.rspeer.environment.Environment;
import org.rspeer.game.script.loader.ScriptSource;
import org.rspeer.ui.component.menu.script.ScriptMenu;
import org.rspeer.ui.component.layout.WrapLayout;

import javax.swing.*;

public class ScriptSelectorViewport extends JPanel {

    public static final int HGAP = 4;
    public static final int VGAP = 4;

    private final Environment environment;
    private final ScriptMenu menu;

    public ScriptSelectorViewport(Environment environment, ScriptMenu menu) {
        this.environment = environment;
        this.menu = menu;

        setLayout(new WrapLayout(WrapLayout.LEFT, HGAP, VGAP));
    }

    public void addScript(ScriptSource source) {
        add(new ScriptBox(environment, menu, source));
    }
}
