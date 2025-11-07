package com.app.bluecotton.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductInfoDetailResponseDTO {

    private Long id;
    private String productName;
    private String productMainDescription;
    private String productSubDescription;
    private String productWeight;
    private String productSize;
    private String productMaterial;
    private String productMainImageUrl;
    private String productSubImageUrl;


}
