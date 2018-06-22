/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.*;
import io.github.liquec.analysis.calculation.Error;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.core.LiquEcException;
import io.github.liquec.analysis.model.SoilLayer;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

import java.util.List;

public class CheckNoLiquefactionRunnable extends Runnable {

    public CheckNoLiquefactionRunnable(final Mode mode, final String description) {
        super(mode, description);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        this.logStart();

        final int layerIndexWithSptInside = Helper.retrieveLayerIndexWithSptInside(sessionState.getGeotechnicalProperties().getSoilLayers(), sptCalculationResult.getDepth());
        LOG.debug(":::::: Layer index with SPT inside: " + layerIndexWithSptInside);

        if (layerIndexWithSptInside == -1) {
            throw new LiquEcException(Error.LAYER_WITH_SPT_INSIDE_NOT_FOUND.getMessage());
        }

        LOG.debug(":::::: Check liquefaction: " + sessionState.getGeotechnicalProperties().getSoilLayers().get(layerIndexWithSptInside).getCheckLiquefaction());
        if (!sessionState.getGeotechnicalProperties().getSoilLayers().get(layerIndexWithSptInside).getCheckLiquefaction()) {
            throw new LiquEcException(Error.NO_LIQUEFACTION.getMessage());
        }

        this.logEnd();
    }

}
