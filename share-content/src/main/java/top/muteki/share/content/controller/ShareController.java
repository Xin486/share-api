package top.muteki.share.content.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.muteki.share.content.domain.entity.Notice;
import top.muteki.share.content.service.NoticeService;
import top.muteki.share.user.resp.CommonResp;

@RestController
@RequestMapping("/share")
public class ShareController {
    @Resource
    private NoticeService noticeService;
    @GetMapping(value = "/notice")
    public CommonResp<Notice> getLatestNotice(){
        CommonResp<Notice> commonResp = new CommonResp<>();
        commonResp.setData(noticeService.getLatest());
        return commonResp;
    }
}
