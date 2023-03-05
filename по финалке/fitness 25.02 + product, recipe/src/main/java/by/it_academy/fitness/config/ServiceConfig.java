package by.it_academy.fitness.config;

import by.it_academy.fitness.core.dto.Product;
import by.it_academy.fitness.core.dto.RecipeForCU;
import by.it_academy.fitness.core.dto.UserCreate;
import by.it_academy.fitness.core.dto.UserRegistration;
import by.it_academy.fitness.dao.repositories.UserRepository;
import by.it_academy.fitness.dao.repositories.ProductRepository;
import by.it_academy.fitness.dao.repositories.RecipeRepository;
import by.it_academy.fitness.dao.repositories.RegistrationRepository;
import by.it_academy.fitness.service.*;
import by.it_academy.fitness.service.api.*;
import by.it_academy.fitness.service.validators.api.IValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class ServiceConfig {

    @Bean
    public IUserService userServiceService(UserRepository repository, ConversionService conversionService, IValidator <UserCreate> validator){
        return new UserService(repository, conversionService, validator);
    }

    @Bean
    public IRegistrationService registrationService(RegistrationRepository registrationRepository, IUserService userService,
                                            MailSender mailSender, ConversionService conversionService, IValidator <UserRegistration> validator){
        return new RegistrationService(registrationRepository, userService, mailSender, conversionService, validator);
    }

    @Bean
    public IProductService productService(ProductRepository repository, ConversionService conversionService, IValidator <Product> validator){
        return new ProductService(repository, conversionService, validator);
    }

    @Bean
    public IRecipeService recipeService(RecipeRepository repository, ProductService productService,
                                        ConversionService conversionService, IValidator<RecipeForCU> validator){
        return new RecipeService(repository, productService, conversionService, validator);
    }

    @Value("${spring.data.redis.url}")
    private String url;
    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.protocol}")
    private String protocol;

    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setPort(port);

        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.transport.protocol", protocol);

        return mailSender;
    }
}
