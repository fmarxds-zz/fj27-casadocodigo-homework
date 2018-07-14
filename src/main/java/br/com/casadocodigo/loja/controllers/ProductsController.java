package br.com.casadocodigo.loja.controllers;

import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductDAO productDAO;

    /** Este métoo deixou de ser usado após integrar o BeanValidation diretamente no Model
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new ProductValidator());
    }
    */

    @RequestMapping(value = {"/form"}, method = RequestMethod.GET)
    public ModelAndView form(Product product){
        ModelAndView view = new ModelAndView("products/form");
        view.addObject("types", BookType.values());
        return view;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(){
        ModelAndView view = new ModelAndView("products/list");
        view.addObject("products", productDAO.list());
        return view;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@Valid Product livro, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return form(livro);
        }
        productDAO.save(livro);
        attributes.addFlashAttribute("livro_cadastrado_sucesso", "Livro " + livro.getTitle() + " cadastrado com sucesso!");
        return new ModelAndView("redirect:products");
    }

}
