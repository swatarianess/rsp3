package org.rspeer.event;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public interface Registry {

    void subscribe(Object type);

    void unsubscribe(Object type);

    Set<Subscription> getSubscriptions();

    class Reflective implements Registry {

        private final Set<Subscription> subscriptions = new CopyOnWriteArraySet<>();

        @Override
        public void subscribe(Object type) {
            Class<?> clazz = type.getClass();
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            for (Method method : clazz.getMethods()) {
                Subscribe annotation = method.getAnnotation(Subscribe.class);
                Class<?>[] params = method.getParameterTypes();
                if (annotation != null && params.length == 1 && Event.class.isAssignableFrom(params[0])) {
                    try {
                        subscriptions.add(new Subscription(params[0], type, lookup.unreflect(method)));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        public void unsubscribe(Object type) {
            subscriptions.removeIf(subscription -> subscription.getReference() == type);
        }

        @Override
        public Set<Subscription> getSubscriptions() {
            return subscriptions;
        }
    }
}
