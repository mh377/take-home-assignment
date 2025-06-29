package org.takehome.assignment.utils;

import lombok.extern.slf4j.Slf4j;
import org.takehome.assignment.models.ApplicationContext;
import org.takehome.assignment.models.GameConfig;
import org.takehome.assignment.models.Symbol;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Slf4j
public final class GameUtils {
    private GameUtils(){}

    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof String) {
            return ((String) obj).isEmpty();
        }

        if (obj instanceof Collection<?>) {
            return ((Collection<?>) obj).isEmpty();
        }

        if (obj instanceof Map<?, ?>) {
            return ((Map<?, ?>) obj).isEmpty();
        }

        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }

        return false;
    }

    public static <T> T readJsonFromResources(String fileName, Class<T> type) {
        try (InputStream inputStream = GameUtils.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found in resources: " + fileName);
            }
            return ApplicationContext.getInstance().getMapper().readValue(inputStream, type);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON from " + fileName, e);
        }
    }

    public static Map<String, Integer> getDefaultSymbols() {
        // Had to add this method because the data in the config.json is incomplete
        // for a 4 x 4 matrix and the values would be null
        return Map.of(
                "A", 1,
                "B", 2,
                "C", 3,
                "D", 4,
                "E", 5,
                "F", 6
        );
    }

    public static Map<String, Symbol> generateRandomSymbol(int bonusSymbolCount, Map<String, Integer> weightedSymbols, Random random) {
        Map<String, Symbol> randomSymbol = pickRandomSymbol(weightedSymbols, random);

        Symbol symbol = randomSymbol.entrySet().stream().findFirst().get().getValue();

        // If there is already a bonus symbol in the matrix, generate a new symbol that isn't a bonus symbol
        if (symbol.isBonus() && bonusSymbolCount > 0) {
            return generateRandomSymbol(bonusSymbolCount, weightedSymbols, random);
        }

        return randomSymbol;
    }

    private static Map<String, Symbol> pickRandomSymbol(Map<String, Integer> weightedSymbols, Random random) {

        // Calculate a total weight of values of each symbol
        int totalWeight = weightedSymbols.values().stream().mapToInt(Integer::intValue).sum();
        int randomNumber = random.nextInt(totalWeight) + 1;

        int total = 0;
        for (Map.Entry<String, Integer> entry : weightedSymbols.entrySet()) {
            total += entry.getValue();
            if (randomNumber <= total) {
                Symbol symbol = ApplicationContext.getInstance().getConfig().getSymbols().get(entry.getKey());
                return Map.of(entry.getKey(), symbol);
            }
        }

        throw new RuntimeException("Unable to generate a random symbol");
    }

    public static String[][] generateMatrix() {

        GameConfig config = ApplicationContext.getInstance().getConfig();

        // Get the number of rows and columns
        int numberOfRows = config.getRows();
        int numberOfColumns = config.getColumns();

        String[][] matrix = new String[numberOfRows][numberOfColumns];

        // Get the bonus symbols
        Map<String, Integer> bonusSymbolProbabilities = config.getBonusSymbolProbabilities();

        int bonusSymbolCount = 0;

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {

                // Get the standard symbols for the current row/column
                Map<String, Integer> standardSymbolsProbabilities = config.getStandardSymbolsProbabilities(i, j);

                // Combine the bonus and standard symbols
                Map<String, Integer> symbolProbabilities = new HashMap<>();
                symbolProbabilities.putAll(standardSymbolsProbabilities);
                symbolProbabilities.putAll(bonusSymbolProbabilities);

                // Randomly generate a symbol based on probability in current square
                Map<String, Symbol> randomSymbol = GameUtils.generateRandomSymbol(bonusSymbolCount, symbolProbabilities, new Random());

                // Get the key and symbol
                String key = randomSymbol.keySet().stream().findFirst().get();
                Symbol symbol = randomSymbol.entrySet().stream().findFirst().get().getValue();

                // Only add 1 bonus symbol per game
                if (symbol.isBonus() && bonusSymbolCount == 0) {
                    bonusSymbolCount++;
                }

                // Add the symbol to the matrix
                matrix[i][j] = key;
            }
        }
        return matrix;
    }

    public static Map<String, Symbol> findBonusSymbol(String[][] matrix) {

        GameConfig config = ApplicationContext.getInstance().getConfig();
        Map<String, Symbol> bonusSymbols =  config.getBonusSymbols();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {

                Symbol symbol = bonusSymbols.get(matrix[i][j]);

                if (!isNullOrEmpty(symbol)) {
                    return Map.of(matrix[i][j], symbol);
                }
            }
        }

        return null;
    }
}
