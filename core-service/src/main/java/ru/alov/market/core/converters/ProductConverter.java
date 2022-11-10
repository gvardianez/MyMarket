package ru.alov.market.core.converters;

import org.springframework.stereotype.Component;
import ru.alov.market.api.dto.ProductDto;
import ru.alov.market.core.entities.Product;

@Component
public class ProductConverter {
    public ProductDto entityToDto(Product p) {
        ProductDto productDto = new ProductDto();
        productDto.setId(p.getId());
        productDto.setTitle(p.getTitle());
        productDto.setPrice(p.getPrice());
        productDto.setCategoryTitle(p.getCategory().getTitle());
        return productDto;
    }
}
