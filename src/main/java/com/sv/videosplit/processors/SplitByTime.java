package com.sv.videosplit.processors;

import com.sv.core.logger.MyLogger;
import com.sv.videosplit.Arguments;
import com.sv.videosplit.BaseProcessor;

/**
 * This processor will split file into two by given time.
 * Created by 44085037 on 16-Oct-17
 */
public class SplitByTime extends BaseProcessor {

    public SplitByTime(MyLogger logger) {
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
        checkParamForEmpty(args.getFileName(),
                "Parameter file is null.");

        // Split a 10-second segment starting from 00:00:05
        /*FFmpeg.atPath(Paths.get(FFMPEG_PATH))
                .addInput(UrlInput.fromUrl(args.getFileName())
                        //.setPosition(0, TimeUnit.HOURS) // Start time
                        .setPosition(0, TimeUnit.HOURS) // Start time
                        .setDuration(1, TimeUnit.HOURS)) // Duration of the clip
                .addOutput(UrlOutput.toPath(Paths.get(outputPath))
                        //.setVideoCodec("copy") // Sets -c:v copy
                        //.setAudioCodec("copy") // Sets -c:a copy
                        .copyAllCodecs()) // Fast split without re-encoding
                .execute();*/

        return true;
    }

}
