package top.muteki.share.content.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.muteki.share.content.domain.enums.AuditStatusEnum;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareAuditDTO {
    private AuditStatusEnum auditStatusEnum;
    private String reason;
    private Boolean showFlag;
}
