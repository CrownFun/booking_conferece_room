package pl.filewicz.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;


@Configuration
@EnableSwagger2
//http://localhost:8080/swagger-ui.html
// rozwiazac kwestie zwiazane z spring security
public class SwaggerConfiguration {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))//tylko endpointy które maja /api w nazwie beda wyswietlane
                .apis(RequestHandlerSelectors.basePackage("pl.filewicz.controller")) // wskazuje pakiet
                .build()
                .apiInfo(createApiInfo());
    }

    private ApiInfo createApiInfo() {
        return new ApiInfo("Room Confernece API", "",
                "version 1.0.45",
                "gfilewicz.pl",
                new Contact("Grzegorz", "gfilewicz.pl", "grzegorz.filewicz@gmail.com"),
                "",
                "", Collections.emptyList());
    }



}
