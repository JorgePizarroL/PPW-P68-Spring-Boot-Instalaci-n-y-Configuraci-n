package ec.edu.ups.icc.fundamentos01.products.services;

import ec.edu.ups.icc.fundamentos01.core.dtos.PaginationDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.CreateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.PartialUpdateProductDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductFilterByUserDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.ProductResponseDto;
import ec.edu.ups.icc.fundamentos01.products.dtos.UpdateProductDto;
import ec.edu.ups.icc.fundamentos01.security.services.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ProductService {

    List<ProductResponseDto> findAll();

    ProductResponseDto findOne(Long id);

    /*
     * Crea un producto usando como owner al usuario autenticado.
     */
    ProductResponseDto create(CreateProductDto dto, UserDetailsImpl currentUser);

    /*
     * Actualiza completamente un producto. Se valida ownership en el servicio.
     */
    ProductResponseDto update(Long id, UpdateProductDto dto, UserDetailsImpl currentUser);

    /*
     * Actualiza parcialmente un producto. Se valida ownership en el servicio.
     */
    ProductResponseDto partialUpdate(Long id, PartialUpdateProductDto dto, UserDetailsImpl currentUser);

    /*
     * Elimina lógicamente un producto. Se valida ownership en el servicio.
     */
    void delete(Long id, UserDetailsImpl currentUser);

    List<ProductResponseDto> findByUserId(Long userId);

    List<ProductResponseDto> findByCategoryId(Long categoryId);

    List<ProductResponseDto> findByUserIdWithFilters(Long userId, ProductFilterByUserDto filters);

    List<ProductResponseDto> findByCategoryIdWithFilters(Long categoryId, ProductFilterByUserDto filters);

    /*
     * Retorna productos activos usando Page.
     */
    Page<ProductResponseDto> findAllPage(PaginationDto pagination);

    
    /*
     * Retorna productos activos usando Slice.
     */
    Slice<ProductResponseDto> findAllSlice(PaginationDto pagination);

    /*
     * Retorna productos de una categoría con filtros y Page.
     */
    Page<ProductResponseDto> findByCategoryIdWithFiltersPage(Long categoryId, ProductFilterByUserDto filters, PaginationDto pagination);

    /*
     * Retorna productos de una categoría con filtros y Slice.
     */
    Slice<ProductResponseDto> findByCategoryIdWithFiltersSlice(Long categoryId, ProductFilterByUserDto filters, PaginationDto pagination);
}
