package org.takehome.assignment.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WinCombination {

    @JsonProperty("reward_multiplier")
    private Double rewardMultiplier;
    private String when;
    private Integer count;
    private String group;

    @JsonProperty("covered_areas")
    private String[][] coveredAreas;
}