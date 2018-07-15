package br.com.casadocodigo.loja.controllers;

import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private FileSaver fileSaver;

    /**
     * Este métoo deixou de ser usado após integrar o BeanValidation diretamente no Model
     *
     * @InitBinder public void initBinder(WebDataBinder binder) {
     * binder.addValidators(new ProductValidator());
     * }
     */

    @RequestMapping(value = {"/form"}, method = RequestMethod.GET)
    public ModelAndView form(Product product) {
        ModelAndView view = new ModelAndView("products/form");
        view.addObject("types", BookType.values());
        return view;
    }

    @Cacheable(value = "lastProducts")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView view = new ModelAndView("products/list");
        view.addObject("products", productDAO.list());
        return view;
    }

    @CacheEvict(value = "lastProducts", allEntries = true)
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(MultipartFile summary, @Valid Product livro, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return form(livro);
        }

        String webPath = fileSaver.write("uploaded-summaries", summary);
        livro.setSummaryPath(webPath);

        productDAO.save(livro);

        attributes.addFlashAttribute("livro_cadastrado_sucesso", "Livro " + livro.getTitle() + " cadastrado com sucesso!");
        return new ModelAndView("redirect:products");
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
    public ModelAndView show(@PathVariable("id") Integer id) {
        ModelAndView view = new ModelAndView("products/show");
        view.addObject("product", productDAO.find(id));
        return  view;
    }

}
