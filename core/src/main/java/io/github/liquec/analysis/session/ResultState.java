/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.session;

import io.github.liquec.analysis.model.GeotechnicalProperties;
import io.github.liquec.analysis.model.SptCalculationResult;

import java.util.ArrayList;
import java.util.List;

public class ResultState {

    private String calculationMode;
    private GeotechnicalProperties geotechnicalProperties;
    private List<SptCalculationResult> sptCalculationResultList = new ArrayList<SptCalculationResult>();

    public ResultState() {}

    public String getCalculationMode() {
        return calculationMode;
    }

    public void setCalculationMode(final String calculationMode) {
        this.calculationMode = calculationMode;
    }

    public GeotechnicalProperties getGeotechnicalProperties() {
        return geotechnicalProperties;
    }

    public void setGeotechnicalProperties(final GeotechnicalProperties geotechnicalProperties) {
        this.geotechnicalProperties = geotechnicalProperties;
    }

    public List<SptCalculationResult> getSptCalculationResultList() {
        return sptCalculationResultList;
    }

    public void setSptCalculationResultList(final List<SptCalculationResult> sptCalculationResultList) {
        this.sptCalculationResultList = sptCalculationResultList;
    }
}
