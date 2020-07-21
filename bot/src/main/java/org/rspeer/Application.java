package org.rspeer;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rspeer.ui.BotFrame;
import org.rspeer.ui.worker.LoadGameWorker;

import javax.swing.*;

/**
 * Entry point for the application
 */
public class Application {

    private static final Injector injector = Guice.createInjector(new ApplicationModule());
    private static final Logger logger = LogManager.getLogger("Application");

    @Inject
    private BotFrame botFrame;

    @Inject
    private LoadGameWorker loadGameWorker;
    
    public static void main(String[] args) {
        logger.debug("Starting client.");
        Application application = injector.getInstance(Application.class);
        application.start();
    }

    public void start() {
        SwingUtilities.invokeLater(() -> {
            botFrame.display();
            loadGameWorker.execute();
        });
    }
}
