package org.rspeer.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.rspeer.event.listener.EventListener;

public class EventDispatcher {

    private final Map<Class<?>, List<EventListener>> listeners = new ConcurrentHashMap<>();

    public void subscribe(EventListener el) {
        List<Class<?>> interfaces = getEventInterfaces(el.getClass());
        for (Class<?> group : interfaces) {
            if (!listeners.containsKey(group)) {
                listeners.put(group, new CopyOnWriteArrayList<>());
            }
            listeners.get(group).add(el);
        }
    }

    public void unsubscribe(EventListener el) {
        List<Class<?>> interfaces = getEventInterfaces(el.getClass());
        for (Class<?> group : interfaces) {
            if (listeners.containsKey(group)) {
                List<EventListener> listenersList = listeners.get(group);
                listenersList.remove(el);

                if (listenersList.isEmpty()) {
                    listeners.remove(group);
                }
            }
        }
    }

    public void dispatch(Event<?, ?> e) {
        List<EventListener> listenersList = listeners.getOrDefault(e.getListenerClass(), Collections.emptyList());
        for (EventListener listener : listenersList) {
            e.dispatch(listener);
        }
    }

    private List<Class<?>> getEventInterfaces(Class<?> clazz) {
        List<Class<?>> interfaces = new ArrayList<>();
        if (clazz != null) {
            // First we check the direct class interfaces
            for (Class<?> interfase : clazz.getInterfaces()) {
                if (Arrays.asList(interfase.getInterfaces()).contains(EventListener.class)) {
                    if (!interfaces.contains(interfase)) {
                        interfaces.add(interfase);
                        interfaces.addAll(getEventInterfaces(interfase));
                    }
                }
            }
            // Then we check the SuperClass interfaces
            interfaces.addAll(getEventInterfaces(clazz.getSuperclass()));
        }
        return interfaces;
    }
}
