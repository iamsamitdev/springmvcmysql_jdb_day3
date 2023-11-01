package com.itgenius.springmvcmysql_jdb.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itgenius.springmvcmysql_jdb.model.Product;
import com.itgenius.springmvcmysql_jdb.repository.ProductRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // Get All Product
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    // Get Product By Id
    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    // Create Product
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Update Product
    public Product updateProduct(int id, Product updateProduct) {
        if (productRepository.existsById(id)) {
            updateProduct.setId(id);
            return productRepository.save(updateProduct);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    // Delete Product
    public void deleteProduct(int id){
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }
}
