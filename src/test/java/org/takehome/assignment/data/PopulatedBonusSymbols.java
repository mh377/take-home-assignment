package org.takehome.assignment.data;

import org.takehome.assignment.models.BonusSymbols;

import java.util.Map;

public class PopulatedBonusSymbols extends BonusSymbols {

    public PopulatedBonusSymbols() {

        Map<String, Integer> bonusSymbols = Map.of(
                "10x", 1,
                "5x", 2,
                "+1000", 3,
                "+500", 4,
                "MISS", 5
        );

        setSymbols(bonusSymbols);
    }

}
