package org.rspeer.ui.worker;

/*
    Author: zScorpio
    Time: 22:18 (UTC+2)
    Date: Sunday - 05/31/2020
*/

import jag.game.RSClient;
import org.rspeer.environment.Environment;
import org.rspeer.environment.preferences.type.SceneRenderPreference;
import org.rspeer.game.Game;
import org.rspeer.game.loader.GameLoader;
import org.rspeer.ui.Window;
import org.rspeer.ui.event.SetAppletEvent;
import org.rspeer.ui.event.SplashEvent;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class LoadGameWorker extends BotWorker<RSClient, String> {

    public LoadGameWorker(Environment environment, Window window) {
        super(environment, window);
    }

    @Override
    protected RSClient work() throws IOException {
        return GameLoader.load(true, new Callback());
    }

    @Override
    protected void notify(String message) {
        window.accept(new SplashEvent(window, message));
    }

    @Override
    protected void onFinish() {
        try {
            RSClient client = get();
            window.accept(new SetAppletEvent(window, client.asApplet()));
            if (!environment.getPreferences().valueOf(SceneRenderPreference.class)) {
                //TODO readd this after event dispatcher is finished via PreferenceListener and PerferenceEvent
                //window.getMenu().getRenderScene().setSelected(false);
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class Callback implements Consumer<RSClient> {

        @Override
        public void accept(RSClient client) {
            publish("Loading RuneScape");
            Game.setClient(client);
            publish("Successfully loaded RuneScape");
        }
    }
}
