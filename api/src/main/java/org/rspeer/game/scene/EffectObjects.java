package org.rspeer.game.scene;

import jag.RSNodeDeque;
import jag.game.scene.entity.RSEffectObject;
import org.rspeer.game.Game;
import org.rspeer.game.adapter.NodeDeque;
import org.rspeer.game.adapter.scene.EffectObject;
import org.rspeer.game.query.scene.EffectObjectQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EffectObjects {

    private static List<EffectObject> getLoaded() {
        RSNodeDeque<RSEffectObject> raw = Game.getClient().getEffectObjects();
        if (raw == null) {
            return Collections.emptyList();
        }

        List<EffectObject> objects = new ArrayList<>();
        NodeDeque<RSEffectObject> deque = new NodeDeque<>(raw);

        RSEffectObject object = deque.head();
        while (object != null) {
            if (!object.isFinished()) {
                objects.add(new EffectObject(object));
            }
            object = deque.next();
        }
        return objects;
    }

    public static EffectObjectQuery query() {
        return new EffectObjectQuery(EffectObjects::getLoaded);
    }
}
