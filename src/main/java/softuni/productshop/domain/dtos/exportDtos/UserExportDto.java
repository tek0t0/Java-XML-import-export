package softuni.productshop.domain.dtos.exportDtos;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserExportDto {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlAttribute(name = "age")
    private int age;

    @XmlElement(name = "sold-products")
    private ProductExportRootDto productExportRootDto;

    public UserExportDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ProductExportRootDto getProductExportRootDto() {
        return productExportRootDto;
    }

    public void setProductExportRootDto(ProductExportRootDto productExportRootDto) {
        this.productExportRootDto = productExportRootDto;
    }
}
