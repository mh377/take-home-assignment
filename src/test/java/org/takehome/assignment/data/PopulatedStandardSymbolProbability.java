package org.takehome.assignment.data;

import org.takehome.assignment.models.StandardSymbolProbability;

import java.util.Map;

public class PopulatedStandardSymbolProbability extends StandardSymbolProbability {

    public PopulatedStandardSymbolProbability(int row, int column) {
        setRow(row);
        setColumn(column);

        Map<String, Integer> symbols = Map.of(
                "A", 1,
                "B", 2,
                "C", 3,
                "D", 4,
                "E", 5,
                "F", 6
        );

        setSymbols(symbols);
    }
}
