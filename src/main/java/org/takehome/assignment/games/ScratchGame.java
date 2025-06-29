package org.takehome.assignment.games;

import lombok.extern.slf4j.Slf4j;
import org.takehome.assignment.adapters.GameResultAdapter;
import org.takehome.assignment.models.*;
import org.takehome.assignment.calculators.RewardCalculator;

@Slf4j
public class ScratchGame {

    public GameResult play(Integer bettingAmount) {

        // Generate new game
        Game game = new Game();

        // Calculate Winnings
        Reward reward =  new RewardCalculator().calculate(bettingAmount, game);

        // Populate Result
        GameResult result = new GameResultAdapter(game, reward).convert();

        log.debug("Successfully Generated a new scratch game result");

        return result;
    }
}
