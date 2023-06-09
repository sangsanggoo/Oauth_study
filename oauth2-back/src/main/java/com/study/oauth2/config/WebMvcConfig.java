package com.study.oauth2.config;

import java.io.IOException;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.nimbusds.jose.util.StandardCharset;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Value("${file.path")
	private String filePath;
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("*");
	}
	
	
	
	// 한글을 디코딩 해주는 역할임 
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/image/**")
				.addResourceLocations("file:///"+ filePath) //  /image/** 경로는  file:///filePath로 바꿔주는 역할
				.resourceChain(true)
				.addResolver(new PathResourceResolver() {
				@Override
				protected Resource getResource(String resourcePath, Resource location) throws IOException {
					resourcePath = URLDecoder.decode(resourcePath, StandardCharset.UTF_8);
					return super.getResource(resourcePath, location);
				}
				});
		
	}
}
