package br.com.casadocodigo.loja.daos;

import br.com.casadocodigo.loja.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
@Repository
public class UserDAO implements UserDetailsService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String sql = "SELECT u FROM User u WHERE u.login = :login";

        List<User> users = manager.createQuery(sql, User.class).setParameter("login", username).getResultList();

        if (users.isEmpty()) {
            throw new UsernameNotFoundException("O usuário '" + username + "' não existe");
        }

        return users.get(0);
    }
}
