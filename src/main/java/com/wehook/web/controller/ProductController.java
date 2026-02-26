package com.wehook.web.controller;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Endpoints para gerenciamento de produtos")
public class ProductController {

    private final ProductService service;

    @Operation(summary = "Criar produto", description = "Cria um novo produto e dispara webhook PRODUCT_CREATED")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@RequestBody @Valid ProductRequest request) {
        return service.create(request);
    }

    @Operation(summary = "Listar todos os produtos")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public List<ProductResponse> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Buscar produto por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto encontrado"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ProductResponse findById(
        @Parameter(description = "ID do produto", required = true)
        @PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza os dados e dispara webhook PRODUCT_UPDATED")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Produto atualizado"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    public ProductResponse update(
        @Parameter(description = "ID do produto", required = true)
        @PathVariable Long id,
        @RequestBody @Valid ProductRequest request) {
        return service.update(id, request);
    }

    @Operation(summary = "Deletar produto", description = "Remove o produto e dispara webhook PRODUCT_DELETED")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Produto deletado"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
        @Parameter(description = "ID do produto", required = true)
        @PathVariable Long id) {
        service.delete(id);
    }
}