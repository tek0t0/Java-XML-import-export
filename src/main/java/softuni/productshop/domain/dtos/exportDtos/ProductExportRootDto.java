package softuni.productshop.domain.dtos.exportDtos;

import javax.xml.bind.annotation.*;
import java.util.Set;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductExportRootDto {

    @XmlAttribute
    private int count;

    @XmlElement(name = "product")
    private Set<ProductExportDto> productExportDtoSet;

    public ProductExportRootDto() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Set<ProductExportDto> getProductExportDtoSet() {
        return productExportDtoSet;
    }

    public void setProductExportDtoSet(Set<ProductExportDto> productExportDtoSet) {
        this.productExportDtoSet = productExportDtoSet;
    }
}
