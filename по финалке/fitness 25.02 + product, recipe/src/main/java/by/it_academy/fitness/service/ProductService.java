package by.it_academy.fitness.service;

import by.it_academy.fitness.core.dto.Page;
import by.it_academy.fitness.core.dto.Product;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.MyError;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import by.it_academy.fitness.dao.repositories.ProductRepository;
import by.it_academy.fitness.entity.ProductEntity;
import by.it_academy.fitness.service.api.IProductService;
import org.springframework.data.domain.PageRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductService implements IProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }
    @Override
    public void add(Product product) {
        if(product == null){
            throw new SingleErrorResponse("error", "Заполните форму для регистрации нового пользователя.");
        }
        validation(product);

        LocalDateTime dtCreate = LocalDateTime.now();
        LocalDateTime dtUpdate = dtCreate;
        ProductEntity productEntity = new ProductEntity(UUID.randomUUID(),
                dtCreate,
                dtUpdate,
                product.getTitle(),
                product.getWeight(),
                product.getCalories(),
                product.getProteins(),
                product.getFats(),
                product.getCarbohydrates());
        repository.save(productEntity);
    }

    @Override
    public Page<Product> getPageProducts(int page, int size) {

//        Product.ProductBuilder productBuilder = Product.ProductBuilder.create();
        org.springframework.data.domain.Page<ProductEntity> productEntityPage = repository.findAll(PageRequest.of(page, size));

        List<ProductEntity> productEntities = productEntityPage.toList();
        List<Product> products = new ArrayList<>();

        for (ProductEntity productEntity : productEntities) {
            products.add(productEntityToProduct(productEntity));
//                    productBuilder.setUuid(productEntity.getUuid())
//                    .setDt_create(productEntity.getDtCreate())
//                    .setDt_update(productEntity.getDtUpdate())
//                    .setTitle(productEntity.getTitle())
//                    .setWeight(productEntity.getWeight())
//                    .setCalories(productEntity.getCalories())
//                    .setProteins(productEntity.getProteins())
//                    .setFats(productEntity.getFats())
//                    .setCarbohydrates(productEntity.getCarbohydrates()).build());
        }

        Page<Product> pageOfProduct = new Page<>(productEntityPage.getNumber(),
                productEntityPage.getSize(),
                productEntityPage.getTotalPages(),
                productEntityPage.getTotalElements(),
                productEntityPage.isFirst(),
                productEntityPage.getNumberOfElements(),
                productEntityPage.isLast(),
                products);
        return pageOfProduct;
    }

    @Override
    public void updateProduct(UUID uuid, long dtUpdate, Product product) {
        if(uuid == null || product == null){
            throw new SingleErrorResponse("error","Не переданы параметры для обновления");
        }
        validation(product);

        if(repository.findById(uuid).isEmpty()){
            throw new SingleErrorResponse("error","Такого продукта для обновления не существует");
        }

        ProductEntity productEntity = repository.findById(uuid).get();

        if(productEntity.getDtUpdate().toEpochSecond(ZoneOffset.UTC) != (dtUpdate)){
            throw new SingleErrorResponse("error", "У вас не актуальная версия");
        }

        if(repository.findByTitle(product.getTitle()).isPresent() && !repository.findByTitle(product.getTitle()).get().getUuid().equals(uuid)){
            throw new SingleErrorResponse("error", "Указанное название продукта уже используется.");
        }
        productEntity.setTitle(product.getTitle());
        productEntity.setWeight(product.getWeight());
        productEntity.setCalories(product.getCalories());
        productEntity.setProteins(product.getProteins());
        productEntity.setFats(product.getFats());
        productEntity.setCarbohydrates(product.getCarbohydrates());
        repository.save(productEntity);
    }
    public Product productEntityToProduct (ProductEntity productEntity) {
        Product.ProductBuilder productBuilder = Product.ProductBuilder.create();

        Product product = productBuilder.setUuid(productEntity.getUuid())
                .setDt_create(productEntity.getDtCreate())
                .setDt_update(productEntity.getDtUpdate())
                .setTitle(productEntity.getTitle())
                .setWeight(productEntity.getWeight())
                .setCalories(productEntity.getCalories())
                .setProteins(productEntity.getProteins())
                .setFats(productEntity.getFats())
                .setCarbohydrates(productEntity.getCarbohydrates()).build();
        return product;
    }

    public ProductEntity getProductEntity (UUID uuid){
        if(repository.findById(uuid).isEmpty()){
            throw new SingleErrorResponse("error", "Продукта с указанным uuid не найдено.");
        }
        return repository.findById(uuid).get();
    }

    public void validation(Product product){
        MultipleErrorResponse multipleError = new MultipleErrorResponse();

        if(product.getTitle() == null || product.getTitle().isBlank()){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не заполнено.", "title"));
        }
        if(product.getWeight() == 0){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не может быть равно 0.", "weight"));
        }
        if(product.getWeight() < 0){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не может быть отрицательным.", "weight"));
        }
        if(product.getCalories() == 0){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не может быть равно 0.", "calories"));
        }
        if(product.getCalories() < 0){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не может быть отрицательным.", "calories"));
        }
        if(product.getProteins() == 0){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не может быть равно 0.", "proteins"));
        }
        if(product.getProteins() < 0){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не может быть отрицательным.", "proteins"));
        }
        if(product.getFats() < 0){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не может быть отрицательным.", "fats"));
        }
        if(product.getCarbohydrates() == 0){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не может быть равно 0.", "carbohydrates"));
        }
        if(product.getCarbohydrates() < 0){
            if(multipleError.getLogref() == null){
                multipleError.setLogref("Проверьте введенные данные!");
            }
            multipleError.setErrors(new MyError("Поле не может быть отрицательным.", "carbohydrates"));
        }

//        if(repository.findByTitle(product.getTitle()).isPresent()){
//            if(multipleError.getLogref() == null){
//                multipleError.setLogref("Проверьте введенные данные!");
//            }
//            multipleError.setErrors(new MyError("Продукт с таким названием уже существует.", "title"));
//        }
        if(multipleError.getErrors().size()>0){
            throw multipleError;
        }
    }
}
