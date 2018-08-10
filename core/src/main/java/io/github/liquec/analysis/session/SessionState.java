/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.liquec.analysis.model.GeotechnicalProperties;
import io.github.liquec.analysis.model.SiteConditions;
import io.github.liquec.analysis.model.StandardPenetrationTest;
import org.apache.commons.lang3.builder.*;

import java.util.ArrayList;
import java.util.List;

public class SessionState {
    private String projectName;
    private String projectLocation;
    private String organization;
    private SiteConditions siteConditions = new SiteConditions();
    private GeotechnicalProperties geotechnicalProperties = new GeotechnicalProperties();
    private List<StandardPenetrationTest> standardPenetrationTestList = new ArrayList<>();

    public SessionState() {
        // No argument constructor to allow use as standard Java Bean
    }

    public SessionState(
        final String projectName,
        final String projectLocation,
        final String organization,
        final SiteConditions siteConditions,
        final GeotechnicalProperties geotechnicalProperties,
        final List<StandardPenetrationTest> standardPenetrationTestList) {
        this.projectName = projectName;
        this.projectLocation = projectLocation;
        this.organization = organization;
        this.siteConditions = siteConditions;
        this.geotechnicalProperties = geotechnicalProperties;
        this.standardPenetrationTestList = standardPenetrationTestList;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }

    public String getProjectLocation() {
        return projectLocation;
    }

    public void setProjectLocation(final String projectLocation) {
        this.projectLocation = projectLocation;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(final String organization) {
        this.organization = organization;
    }

    public SiteConditions getSiteConditions() {
        return siteConditions;
    }

    public void setSiteConditions(final SiteConditions siteConditions) {
        this.siteConditions = siteConditions;
    }

    public GeotechnicalProperties getGeotechnicalProperties() {
        return geotechnicalProperties;
    }

    public void setGeotechnicalProperties(final GeotechnicalProperties geotechnicalProperties) {
        this.geotechnicalProperties = geotechnicalProperties;
    }

    public List<StandardPenetrationTest> getStandardPenetrationTestList() {
        return standardPenetrationTestList;
    }

    public void setStandardPenetrationTestList(final List<StandardPenetrationTest> standardPenetrationTestList) {
        this.standardPenetrationTestList = standardPenetrationTestList;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SessionState that = (SessionState) o;

        return new EqualsBuilder()
            .append(projectName, that.projectName)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(projectName)
            .toHashCode();
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ReflectionToStringBuilder(this, new RecursiveToStringStyle()).toString();
    }
}
