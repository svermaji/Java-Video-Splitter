package com.sv.videosplit;

public enum UIName {

    LBL_FOLDER("Folder", 'F', "Folder"),
    LBL_ACTION("Action", 'A', "Select action"),
    LBL_LOADING("Loading", 'L', "Loading, please wait"),
    LBL_P1("Param1", '1', "Parameter - 1st"),
    LBL_P2("Param2", '2', "Parameter - 2nd"),
    LBL_EXTN("File extension", 'E', "Extension of files to be processed"),
    SUB_FOLDER("Sub-folders", 'O', "If selected process files from root and all sub-folders else only files from the root folder"),
    PROCESS_FOLDER("Process folder", 'R', "Will process folder names also and convert them to title case if selected (only for CONVERT_TO_TITLE_CASE Action)"),
    //OVERWRITE_MP3_TAG ("Overwrite MP3 tag info", true, "Process sub-folders if selected else only files of the folder"),
    APPEND_FOLDER("Append folder", 'D', "If selected process files from root and all sub-folders else only files from the root folder"),
    //ID3V2TTAG ("Update ID3v2Tag also", true, "Process sub-folders if selected else only files of the folder"),
    //MP3_TITLE_TAG ("Modify Title Tag", false, "Process sub-folders if selected else only files of the folder");
    BTN_PRVW("Preview", 'P', "Preview changes that will be applied"),
    BTN_CHNG("Change", 'C', "Apply changes"),
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
