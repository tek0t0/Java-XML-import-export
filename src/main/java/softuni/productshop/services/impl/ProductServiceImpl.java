package softuni.productshop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import softuni.productshop.domain.dtos.exportDtos.ProductsInRangeExportDto;
import softuni.productshop.domain.dtos.exportDtos.ProductsInRangeRootDto;
import softuni.productshop.domain.dtos.importDtos.ProductImportDto;
import softuni.productshop.domain.dtos.importDtos.ProductImportRootDto;
import softuni.productshop.domain.entities.Category;
import softuni.productshop.domain.entities.Product;
import softuni.productshop.domain.entities.User;
import softuni.productshop.domain.repositories.CategoryRepo;
import softuni.productshop.domain.repositories.ProductRepo;
import softuni.productshop.domain.repositories.UserRepo;
import softuni.productshop.services.ProductService;
import softuni.productshop.utils.ValidatorUtil;
import softuni.productshop.utils.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String PRODUCTS_PATH = "src/main/resources/xmls/input/products.xml";
    private static final String PRODUCTS_IN_RANGE_PATH = "src/main/resources/xmls/output/products-in-range.xml";
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;


    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, ModelMapper modelMapper, XmlParser xmlParser, UserRepo userRepo, ValidatorUtil validatorUtil, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.userRepo = userRepo;
        this.validatorUtil = validatorUtil;
        this.categoryRepo = categoryRepo;
    }

    @Transactional
    @Override
    public void seedProducts() throws JAXBException {
        if (this.productRepo.findAll().size() == 0) {
            ProductImportRootDto productImportRootDto = this.xmlParser.parseXml(ProductImportRootDto.class, PRODUCTS_PATH);
            for (ProductImportDto productImportDto : productImportRootDto.getProducts()) {
                if (this.validatorUtil.isValid(productImportDto)) {
                    Product product = this.modelMapper.map(productImportDto, Product.class);

                    product.setBuyer(getRandomUser());
                    product.setSeller(getRandomUser());
                    if (product.getSeller().getId() == product.getBuyer().getId()) {
                        product.setSeller(getRandomUser());
                    }
                    this.productRepo.saveAndFlush(product);
                    product.setCategories(getRandomCategories());
                    this.productRepo.saveAndFlush(product);
                }
            }
            List<Product> all = this.productRepo.findAll();
            for (Product product : all) {
                if (product.getId() % 7 == 0) {
                    product.setBuyer(null);
                    this.productRepo.saveAndFlush(product);
                }
            }


        }
    }

    @Override
    public String getProductsInRange() throws IOException {
        System.out.println("Enter range min");
        BigDecimal min = BigDecimal.valueOf(Long.parseLong(reader.readLine()));
        System.out.println("Enter range max");
        BigDecimal max = BigDecimal.valueOf(Long.parseLong(reader.readLine()));

        Set<ProductsInRangeExportDto> products = new LinkedHashSet<>();
        List<Product> allByPriceBetweenAndBuyerIsNull = this.productRepo.findAllByPriceBetweenAndBuyerIsNull(min, max);
        for (Product product : allByPriceBetweenAndBuyerIsNull) {
            ProductsInRangeExportDto productToExport = this.modelMapper.map(product, ProductsInRangeExportDto.class);
            productToExport.setSeller(product.getSeller().getFirstName() + " " + product.getSeller().getLastName());
            products.add(productToExport);
        }
        ProductsInRangeRootDto productsInRangeRootDto = new ProductsInRangeRootDto();
        productsInRangeRootDto.setProducts(products);

        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(PRODUCTS_IN_RANGE_PATH);
            if (file.createNewFile()) {
                this.xmlParser.exportXml(productsInRangeRootDto, ProductsInRangeRootDto.class, PRODUCTS_IN_RANGE_PATH);
                sb.append("File created: ")
                        .append(file.getName())
                        .append(System.lineSeparator())
                        .append("Products Exported!").append(System.lineSeparator());
            } else {
                this.xmlParser.exportXml(productsInRangeRootDto, ProductsInRangeRootDto.class, PRODUCTS_IN_RANGE_PATH);
                sb.append("File already exists.").append(System.lineSeparator())
                        .append("Products Exported!").append(System.lineSeparator());
            }
        } catch (IOException | JAXBException e) {
            sb.append("An error occurred.").append(System.lineSeparator());
        }
        return sb.toString();

    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            long randomLong = random.nextInt(this.categoryRepo.findAll().size()) + 1;
            categories.add(this.categoryRepo.getOne(randomLong));
        }

        return categories;
    }

    private User getRandomUser() {
        Random random = new Random();

        int i = random.nextInt(this.userRepo.findAll().size()) + 1;
        return this.userRepo.getOne((long) i);
    }
}
