package com.wehook.web.dto.response;

@Data
@Builder
@Schema(description = "Resposta com dados do produto")
public class ProductResponse {

    @Schema(description = "ID único do produto", example = "1")
    private Long id;

    @Schema(example = "Notebook Dell")
    private String name;

    @Schema(example = "3499.90")
    private BigDecimal price;

    @Schema(example = "Notebook Dell Inspiron 15...")
    private String description;

    @Schema(description = "Data de criação")
    private LocalDateTime createdAt;
}