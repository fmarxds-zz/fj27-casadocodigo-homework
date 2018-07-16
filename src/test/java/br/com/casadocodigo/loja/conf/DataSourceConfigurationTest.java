package br.com.casadocodigo.loja.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class DataSourceConfigurationTest {

    @Bean
    @Profile("test") // Usa este Bean somente no profile de teste
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/casadocodigo_test?createDatabaseIfNotExist=true");
        dataSource.setUsername("user");
        dataSource.setPassword("user");

        return dataSource;
    }

}
