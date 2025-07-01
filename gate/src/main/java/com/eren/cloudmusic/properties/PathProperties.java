package com.eren.cloudmusic.properties;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties("configure.path")
@Component
@Data
public class PathProperties {
    List< String> exclude;
}
