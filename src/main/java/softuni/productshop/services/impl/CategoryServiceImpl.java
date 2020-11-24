package softuni.productshop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import softuni.productshop.domain.dtos.exportDtos.CategoryExportDto;
import softuni.productshop.domain.dtos.exportDtos.CategoryExportRootDto;
import softuni.productshop.domain.dtos.importDtos.CategoryImportDto;
import softuni.productshop.domain.dtos.importDtos.CategoryImportRootDto;
import softuni.productshop.domain.entities.Category;
import softuni.productshop.domain.entities.Product;
import softuni.productshop.domain.repositories.CategoryRepo;
import softuni.productshop.domain.repositories.ProductRepo;
import softuni.productshop.services.CategoryService;
import softuni.productshop.utils.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORIES_PATH = "src/main/resources/xmls/input/categories.xml";
    private static final String CATEGORIES_BY_PRODUCTS_PATH = "src/main/resources/xmls/output/categories-by-products.xml";

    private final CategoryRepo categoryRepo;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ProductRepo productRepo;

    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo, XmlParser xmlParser, ModelMapper modelMapper, ProductRepo productRepo) {
        this.categoryRepo = categoryRepo;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.productRepo = productRepo;
    }

    @Override
    public void seedCategories() throws JAXBException {
        if (this.categoryRepo.findAll().size() == 0) {
            CategoryImportRootDto categoryImportRootDto
                    = this.xmlParser.parseXml(CategoryImportRootDto.class, CATEGORIES_PATH);
            for (CategoryImportDto categoryDto : categoryImportRootDto.getCategories()) {
                Category category = this.modelMapper.map(categoryDto, Category.class);
                this.categoryRepo.saveAndFlush(category);
            }
        }
    }

    @Override
    public String getProductsByCount() {
        List<Category> allByProductCount = this.categoryRepo.findAllByProductCount();
        Set<CategoryExportDto> collectDtos = new LinkedHashSet<>();

        for (Category category : allByProductCount) {
            CategoryExportDto dto = this.modelMapper.map(category, CategoryExportDto.class);
            List<Product> all = this.productRepo.findAll();
            dto.setProductCount(category.getProducts().size());
            BigDecimal sum = category.getProducts().stream()
                    .map(Product::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            sum = sum.setScale(2, RoundingMode.HALF_UP);
            dto.setTotalRevenue(sum);
            BigDecimal avgSum = sum.divide(BigDecimal.valueOf(category.getProducts().size()), 6, RoundingMode.HALF_UP);
            dto.setAvgPrice(avgSum);
            collectDtos.add(dto);
        }

        CategoryExportRootDto categoryExportRootDto = new CategoryExportRootDto();
        categoryExportRootDto.setCategories(collectDtos);

        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(CATEGORIES_BY_PRODUCTS_PATH);
            if (file.createNewFile()) {
                this.xmlParser.exportXml(categoryExportRootDto, CategoryExportRootDto.class, CATEGORIES_BY_PRODUCTS_PATH);
                sb.append("File created: ")
                        .append(file.getName())
                        .append(System.lineSeparator())
                        .append("Categories Exported!").append(System.lineSeparator());
            } else {
                this.xmlParser.exportXml(categoryExportRootDto, CategoryExportRootDto.class, CATEGORIES_BY_PRODUCTS_PATH);
                sb.append("File already exists.").append(System.lineSeparator())
                        .append("Categories Exported!").append(System.lineSeparator());
            }
        } catch (IOException | JAXBException e) {
            sb.append("An error occurred.").append(System.lineSeparator());
        }
        return sb.toString();
    }


}
