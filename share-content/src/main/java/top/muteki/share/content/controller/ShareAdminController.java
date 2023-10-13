package top.muteki.share.content.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.muteki.share.content.domain.entity.Share;
import top.muteki.share.content.service.ShareService;
import top.muteki.share.user.resp.CommonResp;

import java.util.List;

@RestController
@RequestMapping("/share/admin")
public class ShareAdminController {
    @Resource
    private ShareService shareService;
    @GetMapping(value = "/list")
    public CommonResp<List<Share>> getSharesNotYet(){
        CommonResp<List<Share>> commonResp=new CommonResp<>();
        commonResp.setData(shareService.querySharesNotYet());
        return commonResp;
    }
}
