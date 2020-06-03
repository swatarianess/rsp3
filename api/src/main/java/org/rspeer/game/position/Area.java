package org.rspeer.game.position;

import org.rspeer.commons.Random;

public abstract class Area {
    public static class Rectangular {
        private final Position bottomLeft, topRight;

        public Rectangular(Position bottomLeft, Position topRight) {
            this.bottomLeft = bottomLeft;
            this.topRight = topRight;
        }

        public Position getRandomPosition() {
            if (this.bottomLeft.getFloorLevel() != this.topRight.getFloorLevel()) {
                return null;
            }

            // prevent min-max errors by inversing
            int minX = Math.min(this.bottomLeft.getX(), this.topRight.getX());
            int minY = Math.min(this.bottomLeft.getY(), this.topRight.getY());

            int maxX = Math.max(this.bottomLeft.getX(), this.topRight.getX());
            int maxY = Math.max(this.bottomLeft.getY(), this.topRight.getY());

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
}