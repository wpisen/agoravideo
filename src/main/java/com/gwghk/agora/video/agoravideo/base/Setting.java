package com.gwghk.agora.video.agoravideo.base;

/**
 * 基本枚举信息
 */
public class Setting implements java.io.Serializable {

    /**
     * 服务提供商枚举类型
     */
    public enum ServiceProvider {
        TX("腾讯"), ALI("阿里巴巴");
        private final String type;

        ServiceProvider(final String type) {
            this.type = type;
        }

        /**
         * @return Returns the type.
         */
        public String getTypeName() {
            return type;
        }
    }

}
