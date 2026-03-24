package com.sv.videosplit.processors;

import com.sv.core.logger.MyLogger;
import com.sv.videosplit.Arguments;
import com.sv.videosplit.BaseProcessor;

/**
 * Created by 44085037 on 16-Oct-17
 */
public class SplitBySize extends BaseProcessor {

    public SplitBySize(MyLogger logger) {
        super(logger);
    }

    /**
     * Remove all occurrence of any digit in file name
     *
     * @param args Object of type Arguments
     * @return String modified file name
     */
    @Override
    protected boolean process(Arguments args) {
        return false;
    }

}
