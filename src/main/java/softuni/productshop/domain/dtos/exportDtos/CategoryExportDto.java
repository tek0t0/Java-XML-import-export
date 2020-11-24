package softuni.productshop.domain.dtos.exportDtos;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;


@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryExportDto {



    @XmlAttribute
    private String name;

    @XmlElement(name = "products-count")
    private int productCount;

    @XmlElement(name = "average-price")
    private BigDecimal avgPrice;

    @XmlElement(name = "total-revenue")
    private BigDecimal totalRevenue;

    public CategoryExportDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
