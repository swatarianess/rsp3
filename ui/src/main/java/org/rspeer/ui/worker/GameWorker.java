package org.rspeer.ui.worker;

/*
    Author: zScorpio
    Time: 22:18 (UTC+2)
    Date: Sunday - 05/31/2020
*/

import jag.game.RSClient;
import org.rspeer.environment.Environment;
import org.rspeer.game.Game;
import org.rspeer.game.loader.GameLoader;
import org.rspeer.ui.BotFrame;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class GameWorker extends BotWorker<RSClient, String> {

    public GameWorker(Environment environment, BotFrame ui) {
        super(environment, ui);
    }

    @Override
    protected RSClient doWork() throws IOException {
        publish("Loading RuneScape");
        GameLoader.load(true, Game::setClient);
        publish("Successfully loaded RuneScape");
        return Game.getClient();
    }

    @Override
    protected void notify(String message) {
        ui.getWelcomePanel().setMessage(message);
    }

    @Override
    protected void onFinish() {
        try {
            RSClient client = get();
            ui.setApplet(client.asApplet());
            if (!environment.getPreferences().getDebug().isSceneRenderingEnabled()) {
                ui.getBotMenuBar().getRenderScene().setSelected(false);
            }
            if (environment.getPreferences().getDebug().isGameDebugRenderingEnabled()) {
                ui.getBotMenuBar().getRenderGameDebug().setSelected(true);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
