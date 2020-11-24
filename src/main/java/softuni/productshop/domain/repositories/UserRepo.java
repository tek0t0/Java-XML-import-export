package softuni.productshop.domain.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.productshop.domain.entities.User;

import java.util.Set;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("select u from User u where u.productsSold.size > 0 order by u.productsSold.size desc ,u.lastName ")
    Set<User> getUsersAndProducts();
}
