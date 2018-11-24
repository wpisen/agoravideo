package io.agora.recording.test;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Timer;
import java.util.TimerTask;

import io.agora.recording.common.Common;
import io.agora.recording.common.Common.AUDIO_FORMAT_TYPE;
import io.agora.recording.common.Common.AudioFrame;
import io.agora.recording.common.Common.CHANNEL_PROFILE_TYPE;
import io.agora.recording.common.Common.REMOTE_VIDEO_STREAM_TYPE;
import io.agora.recording.common.Common.VIDEO_FORMAT_TYPE;
import io.agora.recording.common.Common.VideoFrame;
import io.agora.recording.common.Common.VideoMixingLayout;
import io.agora.recording.common.RecordingConfig;
import io.agora.recording.common.RecordingEngineProperties;

import io.agora.recording.RecordingSDK;
import io.agora.recording.RecordingEventHandler;
import io.agora.recording.test.RecordingCleanTimer;
import io.agora.recording.test.UserInfo;


public class RecordingSample implements RecordingEventHandler {
	// java run status flag
	private boolean isMixMode = false;
	private int width = 0;
	private int height = 0;
	private int fps = 0;
	private int kbps = 0;
	private String storageDir = "./";
	private long aCount = 0;
	private long size = 0;
	private CHANNEL_PROFILE_TYPE profile_type;
	Vector<Long> m_peers = new Vector<Long>();
	private long mNativeHandle = 0;
    private RecordingConfig config = null;
	private RecordingSDK RecordingSDKInstance = null;

    HashMap<String, UserInfo> audioChannels = new HashMap<String, UserInfo>();
    HashMap<String, UserInfo> videoChannels = new HashMap<String, UserInfo>();
    Timer cleanTimer = null;

	public RecordingSample(RecordingSDK recording) {
		this.RecordingSDKInstance = recording;
		RecordingSDKInstance.registerOberserver(this);
	}

    public static void main(String[] args) {
        //should config -Djava.library.path to load library
        RecordingSDK RecordingSdk = new RecordingSDK();
        RecordingSample ars = new RecordingSample(RecordingSdk);
        ars.createChannel(args);
        ars.unRegister();
    }

	public void unRegister(){
		RecordingSDKInstance.unRegisterOberserver(this);	
	}

	private boolean IsMixMode() {
		return isMixMode;
	}

	public void nativeObjectRef(long nativeHandle) {
		mNativeHandle = nativeHandle;
	}

	public void onLeaveChannel(int reason) {
		System.out.println("RecordingSDK onLeaveChannel,code:" + reason);
	}

	public void onError(int error, int stat_code) {
		System.out.println("RecordingSDK onError,error:" + error + ",stat code:" + stat_code);
	}

  public void onWarning(int warn) {
      System.out.println("RecordingSDK onWarning,warn:" + warn);
  }

  public void onJoinChannelSuccess(String channelId, long uid) {
    if(config.decodeAudio != AUDIO_FORMAT_TYPE.AUDIO_FORMAT_DEFAULT_TYPE) {
      cleanTimer.schedule(new RecordingCleanTimer(this), 3000);
    }
    System.out.println("RecordingSDK joinChannel success, channelId:" + channelId +", uid:" + uid);
  }

	public void onUserOffline(long uid, int reason) {
		System.out.println("RecordingSDK onUserOffline uid:" + uid + ",offline reason:" + reason);
		m_peers.remove(uid);
		PrintUsersInfo(m_peers);
		SetVideoMixingLayout();
	}

    protected void clean() {
       synchronized(this) {
           long now = System.currentTimeMillis();
           for(String key : audioChannels.keySet()) {
               UserInfo info = audioChannels.get(key);
               if(now - info.last_receive_time > 3000) {
                   audioChannels.remove(key);
                   try{
                       info.channel.close();
                   }catch(IOException e) {
                       e.printStackTrace();
                   }
               }
           }
           for(String key : videoChannels.keySet()) {
               UserInfo info = videoChannels.get(key);
               if(now - info.last_receive_time > 3000 ) {
                   videoChannels.remove(key);
                   try{
                       info.channel.close();
                   }catch(IOException e) {
                       e.printStackTrace();
                   }
               }
           }
       }
       cleanTimer.schedule(new RecordingCleanTimer(this), 3000);
    }

	public void onUserJoined(long uid, String recordingDir) {
		System.out.println("onUserJoined uid:" + uid + ",recordingDir:" + recordingDir);
		storageDir = recordingDir;
		m_peers.add(uid);
		PrintUsersInfo(m_peers);
		// When the user joined, we can re-layout the canvas
		SetVideoMixingLayout();
	}

    private void checkUser(long uid, boolean isAudio) {
         String path = storageDir + Long.toString(uid);
         String key = Long.toString(uid);
         synchronized(this) {
             if(isAudio && !audioChannels.containsKey(key)) {
                if(config.decodeAudio == AUDIO_FORMAT_TYPE.AUDIO_FORMAT_AAC_FRAME_TYPE ||
                        config.decodeAudio == AUDIO_FORMAT_TYPE.AUDIO_FORMAT_PCM_FRAME_TYPE ||
                        config.decodeAudio == AUDIO_FORMAT_TYPE.AUDIO_FORMAT_MIXED_PCM_FRAME_TYPE) {
                    String audioPath;
                    if(config.decodeAudio == AUDIO_FORMAT_TYPE.AUDIO_FORMAT_AAC_FRAME_TYPE) {
                        audioPath = path + ".aac";
                    }else {
                        audioPath = path + ".pcm";
                    }
                    try {
                        UserInfo info = new UserInfo();
                        info.channel = new FileOutputStream(audioPath, true);
                        info.last_receive_time = System.currentTimeMillis();
                        audioChannels.put(key, info);
                    } catch(FileNotFoundException e) {
                        System.out.println("Can't find file : " + audioPath);
                    }
                }
             }

             if(!isAudio && !videoChannels.containsKey(key)) {
                if(config.decodeVideo == VIDEO_FORMAT_TYPE.VIDEO_FORMAT_YUV_FRAME_TYPE || 
                        config.decodeVideo == VIDEO_FORMAT_TYPE.VIDEO_FORMAT_H264_FRAME_TYPE) {
                    String videoPath;
                    if(config.decodeVideo == VIDEO_FORMAT_TYPE.VIDEO_FORMAT_H264_FRAME_TYPE) {
                        videoPath = path + ".h264";
                    } else {
                        videoPath = path + ".yuv";
                    }
                    try {
                        UserInfo info = new UserInfo();
                        info.channel = new FileOutputStream(videoPath, true);
                        info.last_receive_time = System.currentTimeMillis();
                        videoChannels.put(key, info);
                    } catch (FileNotFoundException e) {
                        System.out.println("Can't find file : " + videoPath);
                    }
                }
             }
         }
    }

	public void audioFrameReceived(long uid, int type, AudioFrame frame) {
		// System.out.println("java demo
		// audioFrameReceived,uid:"+uid+",type:"+type);
		byte[] buf = null;
        long size = 0;
        checkUser(uid, true);
		if (type == 0) {// pcm
			buf = frame.pcm.pcmBuf;
            size = frame.pcm.pcmBufSize;
		} else if (type == 1) {// aac
			buf = frame.aac.aacBuf;
            size = frame.aac.aacBufSize;
		}
		WriteBytesToFileClassic(uid, buf, size, true);
	}

	public void videoFrameReceived(long uid, int type, VideoFrame frame, int rotation)// rotation:0,90,180,270
	{
		byte[] buf = null;
        long size = 0;
        checkUser(uid, false);
		// System.out.println("java demovideoFrameReceived,uid:"+uid+",type:"+type);
		if (type == 0) {// yuv
      buf = frame.yuv.buf;
      size = frame.yuv.bufSize;
			if (buf == null) {
				System.out.println("java demo videoFrameReceived null");
			}
		} else if (type == 1) {// h264
			buf = frame.h264.buf;
      size = frame.h264.bufSize;
		} else if (type == 2) { // jpg
      String path = storageDir + Long.toString(uid) + System.currentTimeMillis() + ".jpg";
			buf = frame.jpg.buf;
      size = frame.jpg.bufSize;
      try {
        FileOutputStream channel = new FileOutputStream(path, true);
        channel.write(buf, 0, (int) size);
        channel.close();
      } catch(Exception e ){
        System.out.println("Error write to " + path);
      }
      return;
		}
		WriteBytesToFileClassic(uid, buf, size, false);
	}

	/*
	 * Brief: Callback when JNI layer exited
	 */
	public void stopCallBack() {
		System.out.println("java demo receive stop from JNI ");
	}

	/*
	 * Brief: Callback when call createChannel successfully
	 * 
	 * @param path recording file directory
	 */
	public void recordingPathCallBack(String path) {
		storageDir = path;
	}

	private int SetVideoMixingLayout() {
		Common ei = new Common();
		Common.VideoMixingLayout layout = ei.new VideoMixingLayout();

		if (!IsMixMode())
			return -1;

		layout.canvasHeight = height;
		layout.canvasWidth = width;
		layout.backgroundColor = "#23b9dc";
		layout.regionCount = (int) (m_peers.size());

		if (!m_peers.isEmpty()) {
			System.out.println("java setVideoMixingLayout m_peers is not empty, start layout");
			int max_peers = (profile_type == CHANNEL_PROFILE_TYPE.CHANNEL_PROFILE_COMMUNICATION ? 7 : 17);
			Common.VideoMixingLayout.Region[] regionList = new Common.VideoMixingLayout.Region[m_peers.size()];
			regionList[0] = layout.new Region();
			regionList[0].uid = m_peers.get(0);
			regionList[0].x = 0.f;
			regionList[0].y = 0.f;
			regionList[0].width = 1.f;
			regionList[0].height = 1.f;
			regionList[0].zOrder = 0;
			regionList[0].alpha = 1.f;
			regionList[0].renderMode = 0;
			float f_width = width;
			float f_height = height;
			float canvasWidth = f_width;
			float canvasHeight = f_height;
			float viewWidth = 0.235f;
			float viewHEdge = 0.012f;
			float viewHeight = viewWidth * (canvasWidth / canvasHeight);
			float viewVEdge = viewHEdge * (canvasWidth / canvasHeight);
			for (int i = 1; i < m_peers.size(); i++) {
				if (i >= max_peers)
					break;
				regionList[i] = layout.new Region();

				regionList[i].uid = m_peers.get(i);
				float f_x = (i - 1) % 4;
				float f_y = (i - 1) / 4;
				float xIndex = f_x;
				float yIndex = f_y;
				regionList[i].x = xIndex * (viewWidth + viewHEdge) + viewHEdge;
				regionList[i].y = 1 - (yIndex + 1) * (viewHeight + viewVEdge);
				regionList[i].width = viewWidth;
				regionList[i].height = viewHeight;
				regionList[i].zOrder = 0;
				regionList[i].alpha = (i + 1);
				regionList[i].renderMode = 0;
			}
			layout.regions = regionList;
		} else {
			layout.regions = null;
		}
		return RecordingSDKInstance.setVideoMixingLayout(mNativeHandle, layout);
	}

	private void WriteBytesToFileClassic(long uid, byte[] byteBuffer, long size, boolean isAudio) {
		if (byteBuffer == null) {
			System.out.println("WriteBytesToFileClassic but byte buffer is null!");
			return;
		}

        synchronized(this) {
            try {
                UserInfo info = isAudio ? audioChannels.get(Long.toString(uid)) : videoChannels.get(Long.toString(uid));
                if(info != null) {
                    info.channel.write(byteBuffer, 0, (int) size);
                    info.channel.flush();
                } else {
                    System.out.println("Channel is null");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}

	private String GetNowDate() {
		String temp_str = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		temp_str = sdf.format(dt);
		return temp_str;
	}

	private void PrintUsersInfo(Vector vector) {
		System.out.println("user size:" + vector.size());
		for (Long l : m_peers) {
			System.out.println("user:" + l);
		}
	}

	public void createChannel(String[] args) {
		int uid = 0;
		String appId = "";
		String channelKey = "";
		String name = "";
		int channelProfile = 0;

		String decryptionMode = "";
		String secret = "";
		String mixResolution = "360,640,15,500";

		int idleLimitSec = 5 * 60;// 300s

		String applitePath = "";
        String recordFileRootDir = "";
        String cfgFilePath = "";
        String proxyServer = "";


		int lowUdpPort = 0;// 40000;
		int highUdpPort = 0;// 40004;

		boolean isAudioOnly = false;
		boolean isVideoOnly = false;
		boolean isMixingEnabled = false;
		boolean mixedVideoAudio = false;

		int getAudioFrame = AUDIO_FORMAT_TYPE.AUDIO_FORMAT_DEFAULT_TYPE.ordinal();
		int getVideoFrame = VIDEO_FORMAT_TYPE.VIDEO_FORMAT_DEFAULT_TYPE.ordinal();
		int streamType = REMOTE_VIDEO_STREAM_TYPE.REMOTE_VIDEO_STREAM_HIGH.ordinal();
        int captureInterval = 5;
        int triggerMode = 0;

        int width = 0;
        int height = 0;
		int fps = 0;
        int kbps = 0;

		// paser command line parameters
		if (args.length % 2 != 0) {
			System.out.println("command line parameters error, should be '--key value' format!");
			return;
		}
		String key = "";
		String value = "";
		Map<String, String> map = new HashMap<String, String>();
		if (0 < args.length) {
			for (int i = 0; i < args.length - 1; i++) {
				key = args[i];
				value = args[i + 1];
				map.put(key, value);
			}
		}
		// prefer to use CmdLineParser or annotation
		Object Appid = map.get("--appId");
		Object Uid = map.get("--uid");
		Object Channel = map.get("--channel");
		Object AppliteDir = map.get("--appliteDir");
		Object ChannelKey = map.get("--channelKey");
		Object ChannelProfile = map.get("--channelProfile");
		Object IsAudioOnly = map.get("--isAudioOnly");
		Object IsVideoOnly = map.get("--isVideoOnly");
		Object IsMixingEnabled = map.get("--isMixingEnabled");
		Object MixResolution = map.get("--mixResolution");
		Object MixedVideoAudio = map.get("--mixedVideoAudio");
		Object DecryptionMode = map.get("--decryptionMode");
		Object Secret = map.get("--secret");
		Object Idle = map.get("--idle");
		Object RecordFileRootDir = map.get("--recordFileRootDir");
		Object LowUdpPort = map.get("--lowUdpPort");
		Object HighUdpPort = map.get("--highUdpPort");
		Object GetAudioFrame = map.get("--getAudioFrame");
		Object GetVideoFrame = map.get("--getVideoFrame");
		Object CaptureInterval = map.get("--captureInterval");
		Object CfgFilePath = map.get("--cfgFilePath");
		Object StreamType = map.get("--streamType");
		Object TriggerMode = map.get("--triggerMode");
		Object ProxyServer = map.get("--proxyServer");

		if (Appid == null || Uid == null || Channel == null || AppliteDir == null) {
			// print usage
            String usage = "java RecordingSDK --appId STRING --uid UINTEGER32 --channel STRING --appliteDir STRING --channelKey STRING --channelProfile UINTEGER32 --isAudioOnly --isVideoOnly --isMixingEnabled --mixResolution STRING --mixedVideoAudio --decryptionMode STRING --secret STRING --idle INTEGER32 --recordFileRootDir STRING --lowUdpPort INTEGER32 --highUdpPort INTEGER32 --getAudioFrame UINTEGER32 --getVideoFrame UINTEGER32 --captureInterval INTEGER32 --cfgFilePath STRING --streamType UINTEGER32 --triggerMode INTEGER32 \r\n --appId     (App Id/must) \r\n --uid     (User Id default is 0/must)  \r\n --channel     (Channel Id/must) \r\n --appliteDir     (directory of app lite 'AgoraCoreService', Must pointer to 'Agora_Server_SDK_for_Linux_FULL/bin/' folder/must) \r\n --channelKey     (channelKey/option)\r\n --channelProfile     (channel_profile:(0:COMMUNICATION),(1:broadcast) default is 0/option)  \r\n --isAudioOnly     (Default 0:A/V, 1:AudioOnly (0:1)/option) \r\n --isVideoOnly     (Default 0:A/V, 1:VideoOnly (0:1)/option)\r\n --isMixingEnabled     (Mixing Enable? (0:1)/option)\r\n --mixResolution     (change default resolution for vdieo mix mode/option)                 \r\n --mixedVideoAudio     (mixVideoAudio:(0:seperated Audio,Video) (1:mixed Audio & Video), default is 0 /option)                 \r\n --decryptionMode     (decryption Mode, default is NULL/option)                 \r\n --secret     (input secret when enable decryptionMode/option)                 \r\n --idle     (Default 300s, should be above 3s/option)                 \r\n --recordFileRootDir     (recording file root dir/option)                 \r\n --lowUdpPort     (default is random value/option)                 \r\n --highUdpPort     (default is random value/option)                 \r\n --getAudioFrame     (default 0 (0:save as file, 1:aac frame, 2:pcm frame, 3:mixed pcm frame) (Can't combine with isMixingEnabled) /option)                 \r\n --getVideoFrame     (default 0 (0:save as file, 1:h.264, 2:yuv, 3:jpg buffer, 4:jpg file, 5:jpg file and video file) (Can't combine with isMixingEnabled) /option)              \r\n --captureInterval     (default 5 (Video snapshot interval (second)))                 \r\n --cfgFilePath     (config file path / option)                 \r\n --streamType     (remote video stream type(0:STREAM_HIGH,1:STREAM_LOW), default is 0/option)  \r\n --triggerMode     (triggerMode:(0: automatically mode, 1: manually mode) default is 0/option) \r\n --proxyServer    (proxyServer:format ip:port, eg,\"127.0.0.1:1080\"/option) \r\n";      
            System.out.println("Usage:" + usage);
            return;
        }
        appId = String.valueOf(Appid);
		uid = Integer.parseInt(String.valueOf(Uid));
		appId = String.valueOf(Appid);
		name = String.valueOf(Channel);
		applitePath = String.valueOf(AppliteDir);

		if (ChannelKey != null)
			channelKey = String.valueOf(ChannelKey);
		if (ChannelProfile != null)
			channelProfile = Integer.parseInt(String.valueOf(ChannelProfile));
		if (DecryptionMode != null)
			decryptionMode = String.valueOf(DecryptionMode);
		if (Secret != null)
			secret = String.valueOf(Secret);
		if (MixResolution != null)
			mixResolution = String.valueOf(MixResolution);
		if (Idle != null)
			idleLimitSec = Integer.parseInt(String.valueOf(Idle));
		if (RecordFileRootDir != null)
			recordFileRootDir = String.valueOf(RecordFileRootDir);
		if (CfgFilePath != null)
			cfgFilePath = String.valueOf(CfgFilePath);
		if (LowUdpPort != null)
			lowUdpPort = Integer.parseInt(String.valueOf(LowUdpPort));
		if (HighUdpPort != null)
			highUdpPort = Integer.parseInt(String.valueOf(HighUdpPort));
		if (IsAudioOnly != null && (Integer.parseInt(String.valueOf(IsAudioOnly)) == 1))
			isAudioOnly = true;
		if (IsVideoOnly != null && (Integer.parseInt(String.valueOf(IsVideoOnly)) == 1))
			isVideoOnly = true;
		if (IsMixingEnabled != null && (Integer.parseInt(String.valueOf(IsMixingEnabled)) == 1))
			isMixingEnabled = true;
		if (MixedVideoAudio != null && (Integer.parseInt(String.valueOf(MixedVideoAudio)) == 1))
			mixedVideoAudio = true;
		if (GetAudioFrame != null)
			getAudioFrame = Integer.parseInt(String.valueOf(GetAudioFrame));
		if (GetVideoFrame != null)
			getVideoFrame = Integer.parseInt(String.valueOf(GetVideoFrame));
		if (StreamType != null)
            streamType = Integer.parseInt(String.valueOf(StreamType));
        if (CaptureInterval != null)
            captureInterval = Integer.parseInt(String.valueOf(CaptureInterval));
        if(TriggerMode != null) triggerMode = Integer.parseInt(String.valueOf(TriggerMode));
        if(ProxyServer != null) proxyServer = String.valueOf(ProxyServer);


        RecordingConfig config = new RecordingConfig();
        config.channelProfile = CHANNEL_PROFILE_TYPE.values()[channelProfile];
        config.idleLimitSec = idleLimitSec;
        config.isVideoOnly = isVideoOnly;
        config.isAudioOnly = isAudioOnly;
        config.isMixingEnabled = isMixingEnabled;
        config.mixResolution = mixResolution;
        config.mixedVideoAudio = mixedVideoAudio;
        config.appliteDir = applitePath;
        config.recordFileRootDir = recordFileRootDir;
        config.cfgFilePath = cfgFilePath;
        config.secret = secret;
        config.decryptionMode = decryptionMode;
        config.lowUdpPort = lowUdpPort;
        config.highUdpPort = highUdpPort;
        config.captureInterval = captureInterval;
        config.decodeAudio = AUDIO_FORMAT_TYPE.values()[getAudioFrame];
        config.decodeVideo = VIDEO_FORMAT_TYPE.values()[getVideoFrame];
        config.streamType = REMOTE_VIDEO_STREAM_TYPE.values()[streamType];
        config.triggerMode = triggerMode;
        config.proxyServer = proxyServer;

        this.config = config;

		/*
		 * change log_config Facility per your specific purpose like
		 * agora::base::LOCAL5_LOG_FCLT Default:USER_LOG_FCLT.
		 *
		 * ars.setFacility(LOCAL5_LOG_FCLT);
		 */

        System.out.println(System.getProperty("java.library.path"));


		this.isMixMode = isMixingEnabled;
		this.profile_type = CHANNEL_PROFILE_TYPE.values()[channelProfile];
		if (isMixingEnabled && !isAudioOnly) {
			String[] sourceStrArray = mixResolution.split(",");
			if (sourceStrArray.length != 4) {
				System.out.println("Illegal resolution:" + mixResolution);
				return;
			}
			this.width = Integer.valueOf(sourceStrArray[0]).intValue();
			this.height = Integer.valueOf(sourceStrArray[1]).intValue();
			this.fps = Integer.valueOf(sourceStrArray[2]).intValue();
			this.kbps = Integer.valueOf(sourceStrArray[3]).intValue();
		}
		// run jni event loop , or start a new thread to do it
    	cleanTimer = new Timer();
		RecordingSDKInstance.createChannel(appId, channelKey, name, uid, config);
    	cleanTimer.cancel();
    	cleanTimer = null;
		System.out.println("jni layer has been exited...");
	}

    public boolean leaveChannel(long nativeHandle) { 
        return RecordingSDKInstance.leaveChannel(nativeHandle);
    }
    public int startService(long nativeHandle) {
        return RecordingSDKInstance.startService(nativeHandle);
    }
    public int stopService(long nativeHandle) {
        return RecordingSDKInstance.stopService(nativeHandle);
    }

    public RecordingEngineProperties getProperties(long nativeHandle) {
        return RecordingSDKInstance.getProperties(nativeHandle);
    }

  
}
