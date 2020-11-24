package softuni.productshop.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface ProductService {
    void seedProducts() throws JAXBException;

    String getProductsInRange() throws IOException;
}
