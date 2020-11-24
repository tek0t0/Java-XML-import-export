package softuni.productshop.services;

import javax.xml.bind.JAXBException;

public interface UserService {
    void seedUsers() throws JAXBException;

    void seedFriends();

    String getAllUsersAndProducts();
}
