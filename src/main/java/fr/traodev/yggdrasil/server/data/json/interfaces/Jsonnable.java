package fr.traodev.yggdrasil.server.data.json.interfaces;

import fr.traodev.yggdrasil.server.data.json.YggdrasilJson;

public interface Jsonnable {
    void toJson(final YggdrasilJson json);
}