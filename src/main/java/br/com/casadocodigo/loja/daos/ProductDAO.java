package br.com.casadocodigo.loja.daos;

import br.com.casadocodigo.loja.models.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
@Repository
public class ProductDAO {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public void save(Product livro) {
        manager.persist(livro);
    }

    public List<Product> list(){
        String query = "SELECT DISTINCT p FROM Product p JOIN FETCH p.prices";
        return manager.createQuery(query, Product.class).getResultList();
    }

    public Product find(Integer id) {
        String sql = "SELECT DISTINCT p FROM Product p JOIN FETCH p.prices WHERE p.id = :id";
        return manager.createQuery(sql, Product.class).setParameter("id", id).getSingleResult();
    }
}
