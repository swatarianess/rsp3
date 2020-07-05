package org.rspeer.game.script;

import org.rspeer.event.EventDispatcher;
import org.rspeer.event.Subscribe;
import org.rspeer.game.script.event.ScriptChangeEvent;
import org.rspeer.game.script.loader.ScriptProvider;
import org.rspeer.game.script.loader.ScriptSource;

public class ScriptController {

    private final EventDispatcher environmentDispatcher;

    private Script active;
    private ScriptSource source;
    private Thread scriptThread;

    public ScriptController(EventDispatcher environmentDispatcher) {
        this.environmentDispatcher = environmentDispatcher;
    }

    public void start(ScriptProvider provider, ScriptSource source) {
        if (active != null) {
            throw new IllegalStateException("A script is already running");
        }

        this.source = source;
        active = provider.define(source, environmentDispatcher);
        active.setState(Script.State.STARTING);
        active.setState(Script.State.RUNNING);

        scriptThread = new Thread(active);
        scriptThread.start();
    }

    public void stop() {
        if (active != null) {
            active.setState(Script.State.STOPPED);
        }
    }

    public ScriptSource getSource() {
        return source;
    }

    public Script getActive() {
        return active;
    }

    @Subscribe
    public void notify(ScriptChangeEvent e) {
        if (e.getState() == Script.State.STOPPED) {
            if (active != null) {
                active = null;
            }

            source = null;
            scriptThread = null;
        }
    }
}
