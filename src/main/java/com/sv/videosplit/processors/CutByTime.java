package com.sv.videosplit.processors;

import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffmpeg.UrlInput;
import com.github.kokorin.jaffree.ffmpeg.UrlOutput;
import com.sv.core.logger.MyLogger;
import com.sv.videosplit.Arguments;
import com.sv.videosplit.BaseProcessor;

import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * This processor will cut file from time start to end
 */
public class CutByTime extends BaseProcessor {

    public CutByTime(MyLogger logger) {
        super(logger);
    }

    private String getOutputName (String inFile) {
        int idx = inFile.lastIndexOf(".");
        String out = inFile.substring(0, idx) + "-cut" + inFile.substring(idx);
        log("Output file name [" + out + "]");
        return out;
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

        String out = getOutputName(args.getFileName());
        LocalTime startTime = LocalTime.parse(args.getStartTime());
        LocalTime endTime = LocalTime.parse(args.getEndTime());

        FFmpeg.atPath(Paths.get(FFMPEG_PATH))
                .addInput(UrlInput.fromUrl(args.getFileName())
                        //.setPosition(0, TimeUnit.HOURS) // Start time
                        .setPosition(startTime.toSecondOfDay(), TimeUnit.SECONDS) // Start time
                        .setDuration(endTime.toSecondOfDay(), TimeUnit.SECONDS)) // Duration of the clip
                .addOutput(UrlOutput.toPath(Paths.get(out))
                        //.setVideoCodec("copy") // Sets -c:v copy
                        //.setAudioCodec("copy") // Sets -c:a copy
                        .copyAllCodecs()) // Fast split without re-encoding
                .execute();

        return true;
    }

}
