/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.liquec.analysis.core.GuiTaskHandler;
import io.github.liquec.gui.model.SessionModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.TableView;

import static io.github.liquec.gui.common.FieldValueTool.*;

@SuppressFBWarnings({"NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
public class SessionController {
    private static final Logger LOG = LoggerFactory.getLogger(SessionController.class);

    public GridPane basicDataGridPane;

    public GridPane informationGridPane;

    public GridPane siteConditionsGridPane;

    public TextField textFieldProjectName;

    public TextField textFieldOrganization;

    public TextField textFieldPeakGroundAceleration;

    public TextField textFieldEarthquakeMagnitude;

    public TextField textFieldGroundWaterTableDepth;

    public TableColumn startDepthTableColumn;

    public TableColumn finalDepthTableColumn;

    public TableColumn soilTypeTableColumn;

    public TableColumn aboveGWTTableColumn;

    public TableColumn belowGWTTableColumn;

    public TableColumn finesContentTableColumn;

    public TableColumn liquefactionTableColumn;

    private SessionModel sessionModel;

    public void initialise(final GuiTaskHandler guiTaskHandler, final SessionModel sessionModel) {
        this.sessionModel = sessionModel;
        // Grids
        this.basicDataGridPane.setHgap(15);
        this.informationGridPane.setVgap(10);
        this.siteConditionsGridPane.setVgap(10);
        // Normative Mode
        this.sessionModel.normativeModeProperty().addListener((a, b, c) -> this.trackValues("Normative mode", b.toString(), c.toString()));
        // Project Name
        this.textFieldProjectName.setPromptText("Enter the project name...");
        Bindings.bindBidirectional(textFieldProjectName.textProperty(), this.sessionModel.projectNameProperty());
        this.sessionModel.projectNameProperty().addListener((a, b, c) -> this.manageSessionModelState("Project name", b, c));
        // Organization
        this.textFieldOrganization.setPromptText("Enter the organization name...");
        // Peak Ground Aceleration
        this.textFieldPeakGroundAceleration.setPromptText("Enter the peak ground aceleration...");
        // Earthquake Magnitude
        this.textFieldEarthquakeMagnitude.setPromptText("Enter the earthquake magnitude...");
        // Ground Water Table Depth
        this.textFieldGroundWaterTableDepth.setPromptText("Enter the ground water table depth...");
        // Layer Table

    }

    private void manageSessionModelState(final String name, final String oldValue, final String newValue) {
        this.trackValues(name, oldValue, newValue);
        this.sessionModel.setChangesSaved(false);
        this.sessionModel.checkAbleToCalculate();
    }

    private void trackValues(final String name, final String oldValue, final String newValue) {
        LOG.debug(name + " old value: " + oldValue);
        LOG.debug(name + " new value: " + newValue);
    }

}
