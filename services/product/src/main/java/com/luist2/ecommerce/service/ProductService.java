package com.luist2.ecommerce.service;

import com.luist2.ecommerce.dto.ProductPurchaseRequest;
import com.luist2.ecommerce.dto.ProductPurchaseResponse;
import com.luist2.ecommerce.dto.ProductRequest;
import com.luist2.ecommerce.dto.ProductResponse;
import com.luist2.ecommerce.exception.ProductPurchaseException;
import com.luist2.ecommerce.mapper.ProductMapper;
import com.luist2.ecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Integer createProduct(@Valid ProductRequest productRequest) {
        var product = productMapper.toProduct(productRequest);
        return productRepository.save(product).getId();
    }

    @Transactional
    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> productPurchaseRequest) {
        // Obtener los IDs de los productos desde las solicitudes de compra
        var productIds = productPurchaseRequest.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        // Buscar en la base de datos los productos con los IDs obtenidos, ordenados por ID
        var storedProducts = productRepository.findAllByIdInOrderById(productIds);

        // Si no se encontraron todos los productos solicitados, lanzar una excepción
        if (storedProducts.size() != productIds.size()) {
            throw new ProductPurchaseException("Some products not found");
        }

        // Ordenar las solicitudes de compra por ID del producto para que coincidan con el orden de los productos obtenidos
        var storedRequests = productPurchaseRequest
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        // Lista que almacenará las respuestas exitosas de la compra
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        // Iterar sobre los productos encontrados y sus solicitudes correspondientes
        for (int i = 0; i < storedProducts.size(); i++) {
            var storedProduct = storedProducts.get(i);       // Producto actual del repositorio
            var requestProduct = storedRequests.get(i);      // Solicitud de compra correspondiente

            // Validar si hay suficiente cantidad disponible para cubrir la solicitud
            if (requestProduct.quantity() > storedProduct.getAvailableQuantity()) {
                throw new ProductPurchaseException("Not enough available quantity for product ID: " + storedProduct.getId());
            }

            // Descontar la cantidad solicitada del stock disponible
            storedProduct.setAvailableQuantity(
                    storedProduct.getAvailableQuantity() - requestProduct.quantity()
            );

            // Guardar el producto actualizado en el repositorio
            productRepository.save(storedProduct);

            // Generar la respuesta de compra y agregarla a la lista
            purchasedProducts.add(
                    productMapper.toProductPurchaseResponse(storedProduct, requestProduct.quantity())
            );
        }

        // Retornar la lista de productos comprados con su información correspondiente
        return purchasedProducts;
    }


    public ProductResponse getProduct(Integer productId) {
        return productRepository.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
