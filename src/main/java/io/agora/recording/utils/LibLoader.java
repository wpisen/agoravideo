package io.agora.recording.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;

/**
 * <p>LibLoader</p>
 * <p>描述：</p>
 * <p>系统：</p>
 * <p>模块：</p>
 * <p>日期：2018/11/24 10:19</p>
 *
 * @author xhguo
 * @version 1.0
 */
public class LibLoader {
    public static void loadLib(String libName) {
        String resourcePath = "/" + libName;
        String folderName = System.getProperty("java.io.tmpdir") + "/lib/";
        File folder = new File(folderName);
        folder.mkdirs();
        File libFile = new File(folder, libName);
        if (libFile.exists()) {
            System.load(libFile.getAbsolutePath());
        } else {
            try {
                InputStream in = LibLoader.class.getResourceAsStream(resourcePath);
                FileUtils.copyInputStreamToFile(in,libFile);
                in.close();
                System.load(libFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to load required lib", e);
            }
        }
    }
}