package org.rspeer.game.script.loader.local;

import org.rspeer.game.adapter.scene.Npc;
import org.rspeer.game.scene.Npcs;
import org.rspeer.game.script.Script;
import org.rspeer.game.script.ScriptMeta;

@ScriptMeta(name = "MadScript")
public class TestScript extends Script {

    @Override
    public int loop() {
        Npc npc = Npcs.query().results().nearest();
        if(npc != null) {
            System.out.println(npc.getName());
        }
        interact();
        return 350;
    }

    private void interact() {

    }

}
