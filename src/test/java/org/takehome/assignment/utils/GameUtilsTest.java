package org.takehome.assignment.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.takehome.assignment.data.PopulatedReward;
import org.takehome.assignment.models.ApplicationContext;
import org.takehome.assignment.models.GameConfig;
import org.takehome.assignment.models.Symbol;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameUtilsTest {

    @BeforeEach
    public void before() {
        GameConfig config = GameUtils.readJsonFromResources("config.json", GameConfig.class);

        // Populate the application context
        ApplicationContext context =  ApplicationContext.getInstance();
        context.setConfig(config);
    }

    @Test
    void shouldReturnTrueIfNullOrEmpty() {
        assertThat(GameUtils.isNullOrEmpty(null)).isTrue();
        assertThat(GameUtils.isNullOrEmpty("")).isTrue();
        assertThat(GameUtils.isNullOrEmpty(new ArrayList<>())).isTrue();
        assertThat(GameUtils.isNullOrEmpty(new HashMap<>())).isTrue();
    }

    @Test
    void shouldReturnTrueIfNotNullOrEmpty() {
        assertThat(GameUtils.isNullOrEmpty(new PopulatedReward())).isFalse();
        assertThat(GameUtils.isNullOrEmpty("same_symbols")).isFalse();
        assertThat(GameUtils.isNullOrEmpty(List.of(new PopulatedReward()))).isFalse();
        assertThat(GameUtils.isNullOrEmpty(Map.of("A", new PopulatedReward()))).isFalse();
    }

    @Test
    void shouldReadJsonFromResources() {
        GameConfig gameConfig = GameUtils.readJsonFromResources("config.json", GameConfig.class);

        assertThat(gameConfig).isNotNull();
        assertThat(gameConfig.getRows()).isEqualTo(4);
        assertThat(gameConfig.getColumns()).isEqualTo(4);
    }

    @Test
    void shouldNotReadJsonFromResourcesIfFileNotFouns() {

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            GameUtils.readJsonFromResources("invalid-config.json", GameConfig.class);
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Failed to read JSON from invalid-config.json");
    }

    @Test
    void shouldGetDefaultSymbols() {
        Map<String, Integer> defaultSymbols =  GameUtils.getDefaultSymbols();

        assertThat(defaultSymbols).isNotEmpty();
        assertThat(defaultSymbols.size()).isEqualTo(6);
        assertThat(defaultSymbols.get("A")).isEqualTo(1);
    }

    @Test
    void shouldGenerateRandomSymbol() {
        Map<String, Integer> defaultSymbols =  GameUtils.getDefaultSymbols();

        Map<String, Symbol> result = GameUtils.generateRandomSymbol(1, defaultSymbols, new Random());

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void shouldGenerateMatrix() {
        String[][] matrix = GameUtils.generateMatrix();

        assertThat(matrix).isNotEmpty();
    }

    @Test
    void shouldFindBonusSymbol() {

        String[][] matrix = GameUtils.generateMatrix();

        Map<String, Symbol> bonusSymbol = GameUtils.findBonusSymbol(matrix);

        assertThat(bonusSymbol).isNotEmpty();

        Symbol symbol = bonusSymbol.entrySet().stream().findFirst().get().getValue();
        assertThat(symbol.isBonus()).isTrue();
    }
}
