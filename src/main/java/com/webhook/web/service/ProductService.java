package com.webhook.web.service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final WebhookService webhookService;

    public ProductResponse create(ProductRequest request) {
        Product product = Product.builder()
            .name(request.getName())
            .price(request.getPrice())
            .description(request.getDescription())
            .build();

        Product saved = repository.save(product);
        webhookService.send("PRODUCT_CREATED", saved);
        return toResponse(saved);
    }

    public List<ProductResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ProductResponse findById(Long id) {
        return repository.findById(id)
            .map(this::toResponse)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + id));
    }

    public ProductResponse update(Long id, ProductRequest request) {
        Product product = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + id));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());

        Product updated = repository.save(product);
        webhookService.send("PRODUCT_UPDATED", updated);
        return toResponse(updated);
    }

    public void delete(Long id) {
        Product product = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + id));

        repository.delete(product);
        webhookService.send("PRODUCT_DELETED", Map.of("id", id));
    }

    private ProductResponse toResponse(Product p) {
        return ProductResponse.builder()
            .id(p.getId())
            .name(p.getName())
            .price(p.getPrice())
            .description(p.getDescription())
            .createdAt(p.getCreatedAt())
            .build();
    }
}