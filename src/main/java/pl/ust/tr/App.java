package pl.ust.tr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static springfox.documentation.builders.PathSelectors.any;

@EnableSwagger2
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("pl.ust.tr")).paths(any()).build()
				.apiInfo(new ApiInfo("Tutorialpedia API's",
						"API's for Tutorialpedia", "1.0", null,
						new Contact("Dariusz Ustrzycki","https://www.linkedin.com/in/dariusz-ustrzycki-java-dev/",
								"newsecond@outlook.com"), null, null, new ArrayList()));
	}
}
