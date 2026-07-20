package ec.edu.ups.icc.fundamentos01.products.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Schema(description = "Datos para actualizar parcialmente un producto. Solo se modifican los campos enviados")
public class PartialUpdateProductDto {

    @Schema(description = "Nuevo nombre del producto (opcional)", example = "Laptop Gaming Edición Especial")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    private String name;

    @Schema(description = "Nuevo precio del producto (opcional), no puede ser negativo", example = "1100.0")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser negativo")
    private Double price;

    @Schema(description = "Nuevo stock disponible (opcional)", example = "15")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Schema(description = "Nuevas categorías del producto (opcional)", example = "[2, 3]")
    @Size(min = 1, message = "Debe seleccionar al menos una categoría")
    private Set<Long> categoryIds;

    public PartialUpdateProductDto() {}

    public PartialUpdateProductDto(String name, Double price, Integer stock, Set<Long> categoryIds) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.categoryIds = categoryIds;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Set<Long> getCategoryIds() { return categoryIds; }
    public void setCategoryIds(Set<Long> categoryIds) { this.categoryIds = categoryIds; }
}
