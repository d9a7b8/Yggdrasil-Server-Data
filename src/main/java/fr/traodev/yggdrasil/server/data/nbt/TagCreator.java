package fr.traodev.yggdrasil.server.data.nbt;

@FunctionalInterface
public interface TagCreator<R> {
    R create();
}