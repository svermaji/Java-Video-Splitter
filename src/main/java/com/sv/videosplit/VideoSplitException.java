package com.sv.videosplit;

/**
 *
 */
public class VideoSplitException extends RuntimeException {

    public VideoSplitException() {
        this("Unknown error occurred.");
    }

    public VideoSplitException(String msg) {
        super(msg);
    }

    public VideoSplitException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
