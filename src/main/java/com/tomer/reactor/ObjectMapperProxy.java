package com.tomer.reactor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

/**
 * A utility class for working with jackson library.
 */
public final class ObjectMapperProxy {
    private static ObjectMapper objectMapper;
    
    /**
     * No-args empty ctor. 
     */
    private ObjectMapperProxy(){}
    
    /**
     * The method returns a singleton object mapper.
     * 
     * @return object mapper instance
     */
    public static ObjectMapper getObjectMapper() {
        if(null == objectMapper)
            objectMapper = JsonMapper.builder().findAndAddModules().build();
        return objectMapper;
    }
}
