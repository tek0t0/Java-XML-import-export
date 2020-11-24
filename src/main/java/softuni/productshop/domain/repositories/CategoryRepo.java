package softuni.productshop.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.productshop.domain.entities.Category;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    @Query("select c from Category c order by c.products.size desc ")
    List<Category> findAllByProductCount();
}
