package com.example.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    /**
     * Creates a general OpenAPI configuration with basic information.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API for Music Platform")
                        .version("1.0")
                        .description("API for managing artists, playlists, and music tracks"));
    }

    /**
     * Generates a grouped API for different parts of your application.
     * For example, separate groups can be created for users, playlists, and tracks.
     */
    @Bean
    public GroupedOpenApi playlistApi() {
        return GroupedOpenApi.builder()
                .group("playlists")
                .pathsToMatch("/playlists/**")
                .build();
    }

    @Bean
    public GroupedOpenApi songApi() {
        return GroupedOpenApi.builder()
                .group("songs")
                .pathsToMatch("/songs/**")
                .build();
    }

    @Bean
    public GroupedOpenApi artistApi() {
        return GroupedOpenApi.builder()
                .group("artists")
                .pathsToMatch("/artists/**")
                .build();
    }
}
