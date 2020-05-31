package org.rspeer.ui.worker;

/*
    Author: zScorpio
    Time: 22:18 (UTC+2)
    Date: Sunday - 05/31/2020
*/

import org.rspeer.environment.Environment;
import org.rspeer.ui.BotFrame;

import javax.swing.*;
import java.util.List;

public abstract class BotWorker<T, V> extends SwingWorker<T, V> {

    protected final Environment environment;
    protected final BotFrame ui;

    public BotWorker(Environment environment, BotFrame ui) {
        this.environment = environment;
        this.ui = ui;
    }

    @Override
    protected T doInBackground() throws Exception {
        return doWork();
    }

    @Override
    protected void process(List<V> chunks) {
        notify(chunks.get(chunks.size() - 1));
    }

    @Override
    protected void done() {
        onFinish();
    }

    protected abstract T doWork() throws Exception;

    protected abstract void notify(V message);

    protected abstract void onFinish();
}
