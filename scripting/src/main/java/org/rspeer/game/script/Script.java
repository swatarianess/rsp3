package org.rspeer.game.script;

import org.rspeer.commons.Configuration;
import org.rspeer.commons.Executor;
import org.rspeer.commons.Time;
import org.rspeer.game.Game;

import java.nio.file.Path;

//TODO make this better, add random handling (login screen, welcome screen etc)
public abstract class Script implements Runnable {

    private State state = State.STOPPED;

    public static Path getDataDirectory() {
        return Configuration.Paths.DATA_LOCATION;
    }

    public abstract int loop();

    public void onStart(String... args) {

    }

    public void onFinish() {

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
        while (true) {
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
        }
    }

    public enum State {
        STARTING,
        RUNNING,
        PAUSED,
        STOPPED
    }
}
