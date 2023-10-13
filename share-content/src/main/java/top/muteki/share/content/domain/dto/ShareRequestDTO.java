package top.muteki.share.content.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareRequestDTO {
    private Long userId;
    private String author;
    private String title;
    private Boolean isOriginal;
    private Integer price;
    private String downloadUrl;
    private String cover;
    private String summary;
}
