package io.github.liquec.analysis.model;

public enum CalculationModeEnum {
    EUROCODE("Eurocode"),
    NCSE02("NCSE-02");

    private String mode;

    CalculationModeEnum(final String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}
