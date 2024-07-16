package com.amancode.blogappserver.Config;

// import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

// import io.swagger.v3.oas.models.Components;
// import io.swagger.v3.oas.models.ExternalDocumentation;
// import io.swagger.v3.oas.models.OpenAPI;
// import io.swagger.v3.oas.models.info.Contact;
// import io.swagger.v3.oas.models.info.Info;
// import io.swagger.v3.oas.models.info.License;
// import io.swagger.v3.oas.models.security.SecurityRequirement;
// import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@SecurityScheme(
    name = "scheme1",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
@OpenAPIDefinition(
    info = @Info(
        title = "Bloging Website API",
        description = "It contains all the api related to bloging website project.",
        version = "1.0",
        contact = @Contact(
            name = "Syed Amanullah Wasti",
            email = "amanwasti5@gmail.com"
        ),
        license = @License(
            name = "Apache 2.0"
        )
    ),
    externalDocs = @ExternalDocumentation(
        url = "https://github.com/AmanWasti9",
        description = "This is my github repository"

    )
)
public class SwaggerConfig {

    // @Bean
    // public OpenAPI openAPI() {

    //     String schemeName="bearerScheme";

    //   return new OpenAPI()
    //     .addSecurityItem(new SecurityRequirement()
    //         .addList(schemeName)
    //     )        
    //     .components(new Components()
    //         .addSecuritySchemes(schemeName, new SecurityScheme()
    //             .name(schemeName)
    //             .type(SecurityScheme.Type.HTTP)
    //             .bearerFormat("JWT")
    //             .scheme("bearer")
    //         )
    //     )
    //     .info(new Info().title("Bloging Website API")
    //             .description("It contains all the api related to bloging website project.")
    //             .version("1.0")
    //             .contact(new Contact().name("Syed Amanullah Wasti").email("amanwasti5@gmail.com"))
    //             .license(new License().name("Apache 2.0"))
    //         )
    //         .externalDocs(new ExternalDocumentation()
    //         .url("https://github.com/AmanWasti9")
    //         .description("This is my github repository"));
    // }
    
    
}
