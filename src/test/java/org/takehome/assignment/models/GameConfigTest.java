package org.takehome.assignment.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.takehome.assignment.data.PopulatedGameConfig;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GameConfigTest {

    private static final GameConfig config = new PopulatedGameConfig();

    @BeforeEach
    void before(){
        ApplicationContext context =  ApplicationContext.getInstance();
        context.setConfig(config);
    }

    @Test
    void shouldReturnStandardSymbols() {

       Map<String, Symbol> standardSymbols = config.getStandardSymbols();

       assertThat(standardSymbols).isNotNull();
       assertThat(standardSymbols.size()).isEqualTo(6);

       for (Map.Entry<String, Symbol> entry: standardSymbols.entrySet()) {
            assertThat(entry.getValue().getType()).isEqualTo("standard");
       }
    }

    @Test
    void shouldReturnBonusSymbols() {

        Map<String, Symbol> bonusSymbols = config.getBonusSymbols();

        assertThat(bonusSymbols).isNotNull();
        assertThat(bonusSymbols.size()).isEqualTo(2);

        for (Map.Entry<String, Symbol> entry: bonusSymbols.entrySet()) {
            assertThat(entry.getValue().getType()).isEqualTo("bonus");
        }
    }

    @Test
    void shouldReturnSameSymbolWinCombinations() {
        Map<String, WinCombination> sameSymbolWinCombinations = config.getSameSymbolWinCombinations();

        assertThat(sameSymbolWinCombinations).isNotNull();
        assertThat(sameSymbolWinCombinations.size()).isEqualTo(7);

        for (Map.Entry<String, WinCombination> entry: sameSymbolWinCombinations.entrySet()) {
            assertThat(entry.getValue().getWhen()).isEqualTo("same_symbols");
        }
    }

    @Test
    void shouldReturnAdditionalWinCombinations() {
        Map<String, WinCombination> additionalWinCombinations = config.getAdditionalWinCombinations();

        assertThat(additionalWinCombinations).isNotNull();
        assertThat(additionalWinCombinations.size()).isEqualTo(3);

        for (Map.Entry<String, WinCombination> entry: additionalWinCombinations.entrySet()) {
            assertThat(entry.getValue().getWhen()).isEqualTo("linear_symbols");
        }
    }

    @Test
    void shouldReturnBonusSymbolProbabilities() {

        Map<String, Integer> bonusSymbolProbabilities =  config.getBonusSymbolProbabilities();

        assertThat(bonusSymbolProbabilities).isNotNull();
        assertThat(bonusSymbolProbabilities.size()).isEqualTo(5);
    }

    @Test
    void shouldReturnStandardSymbolsProbabilities(){
        Map<String, Integer> standardSymbolsProbabilities = config.getStandardSymbolsProbabilities(0, 0);

        assertThat(standardSymbolsProbabilities).isNotNull();
        assertThat(standardSymbolsProbabilities.size()).isEqualTo(6);
    }

    @Test
    void shouldSameSymbolWinCombinationByCount() {
        Map<String, WinCombination> sameSymbolWinCombination =  config.getSameSymbolWinCombinationByCount(3);

        assertThat(sameSymbolWinCombination).isNotNull();
        assertThat(sameSymbolWinCombination.size()).isEqualTo(1);

        for (Map.Entry<String, WinCombination> entry: sameSymbolWinCombination.entrySet()) {
            assertThat(entry.getValue().getRewardMultiplier()).isEqualTo(1);
            assertThat(entry.getValue().getWhen()).isEqualTo("same_symbols");
            assertThat(entry.getValue().getCount()).isEqualTo(3);
            assertThat(entry.getValue().getGroup()).isEqualTo("same_symbols");
        }
    }

    @Test
    void shouldReturnLinearSymbolWinCombinations() {
        Map<String, WinCombination> sameSymbolWinCombination =  config.getLinearSymbolWinCombinations();

        assertThat(sameSymbolWinCombination).isNotNull();
        assertThat(sameSymbolWinCombination.size()).isEqualTo(3);

        for (Map.Entry<String, WinCombination> entry: sameSymbolWinCombination.entrySet()) {
            assertThat(entry.getValue().getWhen()).isEqualTo("linear_symbols");
        }
    }

    @Test
    void shouldReturnRewardMultiplier() {
        Double rewardMultiplier = config.getRewardMultiplier("same_symbol_4_times");

        assertThat(rewardMultiplier).isEqualTo(1.5);
    }

    @Test
    void shouldReturnRewardMultiplierIfWinCombinationIsNull() {
        Double rewardMultiplier = config.getRewardMultiplier(null);

        assertThat(rewardMultiplier).isEqualTo(1.0);
    }

}
