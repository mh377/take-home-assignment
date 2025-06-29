package org.takehome.assignment.data;

import org.takehome.assignment.models.GameConfig;
import org.takehome.assignment.models.Symbol;
import org.takehome.assignment.models.WinCombination;

import java.util.Map;

public class PopulatedGameConfig extends GameConfig {

    public PopulatedGameConfig() {
        setColumns(3);
        setColumns(3);
        setSymbols(getPopulatedSymbols());
        setProbabilities(new PopulatedProbabilities());
        setWinCombinations(getPopulatedWinCombinations());
    }

    private Map<String, WinCombination> getPopulatedWinCombinations() {
        return Map.of(
                "same_symbol_3_times", new PopulatedWinCombination(1.0, "same_symbols", 3, "same_symbols"),
                "same_symbol_4_times", new PopulatedWinCombination(1.5, "same_symbols", 4, "same_symbols"),
                "same_symbol_5_times", new PopulatedWinCombination(2.0, "same_symbols", 5, "same_symbols"),
                "same_symbol_6_times", new PopulatedWinCombination(3.0, "same_symbols", 6, "same_symbols"),
                "same_symbol_7_times", new PopulatedWinCombination(5.0, "same_symbols", 7, "same_symbols"),
                "same_symbol_8_times", new PopulatedWinCombination(10.0, "same_symbols", 8, "same_symbols"),
                "same_symbol_9_times", new PopulatedWinCombination(20.0, "same_symbols", 9, "same_symbols"),
                "same_symbols_horizontally", new PopulatedWinCombination(2.0, "linear_symbols", null, "horizontally_linear_symbols"),
                "same_symbols_vertically", new PopulatedWinCombination(2.0, "linear_symbols", null, "vertically_linear_symbols"),
                "same_symbols_diagonally_left_to_right", new PopulatedWinCombination(5.0, "linear_symbols", null, "ltr_diagonally_linear_symbols")
        );
    }

    private Map<String, Symbol> getPopulatedSymbols() {
        return Map.of(
                "A", new PopulatedSymbol(5.0, "standard", null),
                "B", new PopulatedSymbol(2.0, "standard", null),
                "C", new PopulatedSymbol(2.5, "standard", null),
                "D", new PopulatedSymbol(2.0, "standard", null),
                "E", new PopulatedSymbol(1.2, "standard", null),
                "F", new PopulatedSymbol(1.0, "standard", null),
                "10x", new PopulatedSymbol(10.0, "bonus", null),
                "5x", new PopulatedSymbol(5.0, "bonus", null)
                );
    }
}
