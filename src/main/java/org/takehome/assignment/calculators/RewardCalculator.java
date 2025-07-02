package org.takehome.assignment.calculators;

import lombok.extern.slf4j.Slf4j;
import org.takehome.assignment.models.*;
import org.takehome.assignment.utils.GameUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class RewardCalculator {

    private static final String MULTIPLY_REWARD = "multiply_reward";
    private static final String EXTRA_BONUS = "extra_bonus";
    private static final String COLON = ":";
    private static final GameConfig config = ApplicationContext.getInstance().getConfig();

    public Reward calculate(Integer bettingAmount, Game game) {

        Map<String, Integer> winningSymbols = game.getWinningSymbolCount();

        // If there are no winning symbols no need to continue
        if (GameUtils.isNullOrEmpty(winningSymbols)) {
            return Reward.builder().reward(0).build();
        }

        Map<String, List<String>> appliedWinCombinations = new HashMap<>();
        addSameSymbolWinCombinations(appliedWinCombinations, winningSymbols);
        addLinearSymbolWinCombinations(appliedWinCombinations, game.getMatrix(), winningSymbols);

        int totalReward = calculateTotalReward(bettingAmount, appliedWinCombinations, game);

        return Reward.builder()
                .appliedWinningCombinations(appliedWinCombinations)
                .reward(totalReward)
                .build();
    }

    private int calculateTotalReward(Integer bettingAmount, Map<String, List<String>> appliedWinCombinations, Game game) {

        double totalReward = 0.0;

        double totalMultiplier;

        for (Map.Entry<String, List<String>> entry: appliedWinCombinations.entrySet()) {

            // Get the reward multiplier for the symbol
            totalMultiplier = config.getSymbols().get(entry.getKey()).getRewardMultiplier();

            for (String winningCombination: entry.getValue()) {
                Double additionalReward = config.getRewardMultiplier(winningCombination);

                // Get the reward multiplier for additional rewards
                totalMultiplier *= additionalReward;
            }

            totalReward += (bettingAmount * totalMultiplier);
        }

        Map<String, Symbol> bonusSymbol = game.getBonusSymbol();

        Symbol symbol = bonusSymbol.entrySet().stream().findFirst().get().getValue();

        if (MULTIPLY_REWARD.equalsIgnoreCase(symbol.getImpact())) {
            totalReward = (totalReward * symbol.getRewardMultiplier());
        }

        if (EXTRA_BONUS.equalsIgnoreCase(symbol.getImpact())) {
            totalReward += symbol.getExtra();
        }

        return (int) totalReward;
    }


    private void addSameSymbolWinCombinations(Map<String, List<String>> appliedSameSymbolWinCombinations, Map<String, Integer> winningSymbols) {

        // Find Winning combinations
        for (Map.Entry<String, Integer> winningSymbol : winningSymbols.entrySet()) {

            String key = winningSymbol.getKey();
            Map<String, WinCombination> winningCombination = config.getSameSymbolWinCombinationByCount(winningSymbol.getValue());

            List<String> winCombinations = new ArrayList<>();
            winCombinations.add(winningCombination.keySet().stream().findFirst().get());

            appliedSameSymbolWinCombinations.put(key, winCombinations);
        }
    }

    private void addLinearSymbolWinCombinations(Map<String, List<String>> appliedWinCombinations, String[][] matrix, Map<String, Integer> winningSymbols) {

        // Find Winning combinations
        for (Map.Entry<String, Integer> winningSymbol : winningSymbols.entrySet()) {

            String symbol = winningSymbol.getKey();
            Map<String, WinCombination>  linearSymbolWinCombinations = config.getLinearSymbolWinCombinations();

            for (Map.Entry<String, WinCombination> linearSymbol : linearSymbolWinCombinations.entrySet()) {
              String[][] coveredAreas = linearSymbol.getValue().getCoveredAreas();

              for (int i = 0; i < coveredAreas.length; i++) {
                  boolean hasWinningCombination = true;
                  for (int j = 0; j < coveredAreas[i].length; j++) {
                     String area = coveredAreas[i][j];
                     String[] data = area.split(COLON);
                     String areaSymbol = matrix[Integer.parseInt(data[0])][Integer.parseInt(data[1])];

                     if (!symbol.equalsIgnoreCase(areaSymbol)) {
                         hasWinningCombination = false;
                         break;
                     }
                  }

                  if (hasWinningCombination) {
                      List<String> winCombinations = appliedWinCombinations.get(symbol);
                      String group = linearSymbol.getValue().getGroup();
                      winCombinations.add(group);
                  }
              }
            }
        }
    }
}
