package fr.traodev.yggdrasil.server.data.json.interfaces;

@FunctionalInterface
public interface ValueAdder<T> {
    void apply(T json);
}