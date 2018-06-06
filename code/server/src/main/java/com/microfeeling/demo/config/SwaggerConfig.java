package com.microfeeling.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Add by jianhan on 2018/6/5
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(newArrayList(

                        new ParameterBuilder()
                                .name("pageNumber")
                                .description("pageNumber")
                                .modelRef(new ModelRef("int"))
                                .parameterType("header")
                                .required(false)
                                .build(),

                        new ParameterBuilder()
                                .name("pageSize")
                                .description("pageSize")
                                .modelRef(new ModelRef("int"))
                                .parameterType("header")
                                .required(false)
                                .build(),

                        new ParameterBuilder()
                                .name("sort")
                                .description("sort")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(false)
                                .build(),

                        new ParameterBuilder()
                                .name("desc")
                                .description("desc")
                                .modelRef(new ModelRef("String"))
                                .parameterType("header")
                                .required(false)
                                .build(),

                        new ParameterBuilder()
                                .name("language")
                                .description("language")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(false)
                                .build(),

                        new ParameterBuilder()
                                .name("token")
                                .description("token")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(false)
                                .build()

                ))
                .groupName("demo")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .pathMapping("/")
                .select()
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("demo api") //大标题
                .description("") //小标题
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}
