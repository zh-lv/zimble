package com.zimble.system.config;

import com.zimble.system.utils.file.FileUploadUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Data
public class ZimbleConfig {
    /** 项目名称 */
    @Value("${zimble.name}")
    private String name;

    /** 版本 */
    @Value("${zimble.version}")
    private String version;

    /** 版权年份 */
    @Value("${zimble.copyrightYear}")
    private String copyrightYear;

    /** 实例演示开关 */
    @Value("${zimble.demoEnabled}")
    private boolean demoEnabled;

    /** 上传路径 */
    @Value("${zimble.profile}")
    private String profile;

    /** 获取地址开关 */
    @Value("${zimble.addressEnabled}")
    private boolean addressEnabled;

    @PostConstruct
    public void init(){
        ZimbleSystemConfig.setConfigInfo(this);
    }
}
