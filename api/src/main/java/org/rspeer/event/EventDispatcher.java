package org.rspeer.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.rspeer.event.listener.EventListener;

public class EventDispatcher {

    private final Map<Class<?>, List<EventListener>> listeners = new ConcurrentHashMap<>();

    public void subscribe(EventListener el) {
        Class<?> group = el.getClass();

        if (!listeners.containsKey(group)) {
            listeners.put(group, new CopyOnWriteArrayList<>());
        }

        listeners.get(group).add(el);
    }

    public void unsubscribe(EventListener el) {
        Class<?> group = el.getClass();

        if (listeners.containsKey(group)) {
            List<EventListener> listenersList = listeners.get(group);
            listenersList.remove(el);

            if (listenersList.isEmpty()) {
                listeners.remove(group);
            }
        }
    }

    public void dispatch(Event<?, ?> e) {
        for (EventListener listener : getListeners(e)) {
            e.dispatch(listener);
        }
    }

    private List<EventListener> getListeners(Event<?, ?> e) {
        return Collections.unmodifiableList(listeners.getOrDefault(e.getListenerClass(), new ArrayList<>()));
    }
}
