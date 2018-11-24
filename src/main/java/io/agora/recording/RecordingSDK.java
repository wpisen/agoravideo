package io.agora.recording;

import java.util.ArrayList;
import java.util.List;

import io.agora.recording.common.Common.AudioFrame;
import io.agora.recording.common.Common.VideoFrame;
import io.agora.recording.common.Common.VideoMixingLayout;
import io.agora.recording.common.RecordingConfig;
import io.agora.recording.common.RecordingEngineProperties;

public class RecordingSDK {

	private List<RecordingEventHandler> recordingEventHandlers = null;

	/*
	 * Brief: load Cpp library
	 */

    static {
        //System.loadLibrary("recording");
		System.load("/usr/local/java/jdk/jni/librecording.so");
		//LibLoader.loadLib("librecording.so");
    }

	public RecordingSDK() {
		recordingEventHandlers = new ArrayList<RecordingEventHandler>();
	}
    
	public void registerOberserver(RecordingEventHandler recordingEventHandler) {
		if (!recordingEventHandlers.contains(recordingEventHandler)) {
			recordingEventHandlers.add(recordingEventHandler);
		}
	}

	public void unRegisterOberserver(RecordingEventHandler recordingEventHandler) {
		if (recordingEventHandlers.contains(recordingEventHandler)) {
			recordingEventHandlers.remove(recordingEventHandler);
		}
	}

	/*
	 * Brief: This method will create a recording engine instance and join a
	 * channel, and then start recording.
	 * 
	 * @param channelId A string providing the unique channel id for the
	 * AgoraRTC session
	 * 
	 * @param channelKey This parameter is optional if the user uses a static
	 * key, or App ID. In this case, pass NULL as the parameter value. More
	 * details refer to
	 * http://docs-origin.agora.io/en/user_guide/Component_and_Others/
	 * Dynamic_Key_User_Guide.html
	 * 
	 * @param uid The uid of recording client
	 * 
	 * @param config The config of current recording
	 * 
	 * @return true: Method call succeeded. false: Method call failed.
	 */
	public native boolean createChannel(String appId, String channelKey, String name, int uid, RecordingConfig config);

	/*
	 * Brief: Stop recording
	 * 
	 * @param nativeHandle The recording engine
	 * 
	 * @return true: Method call succeeded. false: Method call failed.
	 */
	public native boolean leaveChannel(long nativeHandle);

	/*
	 * Brief: Set the layout of video mixing
	 * 
	 * @param nativeHandle The recording engine
	 * 
	 * @param layout layout setting
	 * 
	 * @return 0: Method call succeeded. <0: Method call failed.
	 */
	public native int setVideoMixingLayout(long nativeHandle, VideoMixingLayout layout);

	/*
	 * Brief: Start service under manually trigger mode
	 * 
	 * @param nativeHandle The recording engine
	 * 
	 * @return 0: Method call succeeded. <0: Method call failed.
	 */
	public native int startService(long nativeHandle);

	/*
	 * Brief: Stop service under manually trigger mode
	 * 
	 * @param nativeHandle The recording engine
	 * 
	 * @return 0: Method call succeeded. <0: Method call failed.
	 */
	public native int stopService(long nativeHandle);

	/*
	 * Brief: Get recording properties
	 * 
	 * @param nativeHandle The recording engine
	 * 
	 * @return RecordingEngineProperties
	 */
    public native RecordingEngineProperties getProperties(long nativeHandle);

    /*
     * Brief: When call createChannel successfully, JNI will call back this
     * method to set the recording engine.
     */
    private void nativeObjectRef(long nativeHandle) {
        for (RecordingEventHandler oberserver : recordingEventHandlers) {
            oberserver.nativeObjectRef(nativeHandle);
        }
    }

	/*
	 * Brief: Callback when recording application successfully left the channel
	 * 
	 * @param reason leave path reason, please refer to the define of
	 * LEAVE_PATH_CODE
	 */
	private void onLeaveChannel(int reason) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.onLeaveChannel(reason);
		}
	}

	/*
	 * Brief: Callback when an error occurred during the runtime of recording
	 * engine
	 * 
	 * @param error Error code, please refer to the define of ERROR_CODE_TYPE
	 * 
	 * @param error State code, please refer to the define of STAT_CODE_TYPE
	 */
	private void onError(int error, int stat_code) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.onError(error, stat_code);
		}
	}

	/*
	 * Brief: Callback when an warning occurred during the runtime of recording
	 * engine
	 * 
	 * @param warn Warning code, please refer to the define of WARN_CODE_TYPE
	 */
	private void onWarning(int warn) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.onWarning(warn);
		}
	}

	/*
	 * Brief: Callback when a user left the channel or gone offline
	 * 
	 * @param uid user ID
	 * 
	 * @param reason offline reason, please refer to the define of
	 * USER_OFFLINE_REASON_TYPE
	 */
	private void onUserOffline(long uid, int reason) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.onUserOffline(uid, reason);
		}
	}

	/*
	 * Brief: Callback when another user successfully joined the channel
	 * 
	 * @param uid user ID
	 * 
	 * @param recordingDir user recorded file directory
	 */
	private void onUserJoined(long uid, String recordingDir) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.onUserJoined(uid, recordingDir);
		}
	}

	/*
	 * Brief: Callback when received a audio frame
	 * 
	 * @param uid user ID
	 * 
	 * @param type type of audio frame, please refer to the define of AudioFrame
	 * 
	 * @param frame reference of received audio frame
	 */
	private void audioFrameReceived(long uid, int type, AudioFrame frame) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.audioFrameReceived(uid, type, frame);
		}
	}

    /*
     * Brief: Callback when received a video frame
	 * 
	 * @param uid user ID
	 * 
	 * @param type type of video frame, please refer to the define of VideoFrame
	 * 
	 * @param frame reference of received video frame
	 * 
	 * @param rotation rotation of video
	 */
	private void videoFrameReceived(long uid, int type, VideoFrame frame, int rotation) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.videoFrameReceived(uid, type, frame, rotation);
		}
	}

	/*
	 * Brief: Callback when JNI layer exited
	 */
	private void stopCallBack() {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.stopCallBack();
		}
	}

	/*
	 * Brief: Callback when call createChannel successfully
	 * 
	 * @param path recording file directory
	 */
	private void recordingPathCallBack(String path) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.recordingPathCallBack(path);
		}
	}
	/*
     * Brief: Callback when the user hase successfully joined the specified channel
     *
     * @param channelID    channel ID onError
     * @param uid          User ID
     *
     */
	 private void onJoinChannelSuccess(String channelId, long uid) {
		for (RecordingEventHandler oberserver : recordingEventHandlers) {
			oberserver.onJoinChannelSuccess(channelId, uid);
		}
	}


}
