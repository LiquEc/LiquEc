/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.model;

public enum SoilType {
    SAND(1, "Sand"),
    SANDY_SILT(2, "Sandy Silt"),
    SILT(3, "Silt");

    private Integer code;
    private String description;

    SoilType(final Integer code, final String description) {
        this.code = code;
        this.description = description;
    }

    Integer getCode() {
        return this.code;
    }

    String getDescription() {
        return this.description;
    }

    public SoilType getSoilType(final Integer code) {
        for (SoilType soilType: SoilType.values()) {
            if (soilType.getCode().equals(code)) {
                return soilType;
            }
        }
        return null;
    }
}
