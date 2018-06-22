/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.gui.common;

import org.apache.commons.lang.StringUtils;

public enum LiquefactionEnum {
    YES(true, "yes"),
    NO(false, "no");

    private boolean liquefaction;
    private String description;

    LiquefactionEnum(final boolean liquefaction, final String description) {
        this.liquefaction = liquefaction;
        this.description = description;
    }

    public boolean isLiquefaction() {
        return liquefaction;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescription(final boolean liquefaction) {
        for (LiquefactionEnum liquefactionEnum: LiquefactionEnum.values()) {
            if (liquefactionEnum.isLiquefaction() == liquefaction) {
                return liquefactionEnum.getDescription();
            }
        }
        return null;
    }

    public static Boolean getLiquefaction(final String description) {
        if (StringUtils.isEmpty(description)) {
            return null;
        }
        for (LiquefactionEnum liquefactionEnum: LiquefactionEnum.values()) {
            if (liquefactionEnum.getDescription().equals(description)) {
                return liquefactionEnum.isLiquefaction();
            }
        }
        return null;
    }

}
