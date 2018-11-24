package io.agora.recording;

import io.agora.recording.common.Common.AudioFrame;
import io.agora.recording.common.Common.VideoFrame;

public interface RecordingEventHandler {

    void nativeObjectRef(long nativeHandle);

    void onLeaveChannel(int reason);

    void onError(int error, int stat_code);

    void onWarning(int warn);

    void onJoinChannelSuccess(String channelId, long uid);

    void onUserOffline(long uid, int reason);

    void onUserJoined(long uid, String recordingDir);

    void audioFrameReceived(long uid, int type, AudioFrame frame);

    void videoFrameReceived(long uid, int type, VideoFrame frame, int rotation);

    void stopCallBack();

    void recordingPathCallBack(String path);
}
