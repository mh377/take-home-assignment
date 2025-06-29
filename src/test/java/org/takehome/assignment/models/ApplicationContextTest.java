package org.takehome.assignment.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.takehome.assignment.utils.GameUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ApplicationContextTest {

    private static final GameConfig config = GameUtils.readJsonFromResources("config.json", GameConfig.class);


    @BeforeEach
    public void before() {
        // Populate the application context
        ApplicationContext context =  ApplicationContext.getInstance();
        context.setConfig(config);
    }

    @Test
    void shouldGetConfig() {
        GameConfig config = ApplicationContext.getInstance().getConfig();

        assertThat(config).isNotNull();
        assertThat(config.getRows()).isEqualTo(config.getRows());
        assertThat(config.getColumns()).isEqualTo(config.getColumns());
        assertThat(config.getSymbols()).isEqualTo(config.getSymbols());
    }

    @Test
    void shouldGetMapper(){
        ObjectMapper mapper = ApplicationContext.getInstance().getMapper();

        assertThat(mapper).isNotNull();
    }
}
