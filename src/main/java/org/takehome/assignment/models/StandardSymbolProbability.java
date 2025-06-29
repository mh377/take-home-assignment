package org.takehome.assignment.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StandardSymbolProbability {
    private int column;
    private int row;
    private Map<String, Integer> symbols;
}