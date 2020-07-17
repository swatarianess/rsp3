package org.rspeer;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javax.swing.SwingUtilities;
import org.rspeer.environment.Environment;
import org.rspeer.ui.BotFrame;
import org.rspeer.ui.worker.LoadGameWorker;

/**
 * Entry point for the application
 */
public class Application {

    public static final Injector injector = Guice.createInjector(new ApplicationModule());

    public static void main(String[] args) {
        try {
            Application application = injector.getInstance(Application.class);
            application.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject
    private Environment environment;

    public void start() {
        SwingUtilities.invokeLater(() -> {
            BotFrame ui = injector.getInstance(BotFrame.class);
            ui.display();

            LoadGameWorker loader = injector.getInstance(LoadGameWorker.class);
            loader.execute();
        });
    }
}
