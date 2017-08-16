/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation.ranges.crr;

import io.github.liquec.analysis.calculation.Evaluation;
import io.github.liquec.analysis.calculation.Fines;
import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Range;
import io.github.liquec.analysis.calculation.ranges.eurocode.fines05.EurocodeFines05;
import io.github.liquec.analysis.calculation.ranges.eurocode.fines15.EurocodeFines15;
import io.github.liquec.analysis.calculation.ranges.eurocode.fines35.EurocodeFines35;
import io.github.liquec.analysis.calculation.ranges.ncse.fines05.NcseFines05;
import io.github.liquec.analysis.calculation.ranges.ncse.fines15.NcseFines15;
import io.github.liquec.analysis.calculation.ranges.ncse.fines35.NcseFines35;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public enum CRR {
    LESS_OR_EQUAL(
        LessOrEqual.class,
        new Fines[] {Fines.BOUND_05},
        new java.util.List[]{Arrays.asList(EurocodeFines05.values())},
        new java.util.List[]{Arrays.asList(NcseFines05.values())}),
    BETWEEN_FIRST_SECTION(
        BetweenFirstSection.class,
        new Fines[] {Fines.BOUND_05, Fines.BOUND_15},
        new List[]{Arrays.asList(EurocodeFines05.values()), Arrays.asList(EurocodeFines15.values())},
        new List[]{Arrays.asList(NcseFines05.values()), Arrays.asList(NcseFines15.values())}),
    BETWEEN_SECOND_SECTION(
        BetweenSecondSection.class,
        new Fines[] {Fines.BOUND_15, Fines.BOUND_35},
        new List[]{Arrays.asList(EurocodeFines15.values()), Arrays.asList(EurocodeFines35.values())},
        new List[]{Arrays.asList(NcseFines15.values()), Arrays.asList(NcseFines35.values())}),
    GREATER_OR_EQUAL(
        GreaterOrEqual.class,
        new Fines[] {Fines.BOUND_35},
        new List[]{Arrays.asList(EurocodeFines35.values())},
        new List[]{Arrays.asList(NcseFines35.values())});

    private Class<? extends Range> CRREvaluation;
    private Fines[] fines;
    private Collection<Enum<? extends Evaluation>>[] eurocodeEvaluations;
    private Collection<Enum<? extends Evaluation>>[] nsceEvaluations;

    CRR(final Class<? extends Range> CRREvaluation,
        final Fines[] fines,
        final Collection<Enum<? extends Evaluation>> eurocodeEvaluations[],
        final Collection<Enum<? extends Evaluation>> nsceEvaluations[]) {
        this.CRREvaluation = CRREvaluation;
        this.fines = fines;
        this.eurocodeEvaluations = eurocodeEvaluations;
        this.nsceEvaluations = nsceEvaluations;
    }

    public Class<? extends Range> getCRREvaluationClass() {
        return CRREvaluation;
    }

    public Fines[] getBounds() {
        return fines;
    }

    public Collection<Enum<? extends Evaluation>>[] getEvaluations(final Mode mode) {
        if (Mode.EUROCODE.equals(mode)) {
            return this.eurocodeEvaluations;
        }
        return nsceEvaluations;
    }
}
