package org.rspeer.game.script;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rspeer.commons.Configuration;
import org.rspeer.commons.Executor;
import org.rspeer.commons.Time;
import org.rspeer.event.EventDispatcher;
import org.rspeer.game.Game;
import org.rspeer.game.provider.callback.EventMediator;
import org.rspeer.game.script.event.ScriptChangeEvent;
import org.rspeer.game.script.loader.ScriptSource;

import java.nio.file.Path;

//TODO make this better, add random handling (login screen, welcome screen etc)
public abstract class Script implements Runnable {

    private static final Logger logger = LogManager.getLogger(EventMediator.class);

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

        switch (state) {
            case STOPPED:
                logger.trace("Script stopped.");
                Executor.execute(this::onFinish);
                break;

            case STARTING:
                logger.trace("Script Starting.");
                onStart();
                break;
        }

    }

    public final ScriptMeta getMeta() {
        return getClass().getAnnotation(ScriptMeta.class);
    }

    @Override
    public final void run() {
        environmentDispatcher.dispatch(new ScriptChangeEvent(source, Script.State.RUNNING, Script.State.STOPPED));
        Game.getEventDispatcher().subscribe(this);
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
                logger.catching(e);
                Time.sleep(1000);
            }
        }
        Game.getEventDispatcher().unsubscribe(this);
        environmentDispatcher.dispatch(new ScriptChangeEvent(source, Script.State.STOPPED, Script.State.RUNNING));
    }

    public enum State {
        STARTING,
        RUNNING,
        PAUSED,
        STOPPED
    }

    public static Logger getLogger() {
        return logger;
    }
}
