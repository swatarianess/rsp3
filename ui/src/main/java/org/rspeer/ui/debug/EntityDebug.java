package org.rspeer.ui.debug;

import java.awt.*;

public class EntityDebug extends Debug {

    private static final Font DEFAULT = Font.getFont(Font.DIALOG);


    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.GREEN);

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );

        g.setRenderingHints(rh);

        if (DEFAULT != null) g.setFont(DEFAULT);


    }


}
