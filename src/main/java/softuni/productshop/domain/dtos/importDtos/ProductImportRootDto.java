package softuni.productshop.domain.dtos.importDtos;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductImportRootDto {

    @XmlElement(name = "product")
    private Set<ProductImportDto> products;

    public ProductImportRootDto() {
    }


    public Set<ProductImportDto> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductImportDto> products) {
        this.products = products;
    }
}
