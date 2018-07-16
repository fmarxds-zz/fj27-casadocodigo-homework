package br.com.casadocodigo.loja.conf;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.ShoppingCart;
import br.com.casadocodigo.loja.viewresolvers.JsonViewResolver;
import com.google.common.cache.CacheBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Classe utilizada para configurar a aplicação web
 */

@EnableWebMvc
@EnableCaching // Habilita o uso de Cache
@ComponentScan(basePackageClasses = {HomeController.class, ProductDAO.class, FileSaver.class, ShoppingCart.class})
public class AppWebConfiguration extends WebMvcConfigurerAdapter {
// Apenas está sendo realizada a extensão da classe WebMvcConfigureAdapter para que sejam habilitados os recursos estáticos no método 'configureDefaultServoetHandling'

    // Configura os diretórios padrões da view
    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {

        InternalResourceViewResolver resolver = new InternalResourceViewResolver();

        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");

        // Habilita o uso da classe ShoppingCart globalmente pela JSP
        resolver.setExposedContextBeanNames("shoppingCart");

        return resolver;
    }

    // Habilitam o uso de arquivos estáticos (CSS, JS, etc.)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/", "/other-resources/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    //--FIM--

    // Configura o arquivo de mensagens padrão do Spring
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/WEB-INF/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(1);
        return messageSource;
    }

    // Configura o serviço de conversão de data
    @Bean
    public FormattingConversionService mvcConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(true);
        DateFormatterRegistrar registrar = new DateFormatterRegistrar();

        registrar.setFormatter(new DateFormatter("yyyy-MM-dd"));
        registrar.registerFormatters(conversionService);

        return conversionService;
    }

    // Habilita o upload de arquivos
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    // Habilita a injecao do Rest Template
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Habilita o uso de cache com a API GuavaCacheManager da Google
    @Bean
    public CacheManager cacheManager() {
        CacheBuilder<Object, Object> builder =
                CacheBuilder
                        .newBuilder()
                        .maximumSize(100)
                        .expireAfterAccess(5, TimeUnit.MINUTES);
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        cacheManager.setCacheBuilder(builder);
        return cacheManager;
    }

    // Habilita o uso de retornos tipo JSON ou XML
    @Bean
    public ViewResolver contentNegotiationViewResolver(ContentNegotiationManager negotiationManager) {
        List<ViewResolver> resolvers = new ArrayList<>();

        resolvers.add(internalResourceViewResolver());
        resolvers.add(new JsonViewResolver());

        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();

        resolver.setViewResolvers(resolvers);
        resolver.setContentNegotiationManager(negotiationManager);

        return resolver;
    }

    // Adiciona um interceptador de requisições para mudança de língua na página
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleChangeInterceptor());
    }

    // Configura o modo como o Spring vai armazenar a localidade escolhida pelo usuário (no caso, em cookies)
    @Bean
    public LocaleResolver localeResolver() {
        return new CookieLocaleResolver();
    }
}
