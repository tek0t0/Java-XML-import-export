package softuni.productshop.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.productshop.domain.entities.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findAllByPriceBetweenAndBuyerIsNull(BigDecimal min, BigDecimal max);
}
