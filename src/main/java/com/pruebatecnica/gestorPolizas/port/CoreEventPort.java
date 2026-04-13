package com.pruebatecnica.gestorPolizas.port;

import java.util.Map;

public interface CoreEventPort {
    void sendEvent(Map<String, Object> event);
}