package org.rspeer.game.script;

import java.nio.file.Path;
import org.rspeer.commons.Configuration;
import org.rspeer.commons.Executor;
import org.rspeer.commons.Time;
import org.rspeer.event.EventDispatcher;
import org.rspeer.game.Game;
import org.rspeer.game.script.event.ScriptChangeEvent;
import org.rspeer.game.script.loader.ScriptSource;

//TODO make this better, add random handling (login screen, welcome screen etc)
public abstract class Script implements Runnable {

    private ScriptSource source;
    private EventDispatcher environmentDispatcher;

    private State state = State.STOPPED;

    public static Path getDataDirectory() {
        return Configuration.Paths.DATA_LOCATION;
    }

    public abstract int loop();

    public void onStart(String... args) {

    }

    public void onFinish() {

    }

    public final void setSource(ScriptSource source) {
        this.source = source;
    }

    public final void setEnvironmentDispatcher(EventDispatcher environmentDispatcher) {
        this.environmentDispatcher = environmentDispatcher;
    }

    public State getState() {
        return state;
    }

    public final void setState(State state) {
        this.state = state;
        if (state == State.STOPPED) {
            Executor.execute(this::onFinish);
        } else if (state == State.STARTING) {
            onStart();
        }
    }

    public final ScriptMeta getMeta() {
        return getClass().getAnnotation(ScriptMeta.class);
    }

    @Override
    public final void run() {
        environmentDispatcher.dispatch(new ScriptChangeEvent(source, Script.State.RUNNING, Script.State.STOPPED));
        while (true) {
            try {
                if (state == State.PAUSED) {
                    Time.sleep(100);
                    continue;
                }

                if (state == State.STOPPED) {
                    break;
                }

                Game.getClient().setMouseIdleTime(0);

                int sleep = loop();
                if (sleep < 0) {
                    setState(State.STOPPED);
                    return;
                }

                Time.sleep(sleep);
            } catch (Throwable e) {
                e.printStackTrace();
                Time.sleep(1000);
            }
        }
        environmentDispatcher.dispatch(new ScriptChangeEvent(source, Script.State.STOPPED, Script.State.RUNNING));
    }

    public enum State {
        STARTING,
        RUNNING,
        PAUSED,
        STOPPED
    }
}
