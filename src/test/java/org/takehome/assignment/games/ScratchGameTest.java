package org.takehome.assignment.games;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.takehome.assignment.models.ApplicationContext;
import org.takehome.assignment.models.GameConfig;
import org.takehome.assignment.models.GameResult;
import org.takehome.assignment.utils.GameUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class ScratchGameTest {

    @BeforeEach
    public void before() {
        GameConfig config = GameUtils.readJsonFromResources("config.json", GameConfig.class);

        // Populate the application context
        ApplicationContext context =  ApplicationContext.getInstance();
        context.setConfig(config);
    }

    @Test
    void shouldPlayGame() {
        GameResult result = new ScratchGame().play(100);

        assertThat(result).isNotNull();
        assertThat(result.getMatrix()).isNotEmpty();
        assertThat(result.getAppliedBonusSymbol()).isNotBlank();

        if (result.getReward() > 0) {
            assertThat(result.getReward()).isNotZero();
            assertThat(result.getAppliedWinningCombinations()).isNotEmpty();
        } else {
            assertThat(result.getReward()).isZero();
            assertThat(result.getAppliedWinningCombinations()).isEmpty();
        }
    }

}
