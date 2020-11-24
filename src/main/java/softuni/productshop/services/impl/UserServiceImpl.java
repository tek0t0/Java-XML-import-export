package softuni.productshop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import softuni.productshop.domain.dtos.exportDtos.*;
import softuni.productshop.domain.dtos.importDtos.UserImportDto;
import softuni.productshop.domain.dtos.importDtos.UserImportRootDto;
import softuni.productshop.domain.entities.User;
import softuni.productshop.domain.repositories.UserRepo;
import softuni.productshop.services.UserService;
import softuni.productshop.utils.ValidatorUtil;
import softuni.productshop.utils.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final String USERS_PATH = "src/main/resources/xmls/input/users.xml";
    private static final String USERS_PRODUCTS_EXPORT_PATH = "src/main/resources/xmls/output/users-and-products.xml";
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, ModelMapper modelMapper, XmlParser xmlParser, ValidatorUtil validatorUtil) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public void seedUsers() throws JAXBException {
        if (this.userRepo.findAll().size() == 0) {
            UserImportRootDto userImportRootDto = this.xmlParser.parseXml(UserImportRootDto.class, USERS_PATH);
            for (UserImportDto userDto : userImportRootDto.getUsers()) {
                if (validatorUtil.isValid(userDto)) {
                    User user = this.modelMapper.map(userDto, User.class);
                    this.userRepo.saveAndFlush(user);
                }

            }
        }
    }

    @Override
    public void seedFriends() {
        List<User> all = this.userRepo.findAll();
        for (User user : all) {
            user.setFriends(getRandomFriends());
            this.userRepo.saveAndFlush(user);
        }
    }

    @Override
    public String getAllUsersAndProducts() {
        StringBuilder sb = new StringBuilder();
        Set<User> users = this.userRepo.getUsersAndProducts();
        Set<UserExportDto> userExportDtos = new LinkedHashSet<>();
        for (User user : users) {
            UserExportDto exportDto = this.modelMapper.map(user, UserExportDto.class);
            Set<ProductExportDto> products = user.getProductsSold()
                    .stream()
                    .map(p -> this.modelMapper.map(p, ProductExportDto.class))
                    .collect(Collectors.toSet());

            ProductExportRootDto productExportRootDto = new ProductExportRootDto();
            productExportRootDto.setCount(products.size());
            productExportRootDto.setProductExportDtoSet(products);
            exportDto.setProductExportRootDto(productExportRootDto);
            userExportDtos.add(exportDto);
        }
        UserExportRootDto userExportRootDto = new UserExportRootDto();
        userExportRootDto.setUsers(userExportDtos);
        userExportRootDto.setCount(users.size());

        try {
            File file = new File(USERS_PRODUCTS_EXPORT_PATH);
            if (file.createNewFile()) {
                this.xmlParser.exportXml(userExportRootDto, UserExportRootDto.class, USERS_PRODUCTS_EXPORT_PATH);
                sb.append("File created: ")
                        .append(file.getName())
                        .append(System.lineSeparator())
                        .append("Users Exported!").append(System.lineSeparator());
            } else {
                this.xmlParser.exportXml(userExportRootDto, UserExportRootDto.class, USERS_PRODUCTS_EXPORT_PATH);
                sb.append("File already exists.").append(System.lineSeparator())
                        .append("Users Exported!").append(System.lineSeparator());
            }
        } catch (IOException | JAXBException e) {
            sb.append("An error occurred.").append(System.lineSeparator());
        }
        return sb.toString();
    }

    private Set<User> getRandomFriends() {
        Set<User> friends = new HashSet<>();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            long randomLong = random.nextInt(this.userRepo.findAll().size()) + 1;
            friends.add(this.userRepo.getOne(randomLong));
        }
        return friends;
    }
}
