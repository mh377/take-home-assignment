package org.takehome.assignment.models;

import org.junit.jupiter.api.Test;
import org.takehome.assignment.data.PopulatedSymbol;

import static org.assertj.core.api.Assertions.assertThat;

public class SymbolTest {

    @Test
    void shouldReturnTrueIfSymbolIsBonus() {

        Symbol symbol = new PopulatedSymbol(2.5, "bonus", "multiply_reward");

        assertThat(symbol).isNotNull();
        assertThat(symbol.isBonus()).isTrue();
        assertThat(symbol.getRewardMultiplier()).isEqualTo(2.5);
    }

    @Test
    void shouldReturnFalseIfSymbolIsNotBonus() {

        Symbol symbol = new PopulatedSymbol(1.0, "standard", null);

        assertThat(symbol).isNotNull();
        assertThat(symbol.isBonus()).isFalse();
        assertThat(symbol.getRewardMultiplier()).isEqualTo(1.0);
    }
}
