package com.wehook.web.dto.request;

@Data
@Schema(description = "Dados para criação/atualização de produto")
public class ProductRequest {

    @Schema(description = "Nome do produto", example = "Notebook Dell")
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @Schema(description = "Preço do produto", example = "3499.90")
    @NotNull
    @Positive
    private BigDecimal price;

    @Schema(description = "Descrição detalhada", example = "Notebook Dell Inspiron 15, 16GB RAM, SSD 512GB")
    private String description;
}