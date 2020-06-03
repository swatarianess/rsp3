package org.rspeer.game.script;

public class ScriptController {

    private Script active;
    private Thread scriptThread;

    public void start(Script script) {
        if (active != null) {
            throw new IllegalStateException("A script is already running");
        }

        active = script;
        active.setState(Script.State.STARTING);
        active.setState(Script.State.RUNNING);

        scriptThread = new Thread(active);
        scriptThread.start();
    }

    public void stop() {
        if (active != null) {
            active.setState(Script.State.STOPPED);
            active = null;
        }

        scriptThread = null;
    }

    public Script getActive() {
        return active;
    }
}
