package com.sv.videosplit;

public enum UIName {

    LBL_FILE_TO_SPLIT("File to Split", 'F', "Folder"),
    LBL_START_TIME("Start Time", 'S', "Start Time to split/cut"),
    LBL_END_TIME("End Time", 'E', "End Time to split/cut"),
    BTN_CHNG("Split", 'C', "Split"),
    BTN_BROWSE("Browse", 'B', "Browse folder"),
    BTN_CLEAR("Clear Status", 'S', "Clear status area"),
    BTN_USG("Usage", 'U', "See usage of application");

    String name, tip;
    char mnemonic;

    UIName(String name, char mnemonic) {
        this(name, mnemonic, null);
    }

    UIName(String name, char mnemonic, String tip) {
        this.name = name;
        this.tip = tip;
        this.mnemonic = mnemonic;
    }

}
