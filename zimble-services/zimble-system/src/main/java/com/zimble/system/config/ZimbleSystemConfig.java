package com.zimble.system.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * 读取项目相关配置
 * 
 * @author ruoyi
 */
@Repository
public class ZimbleSystemConfig
{
    /** 项目名称 */
    private static String name;

    /** 版本 */
    private static String version;

    /** 版权年份 */
    private static String copyrightYear;

    /** 实例演示开关 */
    private static boolean demoEnabled;

    /** 上传路径 */
    private static String profile;

    /** 获取地址开关 */
    private static boolean addressEnabled;

    public static void setConfigInfo(ZimbleConfig zimbleConfig) {
        ZimbleSystemConfig.name = zimbleConfig.getName();
        ZimbleSystemConfig.version = zimbleConfig.getVersion();
        ZimbleSystemConfig.copyrightYear = zimbleConfig.getCopyrightYear();
        ZimbleSystemConfig.demoEnabled = zimbleConfig.isDemoEnabled();
        ZimbleSystemConfig.profile = zimbleConfig.getProfile();
        ZimbleSystemConfig.addressEnabled = zimbleConfig.isAddressEnabled();
    }

    /**
     * 获取默认路径
     */
    public static String getProfile()
    {
        String sProfile = ZimbleSystemConfig.profile;
        return sProfile;
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        String sAvatarPath = ZimbleSystemConfig.profile+"/avatar";
        return sAvatarPath;
    }

    /**
     * 获取下载路径
     */
    public String getDownloadPath()
    {
        String sDownloadPath = ZimbleSystemConfig.profile+"/download/";
        return sDownloadPath;
    }

    /**
     * 获取上传路径
     */
    public String getUploadPath()
    {
        String sUploadPath = ZimbleSystemConfig.profile+"/upload";
        return sUploadPath;
    }
}