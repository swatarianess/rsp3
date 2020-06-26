package org.rspeer.event;

import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("rawtypes")
public class EventDispatcher {

    private final Registry registry;
    private final String name;
    private final Processor processor;
    private final Consumer<Throwable> handler;

    private EventDispatcher(String name, Registry registry, Processor processor, Consumer<Throwable> handler) {
        this.name = name;
        this.registry = registry;
        this.processor = processor;
        this.handler = handler;
    }

    @Override
    public String toString() {
        return name;
    }

    public void subscribe(Object instance) {
        registry.subscribe(instance);
    }

    public void unsubscribe(Object instance) {
        registry.unsubscribe(instance);
    }

    public void dispatch(Event event) {
        processor.accept(event, registry.getSubscriptions());
    }

    public static class Factory implements Supplier<EventDispatcher> {

        private final String name;

        private Registry registry = new Registry.Reflective();
        private Processor processor = new Processor.Immediate();
        private Consumer<Throwable> handler = Throwable::printStackTrace;

        public Factory(String name) {
            this.name = name;
        }

        public static EventDispatcher getDefault(String name) {
            return new Factory(name).get();
        }

        public Factory registry(Registry registry) {
            this.registry = registry;
            return this;
        }

        public Factory processor(Processor processor) {
            this.processor = processor;
            return this;
        }

        public Factory handler(Consumer<Throwable> handler) {
            this.handler = handler;
            return this;
        }

        @Override
        public EventDispatcher get() {
            return new EventDispatcher(name, registry, processor, handler);
        }
    }
}
