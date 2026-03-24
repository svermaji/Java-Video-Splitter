package com.sv.videosplit;

public enum SplitInfo {

    //TODO: Take names from processor package and use match case

    CUT_BY_TIME("Cut By Time", "CutByTime", "jpCutByTime"),
    BY_TIME("Split By Time", "SplitByTime", "jpByTime"),
    BY_SIZE("Split By Size", "SplitBySize", "jpBySize");

    private final String label, value, panelName;

    SplitInfo(String label, String value, String panelName) {
        this.label = label;
        this.value = value;
        this.panelName = panelName;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    /**
     * This represents actual field defined in class
     * @return name
     */
    public String getPanelName() {
        return panelName;
    }

    public String toString() {
        return getLabel();
    }
}

class SplitComparator implements java.util.Comparator<SplitInfo> {
    public int compare(SplitInfo left, SplitInfo right) {
        return left.getLabel().compareTo(right.getLabel());
    }
}
