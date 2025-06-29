package org.takehome.assignment.calculators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.takehome.assignment.models.ApplicationContext;
import org.takehome.assignment.models.Game;
import org.takehome.assignment.models.GameConfig;
import org.takehome.assignment.models.Reward;
import org.takehome.assignment.utils.GameUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class RewardCalculatorTest {

    @BeforeEach
    public void before() {
        GameConfig config = GameUtils.readJsonFromResources("config.json", GameConfig.class);

        // Populate the application context
        ApplicationContext context =  ApplicationContext.getInstance();
        context.setConfig(config);
    }

    @Test
    void shouldCalculateReward() {

        Reward reward = new RewardCalculator().calculate(100, new Game());

        assertThat(reward).isNotNull();

        if (reward.getReward() > 0) {
            assertThat(reward.getReward()).isNotZero();
            assertThat(reward.getAppliedWinningCombinations()).isNotEmpty();
        } else {
            assertThat(reward.getReward()).isZero();
            assertThat(reward.getAppliedWinningCombinations()).isEmpty();
        }
    }


}
