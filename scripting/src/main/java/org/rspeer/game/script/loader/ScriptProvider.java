package org.rspeer.game.script.loader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import org.rspeer.game.script.Script;
import org.rspeer.game.script.ScriptMeta;

public interface ScriptProvider extends Predicate<Class<?>> {

    ScriptBundle load();

    default Script define(ScriptSource source) {
        try {
            return source.getTarget().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    }

    @Override
    default boolean test(Class<?> clazz) {
        return !Modifier.isAbstract(clazz.getModifiers())
               && Script.class.isAssignableFrom(clazz)
               && clazz.isAnnotationPresent(ScriptMeta.class);
    }
}
