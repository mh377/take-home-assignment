package org.takehome.assignment.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.takehome.assignment.utils.GameUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
public class Game {

    private static final GameConfig config = ApplicationContext.getInstance().getConfig();

    private String[][] matrix;

    public Map<String, Symbol> bonusSymbol;

    public Map<String, Integer> winningSymbolCount;

    public Game() {
        this.matrix = GameUtils.generateMatrix();
    }

    public Map<String, Symbol> getBonusSymbol() {
        if (!GameUtils.isNullOrEmpty(this.bonusSymbol)) {
            return this.bonusSymbol;
        }

        this.bonusSymbol = GameUtils.findBonusSymbol(this.matrix);
        return this.bonusSymbol;
    }

    public String getBonusSymbolAsText() {
        if (GameUtils.isNullOrEmpty(this.bonusSymbol)) {
            this.bonusSymbol = getBonusSymbol();
        }

        return this.bonusSymbol.keySet().stream().findFirst().get();
    }

    public Map<String, Integer> getWinningSymbolCount() {

        if (!GameUtils.isNullOrEmpty(winningSymbolCount)) {
            return this.winningSymbolCount;
        }

        return Arrays.stream(matrix)
                .flatMap(Arrays::stream) // Flatten the 2D array to a Stream<String>
                .collect(Collectors.groupingBy(
                        symbol -> symbol,
                        Collectors.summingInt(symbol -> 1) // Count occurrences
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() >= 3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }
}
