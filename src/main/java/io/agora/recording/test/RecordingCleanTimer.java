package io.agora.recording.test;

import java.util.TimerTask;
/**
 * <p>RecordingCleanTimer</p>
 * <p>描述：</p>
 * <p>系统：</p>
 * <p>模块：</p>
 * <p>日期：2018/11/23 19:31</p>
 *
 * @author xhguo
 * @version 1.0
 */
public class RecordingCleanTimer extends TimerTask {

    RecordingSample rs;
    public RecordingCleanTimer(RecordingSample rs) {
        this.rs = rs;
    }
    @Override
    public void run() {
        rs.clean();
    }
}