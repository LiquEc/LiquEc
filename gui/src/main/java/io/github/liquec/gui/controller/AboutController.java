/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.liquec.gui.common.BuildInfo;
import io.github.liquec.gui.common.GuiConstants;
import io.github.liquec.gui.services.WebPageTool;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.inject.Inject;

@SuppressFBWarnings({"NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
public class AboutController {
    private final WebPageTool webPageTool;

    public Button buttonClose;

    public Hyperlink linkWebsite;

    public Hyperlink linkTwitter;

    public Label labelVersion;

    @Inject
    public AboutController(final WebPageTool webPageTool) {
        this.webPageTool = webPageTool;
    }

    public void initialise(final Stage stage) {
        buttonClose.setOnAction(e -> stage.close());
        linkWebsite.setOnAction(e -> webPageTool.showWebPage(GuiConstants.WEBSITE));
        linkTwitter.setOnAction(e -> webPageTool.showWebPage(GuiConstants.TWITTER));
        setupVersion();
    }

    private void setupVersion() {
        String template = labelVersion.getText();
        String text = String.format(template, BuildInfo.version());

        labelVersion.setText(text);
    }
}
