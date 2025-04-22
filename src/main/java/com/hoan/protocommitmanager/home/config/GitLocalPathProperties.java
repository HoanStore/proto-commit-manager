package com.hoan.protocommitmanager.home.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "git.local")
@Getter
@Setter
public class GitLocalPathProperties {
    private String path;
}

