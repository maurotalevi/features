package org.features.ws.server;

import javax.jws.WebService;

@WebService
public interface Features {
    
    boolean isActive(String name);
    
}

