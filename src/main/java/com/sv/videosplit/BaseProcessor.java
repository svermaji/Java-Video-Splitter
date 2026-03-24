package com.sv.videosplit;

import com.sv.core.Utils;
import com.sv.core.logger.MyLogger;

import static com.sv.core.Constants.SP_DASH_SP;

/**
 * Created by 44085037 on 16-Oct-17
 */
public abstract class BaseProcessor {

    private final MyLogger logger;
    protected static final String FFMPEG_PATH = "C:\\Users\\Shailendra\\Downloads\\ffmpeg-2026-03-18-git-106616f13d-essentials_build\\bin";

    public BaseProcessor(MyLogger logger) {
        this.logger = logger;
    }

    private void printParameters(Arguments args) {
        printParameters(args, true);
    }

    private void printParameters(Arguments args, boolean isInitMsg) {
        logger.info(this.getClass().getName() + SP_DASH_SP
            + (isInitMsg ? "Initialising" : "Finishing") + " process with parameters: [" + args + "]");
    }

    public boolean execute(Arguments args) {
        printParameters(args);
        boolean result = process(args);
        printParameters(args, false);
        return result;
    }

    public void log(String msg) {
        logger.info(this.getClass().getName() + SP_DASH_SP + msg);
    }

    public void checkParamForEmpty (String param, String errMsg) {
        if (!Utils.hasValue(param)) {
            log(errMsg);
            throw new VideoSplitException(errMsg);
        }
    }

    protected abstract boolean process(Arguments args);

}
