package softuni.productshop.domain.dtos.exportDtos;

import javax.xml.bind.annotation.*;
import java.util.Set;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserExportRootDto {

    @XmlAttribute
    private int count;

    @XmlElement(name = "user")
    private Set<UserExportDto> users;

    public UserExportRootDto() {
    }

    public Set<UserExportDto> getUsers() {
        return users;
    }

    public void setUsers(Set<UserExportDto> users) {
        this.users = users;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
