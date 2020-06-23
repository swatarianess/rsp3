package org.rspeer.event;

import java.lang.invoke.MethodHandle;

@SuppressWarnings("rawtypes")
public class Subscription {

    private final Class<?> type;
    private final Object reference;
    private final MethodHandle address;

    public Subscription(Class<?> type, Object reference, MethodHandle address) {
        this.type = type;
        this.reference = reference;
        this.address = address;
    }

    public void delegate(Event event) {
        try {
            address.invoke(reference, event);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public Class<?> getType() {
        return type;
    }

    public Object getReference() {
        return reference;
    }

    public MethodHandle getAddress() {
        return address;
    }
}