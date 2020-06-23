package org.rspeer.game.script.event;

import org.rspeer.event.Event;
import org.rspeer.game.script.Script;
import org.rspeer.game.script.loader.ScriptSource;

public class ScriptChangeEvent extends Event<ScriptSource> {

    private final Script.State state;
    private final Script.State previousState;

    public ScriptChangeEvent(ScriptSource source, Script.State state, Script.State previousState) {
        super(source);
        this.state = state;
        this.previousState = previousState;
    }

    public Script.State getPreviousState() {
        return previousState;
    }

    public Script.State getState() {
        return state;
    }
}
