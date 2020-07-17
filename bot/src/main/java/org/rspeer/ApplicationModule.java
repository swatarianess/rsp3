package org.rspeer;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.rspeer.environment.BotContext;
import org.rspeer.environment.Environment;
import org.rspeer.environment.preferences.BotPreferences;
import org.rspeer.event.EventDispatcher;
import org.rspeer.game.script.ScriptController;
import org.rspeer.ui.BotFrame;

public class ApplicationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Environment.class).asEagerSingleton();

        bind(EventDispatcher.class).annotatedWith(Names.named("BotDispatcher"))
                                   .toInstance(EventDispatcher.Factory.getDefault("bot"));

        bind(ScriptController.class).asEagerSingleton();
        bind(BotContext.class).asEagerSingleton();
        bind(BotPreferences.class).asEagerSingleton();

        bind(BotFrame.class).asEagerSingleton();
    }
}
