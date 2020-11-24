package softuni.productshop.services;

import javax.xml.bind.JAXBException;

public interface CategoryService {
    void seedCategories() throws JAXBException;

    String getProductsByCount();
}
