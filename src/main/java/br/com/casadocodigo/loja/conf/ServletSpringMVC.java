package br.com.casadocodigo.loja.conf;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

/** Classe que configura a Servlet do SpringMVC */

public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    // Informa ao Spring onde estão as classes de configuração da aplicação
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppWebConfiguration.class, JPAConfiguration.class};
    }

    // Configura quais URIs a servlet do Spring vai mapear da aplicação (no caso, com "/" ela mapeia a aplicação toda)
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    // Habilita o upload de arquivos na Servlet
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(new MultipartConfigElement(""));
    }
}
