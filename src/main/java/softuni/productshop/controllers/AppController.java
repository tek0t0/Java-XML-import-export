package softuni.productshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.productshop.services.CategoryService;
import softuni.productshop.services.ProductService;
import softuni.productshop.services.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;


@Component
public class AppController implements CommandLineRunner {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;


    @Autowired
    public AppController(UserService userService, ProductService productService, CategoryService categoryService) {
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
    }


    @Override
    public void run(String... args) throws Exception {
        this.userService.seedUsers();
        this.categoryService.seedCategories();
        this.productService.seedProducts();
        this.userService.seedFriends();


        System.out.println("Select query from Product Shop Ex ---> You don`t need to create output files in advance! The App will do that!");
        System.out.println("1.Products in Range");
        System.out.println("2.Successfully Sold Products");
        System.out.println("3.Categories by Products Count");
        System.out.println("4.Users and Products");
        System.out.println("You don`t need to create output files in advance! The App will create them!");
        String input = reader.readLine();
        while (true) {
            switch (input) {
                case "1":
                    System.out.println(this.productService.getProductsInRange());
                    break;
                case "2":
                    System.out.println("Sorry, try another one :)");
                    break;
                case "3":
                    System.out.println(this.categoryService.getProductsByCount());
                    break;
                case "4":
                    System.out.println(this.userService.getAllUsersAndProducts());
                    break;
                case "0":
                    System.out.println("Thank You and Good Luck!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
            System.out.println("Select query from Car Dealer Ex");
            input = reader.readLine();
        }
    }
}
