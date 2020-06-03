package org.rspeer.game.position;

import org.rspeer.commons.Random;

public class Area {

    private final Position bottomLeft, topRight;

    public Area(Position bottomLeft, Position topRight) {
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
    }

    public Position getRandomPosition() {
        // prevent min-max errors by inversing
        int minX = Math.min(this.bottomLeft.getX(), this.topRight.getX());
        int minY = Math.min(this.bottomLeft.getY(), this.topRight.getY());

        int maxX = Math.max(this.bottomLeft.getX(), this.topRight.getX());
        int maxY = Math.max(this.bottomLeft.getY(), this.topRight.getY());

        if (this.bottomLeft.getFloorLevel() != this.topRight.getFloorLevel()) {
            return null;
        }

        return new Position(
                Random.nextInt(minX, maxX),
                Random.nextInt(minY, maxY),
                this.bottomLeft.getFloorLevel()
        );
    }

    public Position getBottomLeft() {
        return bottomLeft;
    }

    public Position getTopRight() {
        return topRight;
    }
}
