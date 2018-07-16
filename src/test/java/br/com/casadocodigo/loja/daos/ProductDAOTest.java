package br.com.casadocodigo.loja.daos;

import br.com.casadocodigo.loja.builders.ProductBuilder;
import br.com.casadocodigo.loja.conf.DataSourceConfigurationTest;
import br.com.casadocodigo.loja.conf.JPAConfiguration;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class) // Informa ao JUnit que o Spring deverá ser notificado da execução de testes (para funcionar as injeções)
@ContextConfiguration(classes = {ProductDAO.class, JPAConfiguration.class, DataSourceConfigurationTest.class}) // Informa ao Spring quais classes devem ser carregas para os testes
@ActiveProfiles("test")
public class ProductDAOTest {

    @Autowired
    private ProductDAO productDAO;

    @Test
    @Transactional
    public void shouldSumAllPricesOfEachBookPerType() {
        List<Product> printedBooks = ProductBuilder.newProduct(BookType.PRINTED, BigDecimal.TEN).more(4).buildAll();
        printedBooks.stream().forEach(productDAO::save);
        List<Product> ebooks = ProductBuilder.newProduct(BookType.EBOOK, BigDecimal.TEN).more(4).buildAll();
        printedBooks.stream().forEach(productDAO::save);

        BigDecimal value = productDAO.sumPricesPerType(BookType.PRINTED);

        Assert.assertEquals(new BigDecimal(50).setScale(2), value);
    }

}
