package com.sv.videosplit;

import java.io.File;

public class Arguments {

    private SplitInfo splitInfo;
    private String splitBy, fileName, startTime, endTime;
    private int splitSizeInMB;
    //private File file;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public SplitInfo getSplitInfo() {
        return splitInfo;
    }

    public void setSplitInfo(SplitInfo splitInfo) {
        this.splitInfo = splitInfo;
    }

    public String getSplitBy() {
        return splitBy;
    }

    public void setSplitBy(String splitBy) {
        this.splitBy = splitBy;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getSplitSizeInMB() {
        return splitSizeInMB;
    }

    public void setSplitSizeInMB(int splitSizeInMB) {
        this.splitSizeInMB = splitSizeInMB;
    }

    /**
     * Params except file
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Arguments{" +
                "splitInfo=" + splitInfo +
                ", splitBy='" + splitBy + '\'' +
                ", fileName='" + fileName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", splitSizeInMB=" + splitSizeInMB +
                '}';
    }
}
