package com.invillia.acme.util;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author Marcus Vinicius
 * 6 de dez de 2019
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.invillia.acme")).paths(regex("/.*")).build()
				.apiInfo(metaInfo());
	}

	private ApiInfo metaInfo() {

		ApiInfo apiInfo = new ApiInfo("Invillia API REST", "API REST ERP", "1.0", "Termos de serviço",
				new Contact("Marcus Vinicius G. Oliveira", "http://invillia.com", "luluka.magaiver@gmail.com"),
				"Apache License Version 2.0", "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>());

		return apiInfo;
	}

}
