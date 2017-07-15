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

    public Label soilUnitWeightLabel;

    public GridPane basicDataGridPane;

    public GridPane informationGridPane;

    public GridPane siteConditionsGridPane;

    public TextField textFieldProjectName;

    public TextField textFieldOrganization;

    public TextField textFieldPeakGroundAceleration;

    public TextField textFieldEarthquakeMagnitude;

    public TextField textFieldGroundWaterTableDepth;



    private SessionModel sessionModel;

    public void initialise(final GuiTaskHandler guiTaskHandler, final SessionModel sessionModel) {
        this.sessionModel = sessionModel;
        // Labels
//        soilUnitWeightLabel.setText("Soil Unit Weight (KN/m<sup>3</sup>)");
        System.out.println("Soil Unit Weight (KN/m" + '\u2073' + ")");
        // Normative Mode
        this.sessionModel.normativeModeProperty().addListener((a, b, c) -> this.trackValues("Normative Mode", b.toString(), c.toString()));
        // Project Name
        Bindings.bindBidirectional(this.textFieldProjectName.textProperty(), this.sessionModel.projectNameProperty());
        this.sessionModel.projectNameProperty().addListener((a, b, c) -> this.manageSessionModelState("Project Name", b, c));
        // Organization
        Bindings.bindBidirectional(this.textFieldOrganization.textProperty(), this.sessionModel.organizationProperty());
        this.sessionModel.organizationProperty().addListener((a, b, c) -> this.manageSessionModelState("Organization", b, c));
        // Peak Ground Aceleration
        Bindings.bindBidirectional(this.textFieldPeakGroundAceleration.textProperty(), this.sessionModel.peakGroundAcelerationProperty());
        this.textFieldPeakGroundAceleration.textProperty().addListener((a, b, c) -> this.validateNumberValue(this.textFieldPeakGroundAceleration,"\\d{0,1}([\\.]\\d{0,2})?", b, c));
        this.sessionModel.peakGroundAcelerationProperty().addListener((a, b, c) -> this.manageSessionModelState("Peak Ground Aceleration", b, c));
        // Earthquake Magnitude
        Bindings.bindBidirectional(this.textFieldEarthquakeMagnitude.textProperty(), this.sessionModel.earthquakeMagnitudeProperty());
        this.textFieldEarthquakeMagnitude.textProperty().addListener((a, b, c) -> this.validateNumberValue(this.textFieldEarthquakeMagnitude,"\\d{0,1}([\\.]\\d{0,1})?", b, c));
        this.sessionModel.earthquakeMagnitudeProperty().addListener((a, b, c) -> this.manageSessionModelState("Earthquake Magnitude", b, c));
        // Ground Water Table Depth
        Bindings.bindBidirectional(this.textFieldGroundWaterTableDepth.textProperty(), this.sessionModel.groundWaterTableDepthProperty());
        this.textFieldGroundWaterTableDepth.textProperty().addListener((a, b, c) -> this.validateNumberValue(this.textFieldPeakGroundAceleration,"\\d{0,1}([\\.]\\d{0,2})?", b, c));
        this.sessionModel.groundWaterTableDepthProperty().addListener((a, b, c) -> this.manageSessionModelState("Ground Water Table Depth", b, c));
        // Layer Table

    }

    private void manageSessionModelState(final String name, final String oldValue, final String newValue) {
        this.trackValues(name, oldValue, newValue);
        this.sessionModel.setChangesSaved(false);
        this.sessionModel.checkAbleToCalculate();
    }

    private void validateNumberValue(final TextField textField, final String regex, final String oldValue, final String newValue) {
        if (!newValue.matches(regex)) {
            textField.setText(oldValue);
        }
    }

    private void trackValues(final String name, final String oldValue, final String newValue) {
        LOG.debug(name + " old value: " + oldValue);
        LOG.debug(name + " new value: " + newValue);
    }

}
