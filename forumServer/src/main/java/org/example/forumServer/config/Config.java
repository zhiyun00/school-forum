package org.example.forumServer.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Import;

@Configurable
@Import({RedisConfig.class, WebMvcConfiguration.class})
public class Config {
}
