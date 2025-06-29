package org.takehome.assignment.data;

import org.takehome.assignment.models.Probabilities;
import org.takehome.assignment.models.StandardSymbolProbability;

import java.util.List;

public class PopulatedProbabilities extends Probabilities {

    public PopulatedProbabilities() {
        setBonusSymbols(new PopulatedBonusSymbols());

        List<StandardSymbolProbability> standardSymbolProbabilities = List.of(
                new PopulatedStandardSymbolProbability(0, 0),
                new PopulatedStandardSymbolProbability(0, 1),
                new PopulatedStandardSymbolProbability(0, 2),
                new PopulatedStandardSymbolProbability(1, 0),
                new PopulatedStandardSymbolProbability(1, 1),
                new PopulatedStandardSymbolProbability(1, 2),
                new PopulatedStandardSymbolProbability(2, 0),
                new PopulatedStandardSymbolProbability(2, 1),
                new PopulatedStandardSymbolProbability(2, 2)
        );

        setStandardSymbols(standardSymbolProbabilities);
    }

}
