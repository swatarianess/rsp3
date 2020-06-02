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
import org.rspeer.ui.BotFrame;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class LoadGameWorker extends BotWorker<RSClient, String> {

    public LoadGameWorker(Environment environment, BotFrame ui) {
        super(environment, ui);
    }

    @Override
    protected RSClient work() throws IOException {
        return GameLoader.load(true, new Callback());
    }

    @Override
    protected void notify(String message) {
        ui.getSplash().setMessage(message);
    }

    @Override
    protected void onFinish() {
        try {
            RSClient client = get();
            ui.setApplet(client.asApplet());
            if (!environment.getPreferences().valueOf(SceneRenderPreference.class)) {
                ui.getMenu().getRenderScene().setSelected(false);
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
