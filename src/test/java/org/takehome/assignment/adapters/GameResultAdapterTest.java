package org.takehome.assignment.adapters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.takehome.assignment.data.PopulatedReward;
import org.takehome.assignment.models.*;
import org.takehome.assignment.utils.GameUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class GameResultAdapterTest {
    private static final GameConfig config = GameUtils.readJsonFromResources("config.json", GameConfig.class);

    @BeforeEach
    public void before() {
        // Populate the application context
        ApplicationContext context =  ApplicationContext.getInstance();
        context.setConfig(config);
    }

    @Test
    void shouldReturnGameResult() {

        Game game = new Game();
        Reward reward = new PopulatedReward();

        GameResult result = new GameResultAdapter(game, reward).convert();

        assertThat(result).isNotNull();
        assertThat(result.getMatrix()).isNotEmpty();
        assertThat(result.getReward()).isEqualTo(reward.getReward());
        assertThat(result.getAppliedBonusSymbol()).isNotBlank();
    }

    @Test
    void shouldNotReturnGameResultIfGameIsNull() {
        Reward reward = new PopulatedReward();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new GameResultAdapter(null, reward).convert();
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Unable to generate scratch game result");
    }

    @Test
    void shouldNotReturnGameResultIfGameMatrixIsNull() {
        Game game = new Game();
        Reward reward = new PopulatedReward();
        game.setMatrix(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new GameResultAdapter(game, reward).convert();
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Unable to generate scratch game result");
    }

    @Test
    void shouldNotReturnGameResultIfRewardIsNull() {
        Game game = new Game();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new GameResultAdapter(game, null).convert();
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Unable to generate scratch game result");
    }

}
