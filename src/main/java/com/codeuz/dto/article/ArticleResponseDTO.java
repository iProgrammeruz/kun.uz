package com.codeuz.dto.article;

import com.codeuz.dto.AttachDTO;
import com.codeuz.dto.CategoryDTO;
import com.codeuz.dto.RegionDTO;
import com.codeuz.mapper.RegionMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleResponseDTO {
    private String id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private Integer viewCount;
    private Long likeCount;
    private Long dislikeCount;
    private String imageId;
    private Integer regionId;
    private Integer categoryId;
    private List<Integer> typesList;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private AttachDTO image;
    private RegionMapper region;
    private CategoryDTO category;

}
