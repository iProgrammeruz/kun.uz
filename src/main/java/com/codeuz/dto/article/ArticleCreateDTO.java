package com.codeuz.dto.article;
import com.codeuz.dto.TypesDTO;
import com.codeuz.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleCreateDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String content;

    @NotBlank
    private Integer imageId;

    @NotBlank
    private Integer regionId;

    @NotBlank
    private Integer categoryId;

    private Integer sharedCount;

    private ArticleStatus status;

    private List<Integer> types;




}
