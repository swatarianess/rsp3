package org.rspeer.game.script.event.listener;

import org.rspeer.event.Event;
import org.rspeer.event.listener.EventListener;
import org.rspeer.game.script.Script;
import org.rspeer.game.script.loader.ScriptSource;

public class ScriptChangeEvent extends Event<ScriptSource> {

    private final Script.State newState;
    private final Script.State oldState;

    public ScriptChangeEvent(ScriptSource source, Script.State newState, Script.State oldState) {
        super(source);
        this.newState = newState;
        this.oldState = oldState;
    }

    public Script.State getOldState() {
        return oldState;
    }

    public Script.State getNewState() {
        return newState;
    }

    @Override
    public void dispatch(EventListener listener) {
        if(listener instanceof ScriptChangeListener) {
            ((ScriptChangeListener) listener).notify(this);
        }
    }
}
