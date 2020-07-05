package org.rspeer.game.position.area;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import org.rspeer.game.adapter.type.SceneNode;
import org.rspeer.game.position.Position;

public class PolygonalArea implements Area {

    private final int floorLevel;
    private final List<Position> ps;
    private final java.awt.geom.Area polygon;

    PolygonalArea(Position... edges) {
        int[] xPoints = new int[edges.length];
        int[] yPoints = new int[edges.length];

        for (int i = 0; i < edges.length; i++) {
            Position position = edges[i];
            xPoints[i] = position.getX();
            yPoints[i] = position.getY();
        }

        this.ps = new ArrayList<>();
        this.polygon = new java.awt.geom.Area(new Polygon(xPoints, yPoints, xPoints.length));
        this.floorLevel = edges.length > 0 ? edges[0].getFloorLevel() : 0;

        Rectangle bounds = polygon.getBounds();

        for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
            for (int y = bounds.y; y < bounds.y + bounds.height; y++) {

                if (polygon.contains(x, y)) {
                    this.ps.add(new Position(x, y, floorLevel));
                }
            }
        }
    }

    @Override
    public int getFloorLevel() {
        return floorLevel;
    }

    @Override
    public List<Position> getTiles() {
        return ps;
    }

    @Override
    public boolean contains(SceneNode entity) {
        return entity != null
               && polygon.contains(entity.getX(), entity.getY());
    }
}
