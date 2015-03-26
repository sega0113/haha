package com.sega.testJsonParser.configuration;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.sega.testJsonParser")
public class TestConfiguration extends WebMvcConfigurerAdapter{

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
		final FhirHttpMessageConverter fhirParser = new FhirHttpMessageConverter();
		final ByteArrayHttpMessageConverter byteConverter = new ByteArrayHttpMessageConverter();
		final ResourceHttpMessageConverter resourceConverter = new ResourceHttpMessageConverter();
		converters.add(fhirParser);
		converters.add(byteConverter);
		converters.add(resourceConverter);
		super.configureMessageConverters(converters);
	}
}
