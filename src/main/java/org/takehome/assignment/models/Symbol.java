package org.takehome.assignment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Symbol {
    @JsonProperty("reward_multiplier")
    private Double rewardMultiplier;
    private String type;
    private String impact;
    private Integer extra;

    @JsonIgnore
    public boolean isBonus() {
        return "bonus".equalsIgnoreCase(type);
    }

}