/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.gui.chart;

import io.github.liquec.gui.model.SptResultRow;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

@SuppressWarnings("unchecked")
public class SptResultTableCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    public SptResultTableCellFactory() {}

    @Override
    public TableCell<S, T> call(final TableColumn<S, T> p) {

        TableCell<S, T> cell = new TableCell<S, T>() {

            @Override
            protected void updateItem(final Object item, final boolean empty) {

                String check = "check";
                String noCheck = "no-check";
                String cssStyle = "";

                SptResultRow sptResultRow = null;
                if (getTableRow() != null) {
                    sptResultRow = (SptResultRow) getTableRow().getItem();
                }

                getStyleClass().remove(check);
                getStyleClass().remove(noCheck);

                super.updateItem((T) item, empty);

                if (sptResultRow != null) {
                    cssStyle = (sptResultRow.isResult()) ? check : noCheck;
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
