package softuni.productshop.domain.dtos.importDtos;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserImportRootDto {

    @XmlElement(name = "user")
    private Set<UserImportDto> users;

    public UserImportRootDto() {
    }

    public Set<UserImportDto> getUsers() {
        return users;
    }

    public void setUsers(Set<UserImportDto> users) {
        this.users = users;
    }
}
