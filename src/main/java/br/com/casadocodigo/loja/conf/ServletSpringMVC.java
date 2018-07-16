package br.com.casadocodigo.loja.conf;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

/** Classe que configura a Servlet do SpringMVC */

public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Informa ao Spring quais classes de configuração devem ser carregadas ao iniciar a Aplicação (Passo 1)
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SecurityConfiguration.class, AppWebConfiguration.class, JPAConfiguration.class};
    }

    // Informa ao Spring quais classes de configuração devem ser carregadas ao iniciar a Servlet (Passo 2). Caso esteja utilizando o Spring Security, as classes são carregadas no início da aplicação.
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{};
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
