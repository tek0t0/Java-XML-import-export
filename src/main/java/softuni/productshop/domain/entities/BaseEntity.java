package softuni.productshop.domain.entities;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "id")
    private long id;

    public BaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
