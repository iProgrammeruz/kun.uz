package com.codeuz.dto.article;
import com.codeuz.entity.TypesEntity;
import com.codeuz.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {

    private String id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private Integer imageId;
    private Integer regionId;
    private Integer categoryId;
    private Integer moderatorId;
    private Integer publisherId;
    private ArticleStatus status;
    LocalDateTime createdDate;
    LocalDateTime publishedDate;
    private Boolean visible;
    private Integer viewCount;
    private List<Integer> types;



}
