package org.takehome.assignment.data;

import org.takehome.assignment.models.WinCombination;

public class PopulatedWinCombination extends WinCombination {
    public PopulatedWinCombination(Double rewardMultiplier, String when, Integer count, String group) {
        setRewardMultiplier(rewardMultiplier);
        setWhen(when);
        setCount(count);
        setGroup(group);
    }
}
