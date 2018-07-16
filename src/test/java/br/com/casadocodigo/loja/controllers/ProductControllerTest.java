package br.com.casadocodigo.loja.controllers;

import br.com.casadocodigo.loja.conf.AppWebConfiguration;
import br.com.casadocodigo.loja.conf.DataSourceConfigurationTest;
import br.com.casadocodigo.loja.conf.JPAConfiguration;
import br.com.casadocodigo.loja.conf.SecurityConfiguration;
import br.com.casadocodigo.loja.daos.ProductDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppWebConfiguration.class, JPAConfiguration.class, SecurityConfiguration.class, DataSourceConfigurationTest.class})
@ActiveProfiles("test")
public class ProductControllerTest {

    @Autowired private ProductDAO productDAO;
    @Autowired private WebApplicationContext wac;
    @Autowired private Filter springSecurityFilterChain;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilters(springSecurityFilterChain).build();
    }

    @Test
    @Transactional
    public void shoudListAllBooks() throws Exception {
        this.mockMvc.perform(get("/products"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/views/products/list.jsp"));
    }

    @Test
    public void onlyAdminsShouldAccessProductsForm() throws Exception {
        RequestPostProcessor processor = SecurityMockMvcRequestPostProcessors.user("comprador@gmail.com").password("123456").roles("COMPRADOR");
        this.mockMvc.perform(get("/products/form").with(processor))
            .andExpect(MockMvcResultMatchers.status().is(403));
    }

}
