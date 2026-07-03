package ec.edu.ups.icc.fundamentos01.products.mappers;

import ec.edu.ups.icc.fundamentos01.categories.dtos.CategoryResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.entities.ProductEntity;
import ec.edu.ups.icc.fundamentos01.products.models.ProductModel;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;

public class ProductMapper {

    public static ProductModel toModelFromDTO(CreateProductDto dto) {
        ProductModel model = new ProductModel();
        model.setName(dto.getName());
        model.setPrice(dto.getPrice());
        model.setStock(dto.getStock());
        model.setOwnerId(dto.getUserId());
        return model;
    }

    public static ProductModel toModelFromEntity(ProductEntity entity) {
        ProductModel model = new ProductModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setPrice(entity.getPrice());
        model.setStock(entity.getStock());
        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setDeleted(entity.isDeleted());

        if (entity.getOwner() != null) {
            model.setOwnerId(entity.getOwner().getId());
            model.setOwnerName(entity.getOwner().getName());
            model.setOwnerEmail(entity.getOwner().getEmail());
        }

        if (entity.getCategories() != null) {
            model.setCategories(
                    entity.getCategories().stream()
                            .map(c -> new ProductModel.CategoryInfo(
                                    c.getId(),
                                    c.getName(),
                                    c.getDescription()
                            ))
                            .toList()
            );
        }

        return model;
    }

    public static ProductEntity toEntityFromModel(ProductModel model) {
        ProductEntity entity = new ProductEntity();
        entity.setName(model.getName());
        entity.setPrice(model.getPrice());
        entity.setStock(model.getStock());
        return entity;
    }

    public static ProductResponseDto toResponse(ProductModel model) {
        ProductResponseDto response = new ProductResponseDto();
        response.setId(model.getId());
        response.setName(model.getName());
        response.setPrice(model.getPrice());
        response.setStock(model.getStock());
        response.setCreatedAt(model.getCreatedAt());
        response.setUpdatedAt(model.getUpdatedAt());

        if (model.getOwnerId() != null) {
            response.setOwner(new UserResponseDto(
                    model.getOwnerId(),
                    model.getOwnerName(),
                    model.getOwnerEmail()
            ));
        }

        if (model.getCategories() != null) {
            response.setCategories(
                    model.getCategories().stream()
                            .map(c -> new CategoryResponseDto(
                                    c.getId(),
                                    c.getName(),
                                    c.getDescription()
                            ))
                            .toList()
            );
        }

        return response;
    }
}
