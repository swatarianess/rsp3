package org.rspeer.event;

import java.util.Set;

@SuppressWarnings("rawtypes")
public interface Processor {

    void accept(Event event, Set<Subscription> subscriptions);

    class Immediate implements Processor {

        @Override
        public void accept(Event event, Set<Subscription> subscriptions) {
            for (Subscription subscription : subscriptions) {
                if (subscription.getType().isAssignableFrom(event.getClass())) {
                    subscription.delegate(event);
                }
            }
        }
    }
}