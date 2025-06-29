package org.takehome.assignment.adapters;

import lombok.AllArgsConstructor;
import org.takehome.assignment.models.Game;
import org.takehome.assignment.models.GameResult;
import org.takehome.assignment.models.Reward;

import static java.util.Objects.isNull;

@AllArgsConstructor
public class GameResultAdapter {

    private Game matrix;

    private Reward reward;

    public GameResult convert(){

        if (isNull(matrix) || isNull(matrix.getMatrix()) || isNull(reward)) {
            throw new IllegalArgumentException("Unable to generate scratch game result");
        }

        GameResult.GameResultBuilder builder =  GameResult.builder();
        builder.matrix(matrix.getMatrix());
        builder.appliedBonusSymbol(matrix.getBonusSymbolAsText());
        builder.reward(reward.getReward());
        builder.appliedWinningCombinations(reward.getAppliedWinningCombinations());

        return builder.build();
    }
}
