package softuni.productshop.domain.dtos.exportDtos;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryExportRootDto {

    @XmlElement(name = "category")
    private Set<CategoryExportDto> categories;

    public CategoryExportRootDto() {
    }

    public Set<CategoryExportDto> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryExportDto> categories) {
        this.categories = categories;
    }
}
