/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.gui.chart;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.gui.model.SptResultRow;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.Optional;

@SuppressWarnings("unchecked")
public class SptResultTableCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    public SptResultTableCellFactory() {}

    @Override
    public TableCell<S, T> call(final TableColumn<S, T> p) {

        TableCell<S, T> cell = new TableCell<S, T>() {

            @Override
            protected void updateItem(final Object item, final boolean empty) {

                final String check = "check";
                final String checkBelowSf = "check-below-sf";
                final String checkBelowOne = "check-below-one";
                final String noCheck = "no-check";
                String cssStyle = "";

                SptResultRow sptResultRow = null;
                if (getTableRow() != null) {
                    sptResultRow = (SptResultRow) getTableRow().getItem();
                }

                getStyleClass().remove(check);
                getStyleClass().remove(checkBelowSf);
                getStyleClass().remove(checkBelowOne);
                getStyleClass().remove(noCheck);

                super.updateItem((T) item, empty);

                if (sptResultRow != null) {
                    if (!sptResultRow.isResult()) {
                        cssStyle = noCheck;
                    } else {
                        final Mode mode = Mode.getMode(sptResultRow.getMode());
                        Double safetyFactor;
                        try {
                            safetyFactor = Double.valueOf(sptResultRow.getSafetyFactor());
                        } catch (NumberFormatException e) {
                            safetyFactor = 0.0;
                        }
                        if (safetyFactor < 1) {
                            cssStyle = checkBelowOne;
                        } else if (safetyFactor < Optional.ofNullable(mode).map(Mode::getSafetyFactor).orElse(1.25f)) {
                            cssStyle = checkBelowSf;
                        } else {
                            cssStyle = check;
                        }
                    }
                }

                getStyleClass().add(cssStyle);
                if (item != null) {
                    setText(item.toString());
                } else {
                    setText("");
                }
            }
        };

        return cell;
    }
}
