package org.takehome.assignment.data;

import org.takehome.assignment.models.Reward;
import org.takehome.assignment.models.WinCombination;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopulatedReward extends Reward {

    public PopulatedReward(){
        setReward(100);

        Map<String, List<String>> appliedWinningCombinations = Map.of(
                "D", List.of("same_symbol_4_times", "horizontally_linear_symbols"),
                "E", List.of("same_symbol_3_times"),
                "F", List.of("same_symbol_5_times", "vertically_linear_symbols")
        );

        setAppliedWinningCombinations(appliedWinningCombinations);
    }

}


