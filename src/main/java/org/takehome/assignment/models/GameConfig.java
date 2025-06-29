package org.takehome.assignment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.takehome.assignment.utils.GameUtils;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameConfig {

    private Integer columns;
    private Integer rows;
    private Map<String, Symbol> symbols;
    private Probabilities probabilities;

    @JsonProperty("win_combinations")
    private Map<String, WinCombination> winCombinations;

    @JsonIgnore
    public Map<String, Symbol> getStandardSymbols() {
        return symbols.entrySet().stream()
                .filter(entry -> "standard".equalsIgnoreCase(entry.getValue().getType()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    @JsonIgnore
    public Map<String, Symbol> getBonusSymbols() {
        return symbols.entrySet().stream()
                .filter(entry -> "bonus".equalsIgnoreCase(entry.getValue().getType()))
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue
                ));
    }

    @JsonIgnore
    public Map<String, WinCombination> getSameSymbolWinCombinations() {
        return winCombinations.entrySet().stream()
                .filter(entry -> "same_symbols".equalsIgnoreCase(entry.getValue().getGroup()))
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue
                ));
    }

    @JsonIgnore
    public Map<String, WinCombination> getAdditionalWinCombinations() {
        return winCombinations.entrySet().stream()
                .filter(entry -> !"same_symbols".equalsIgnoreCase(entry.getValue().getGroup()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                    )
                );
    }

    @JsonIgnore
    public Map<String, Integer> getBonusSymbolProbabilities() {
        return probabilities.getBonusSymbols().getSymbols();
    }

    @JsonIgnore
    public Map<String, Integer> getStandardSymbolsProbabilities(int currentRow, int currentColumn) {

        GameConfig config = ApplicationContext.getInstance().getConfig();

        // Find the standard symbol probabilities for the current row/column
        Optional<StandardSymbolProbability> standardSymbolProbability = config.getProbabilities().getStandardSymbols().stream()
                .filter(symbol -> symbol.getRow() == currentRow && symbol.getColumn() == currentColumn)
                .findFirst();

        Map<String, Integer> standardSymbols;

        if (standardSymbolProbability.isPresent()) {
            standardSymbols = standardSymbolProbability.get().getSymbols();
        } else {
            // Get default values to avoid nulls if data is incomplete in config.json
            standardSymbols = GameUtils.getDefaultSymbols();
        }

        return standardSymbols;
    }

    @JsonIgnore
    public Map<String, WinCombination> getSameSymbolWinCombinationByCount(Integer count) {

        return winCombinations.entrySet().stream()
                .filter(winCombination -> winCombination.getValue().getCount() != null && winCombination.getValue().getCount().intValue() == count.intValue())
                .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue
         ));
    }

    @JsonIgnore
    public Map<String, WinCombination> getLinearSymbolWinCombinations() {
        return winCombinations.entrySet().stream()
                .filter(entry -> "linear_symbols".equalsIgnoreCase(entry.getValue().getWhen()))
                .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        )
                );
    }

    @JsonIgnore
    public Double getRewardMultiplier(String winCombination) {

        if (GameUtils.isNullOrEmpty(winCombination) || GameUtils.isNullOrEmpty(winCombinations)) {
            return 1.0;
        }

        WinCombination winningCombination =  winCombinations.get(winCombination);

        if (GameUtils.isNullOrEmpty(winningCombination)) {
           return winCombinations.entrySet().stream()
                    .filter(entry -> winCombination.equalsIgnoreCase(entry.getValue().getGroup()))
                    .findFirst()
                    .get().getValue()
                    .getRewardMultiplier();
        }

        return winningCombination.getRewardMultiplier();
    }
}