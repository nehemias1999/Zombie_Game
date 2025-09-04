package com.zombie.resources;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Message implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private final Map<String, Serializable> data;

    private Message(Map<String, Serializable> datos) {
        this.data = Collections.unmodifiableMap(new HashMap<>(datos));
    }

    public <T extends Serializable> T get(String key, Class<T> type) {
        Serializable raw = data.get(key);
        return raw == null ? null : (T) type.cast(raw);
    }
        
    public static class Builder {
        private final Map<String, Serializable> datos = new HashMap<>();

        public Builder put(String clave, Serializable valor) {
            datos.put(clave, valor);
            return this;
        }
        
        public Message build() {
            return new Message(datos);
        }
    }

}

