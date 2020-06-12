package org.rspeer.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import org.rspeer.event.listener.EventListener;

public class EventDispatcher {

    private final Map<Class<?>, List<EventListener>> listeners = new ConcurrentHashMap<>();

    public void subscribe(EventListener el) {
        List<Class<?>> interfaces = getListenerInterfaces(el.getClass());
        interfaces.forEach(group -> {
            if (!listeners.containsKey(group)) {
                listeners.put(group, new CopyOnWriteArrayList<>());
            }
            listeners.get(group).add(el);
        });
    }

    public void unsubscribe(EventListener el) {
        List<Class<?>> interfaces = getListenerInterfaces(el.getClass());
        interfaces.forEach(group -> {
            if (listeners.containsKey(group)) {
                List<EventListener> listenersList = listeners.get(group);
                listenersList.remove(el);

                if (listenersList.isEmpty()) {
                    listeners.remove(group);
                }
            }
        });
    }

    public void dispatch(Event<?, ?> e) {
        Class<?> group = e.getListenerClass();
        List<EventListener> listenersList = listeners.getOrDefault(group, Collections.emptyList());
        listenersList.forEach(e::dispatch);
    }

    private List<Class<?>> getListenerInterfaces(Class<?> clazz) {
        List<Class<?>> listenerInterfaces = new ArrayList<>();
        if (clazz != null) {
            // First we check the directly implemented interfaces
            List<Class<?>> impl = Arrays.stream(clazz.getInterfaces())
                                        .filter(this::isEventListenerInterface)
                                        .collect(Collectors.toList());
            listenerInterfaces.addAll(impl);
            // Then we check interfaces implemented by the SuperClass
            Class<?> superClass = clazz.getSuperclass();
            listenerInterfaces.addAll(getListenerInterfaces(superClass));
        }
        return listenerInterfaces;
    }

    private boolean isEventListenerInterface(Class<?> clazz) {
        if (clazz.isInterface()) {
            // First we check if the class itself is an EventListener
            if (clazz.equals(EventListener.class)) {
                return true;
            }
            // Then we check if any of the SuperInterfaces is an EventListener
            return Arrays.stream(clazz.getInterfaces())
                         .anyMatch(this::isEventListenerInterface);
        }
        return false;
    }
}
