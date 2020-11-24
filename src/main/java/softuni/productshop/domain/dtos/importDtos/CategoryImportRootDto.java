package softuni.productshop.domain.dtos.importDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryImportRootDto {

    @XmlElement(name = "category")
    private Set<CategoryImportDto> categories;

    public CategoryImportRootDto() {
    }

    public Set<CategoryImportDto> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryImportDto> categories) {
        this.categories = categories;
    }
}
