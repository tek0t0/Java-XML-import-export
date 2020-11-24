package softuni.productshop.domain.dtos.exportDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsInRangeRootDto {

    @XmlElement(name = "product")
    private Set<ProductsInRangeExportDto> products;

    public ProductsInRangeRootDto() {
    }

    public Set<ProductsInRangeExportDto> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductsInRangeExportDto> products) {
        this.products = products;
    }
}
