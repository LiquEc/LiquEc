/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import io.github.liquec.gui.common.BuildInfo;
import io.github.liquec.gui.common.GuiConstants;
import io.github.liquec.gui.services.WebPageTool;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.inject.Inject;

public class AboutController {
    private final WebPageTool webPageTool;

    public Button buttonClose;

    public Hyperlink linkWebsite;

    public Hyperlink linkJose;

    public Hyperlink linkPablo;

    public Label labelVersion;

    @Inject
    public AboutController(final WebPageTool webPageTool) {
        this.webPageTool = webPageTool;
    }

    public void initialise(final Stage stage) {
        buttonClose.setOnAction(e -> stage.close());
        linkJose.setOnAction(e -> webPageTool.showWebPage(GuiConstants.LINKEDIN_JOSE));
        linkPablo.setOnAction(e -> webPageTool.showWebPage(GuiConstants.LINKEDIN_PABLO));
        linkWebsite.setOnAction(e -> webPageTool.showWebPage(GuiConstants.WEBSITE));
        setupVersion();
    }

    private void setupVersion() {
        String template = labelVersion.getText();
        String text = String.format(template, BuildInfo.version());

        labelVersion.setText(text);
    }
}
