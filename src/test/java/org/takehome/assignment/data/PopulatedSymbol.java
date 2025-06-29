package org.takehome.assignment.data;

import org.takehome.assignment.models.Symbol;

public class PopulatedSymbol extends Symbol {

    public PopulatedSymbol(Double rewardMultiplier, String type, String impact) {
        setRewardMultiplier(rewardMultiplier);
        setImpact(impact);
        setType(type);
    }
}
