package org.rspeer.game.effect;

import jag.game.scene.entity.RSPathingEntity;
import org.rspeer.game.adapter.scene.PathingEntity;

public class Stance extends Animation {

    private final PathingEntity<?> src;

    public Stance(PathingEntity<?> src, int id, int frame) {
        super(id, frame);
        this.src = src;
    }

    public boolean isWalking() {
        RSPathingEntity provider = src.getProvider();
        return provider != null && getId() == provider.getWalkingStance();
    }
}
