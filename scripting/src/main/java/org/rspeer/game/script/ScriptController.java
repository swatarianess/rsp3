package org.rspeer.game.script;

public class ScriptController {

    private Script current;
    private Thread scriptThread;

    public void start(Script script) {
        if (current != null) {
            throw new IllegalStateException("A script is already running");
        }

        current = script;
        current.setState(Script.State.STARTING);
        current.setState(Script.State.RUNNING);

        scriptThread = new Thread(current);
        scriptThread.start();
    }

    public void stop() {
        if (current != null) {
            current.setState(Script.State.STOPPED);
            current = null;
        }

        scriptThread = null;
    }

    public Script getCurrent() {
        return current;
    }
}
