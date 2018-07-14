package br.com.casadocodigo.loja.controllers;

import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;
import br.com.casadocodigo.loja.models.ShoppingCart;
import br.com.casadocodigo.loja.models.ShoppingItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/shopping")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ShoppingCartController {

    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private ShoppingCart shoppingCart;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView add(Integer productId, @RequestParam BookType bookType) {
        ShoppingItem item = createItem(productId, bookType);
        shoppingCart.add(item);
        return new ModelAndView("redirect:/products");
    }

    private ShoppingItem createItem(Integer productId, BookType bookType) {
        Product product = productDAO.find(productId);
        ShoppingItem item = new ShoppingItem(product, bookType);
        return item;
    }


}
