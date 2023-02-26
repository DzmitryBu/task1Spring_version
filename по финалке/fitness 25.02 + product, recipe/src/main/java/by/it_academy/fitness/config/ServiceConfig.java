package by.it_academy.fitness.config;

import by.it_academy.fitness.dao.repositories.AdminRepository;
import by.it_academy.fitness.dao.repositories.ProductRepository;
import by.it_academy.fitness.dao.repositories.RecipeRepository;
import by.it_academy.fitness.dao.repositories.UserRepository;
import by.it_academy.fitness.service.*;
import by.it_academy.fitness.service.api.IUserService;
import by.it_academy.fitness.service.api.IProductService;
import by.it_academy.fitness.service.api.IRecipeService;
import by.it_academy.fitness.service.api.IRegistrationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class ServiceConfig {

    @Bean
    public IUserService adminService(AdminRepository repository){
        return new UserService(repository);
    }

    @Bean
    public IRegistrationService userService(UserRepository repository, MailSender mailSender){
        return new RegistrationService(repository, mailSender);
    }

    @Bean
    public IProductService productService(ProductRepository repository){
        return new ProductService(repository);
    }

    @Bean
    public IRecipeService recipeService(RecipeRepository repository, ProductService productService){
        return new RecipeService(repository, productService);
    }

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
