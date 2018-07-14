package br.com.casadocodigo.loja.controllers;

import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

@Controller
@RequestMapping("/shopping")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ShoppingCartController {

    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private ShoppingCart shoppingCart;
    @Autowired
    private RestTemplate restTemplate;

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

    @RequestMapping(method = RequestMethod.GET)
    public String list() {
        return "shopping/items";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public Callable<String> checkout() {
        return () -> {
            BigDecimal total = shoppingCart.getTotal();

            String uriToPay = "http://book-payment.herokuapp.com/payment";

            try {
                String response = restTemplate.postForObject(uriToPay, new PaymentData(total), String.class);
                shoppingCart.esvazia();
                System.out.println(response);
                return "redirect:/products";
            } catch (HttpClientErrorException e) {
                System.out.println("Ocorreu um erro ao criar o pagamento: " + e.getMessage());
                return "redirect:/shopping";
            }
        };
    }


}
