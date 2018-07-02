package com.luomor.yiaroundad.media.callback;

/**
 * Created by Peter on 18/6/31 21:42
 * 1097692918@qq.com
 * <p/>
 * 视频控制回调接口
 */
public interface MediaPlayerListener {
    void start();

    void pause();

    int getDuration();

    int getCurrentPosition();

    void seekTo(long pos);

    boolean isPlaying();

    int getBufferPercentage();

    boolean canPause();
}
