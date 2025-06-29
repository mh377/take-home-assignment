package org.takehome.assignment.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import static java.util.Objects.isNull;

public final class ApplicationContext {
    private static final ApplicationContext instance = new ApplicationContext();

    private static final ObjectMapper mapper = new ObjectMapper();

    private GameConfig config;

    private ApplicationContext() {
        // private constructor to enforce singleton pattern
    }

    public static ApplicationContext getInstance() {
        return instance;
    }

    public void setConfig(GameConfig config) {
        this.config = config;
    }

    public GameConfig getConfig() {

        if (isNull(this.config)) {
            throw new RuntimeException("Game config has not be set");
        }

        return this.config;
    }

    public ObjectMapper getMapper(){
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

}
