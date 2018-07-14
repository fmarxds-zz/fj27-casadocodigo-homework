package br.com.casadocodigo.loja.validations;

import br.com.casadocodigo.loja.models.Product;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ProductValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Product.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "field.required");

        Product product = (Product) o;
        if (product.getNumberOfPages() == 0) {
            errors.rejectValue("numberOfPages", "field.required");
        }
    }
}
