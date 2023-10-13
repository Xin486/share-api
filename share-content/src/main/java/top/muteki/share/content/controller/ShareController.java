package top.muteki.share.content.controller;

import cn.hutool.json.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.muteki.share.content.domain.ShareResp;
import top.muteki.share.content.domain.dto.ExchangeDTO;
import top.muteki.share.content.domain.dto.ShareRequestDTO;
import top.muteki.share.content.domain.entity.Notice;
import top.muteki.share.content.domain.entity.Share;
import top.muteki.share.content.service.NoticeService;
import top.muteki.share.content.service.ShareService;
import top.muteki.share.user.resp.CommonResp;
import top.muteki.share.user.util.JwtUtil;

import java.util.List;

/**
 * @author mqxu
 * @date 2023/10/8
 * @description ShareService
 **/
@RestController
@RequestMapping(value = "/share")
@Slf4j
public class ShareController {
    @Resource
    private NoticeService noticeService;

    @Resource
    private ShareService shareService;

    private final int MAX = 100;

    @GetMapping(value = "/notice")
    public CommonResp<Notice> getLatestNotice() {
        CommonResp<Notice> commonResp = new CommonResp<>();
        commonResp.setData(noticeService.getLatest());
        return commonResp;
    }

    @GetMapping("/list")
    public CommonResp<List<Share>> getShareList(@RequestParam(required = false) String title,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                                @RequestParam(required = false, defaultValue = "3") Integer pageSize,
                                                @RequestHeader(value = "token", required = false) String token) {
        if (pageSize > MAX) {
            pageSize = MAX;
        }
        int userId = getUserIdFromToken(token);
        CommonResp<List<Share>> commonResp = new CommonResp<>();
        commonResp.setData(shareService.getList(title, pageNo, pageSize, (long) userId));
        return commonResp;
    }

    /**
     * 封装一个从 token 中提取 userId 的方法
     *
     * @param token token
     * @return userId
     */
    private int getUserIdFromToken(String token) {
        log.info(">>>>>>>>>>> token" + token);
        int userId = 1;
        String noToken = "no-token";
        if (!noToken.equals(token)) {
            JSONObject jsonObject = JwtUtil.getJSONObject(token);
            log.info("解析到 token 的 json 数据为：{}", jsonObject);
            userId = (Integer) jsonObject.get("id");
        } else {
            log.info("没有 token");
        }
        return userId;
    }
    @GetMapping("/{id}")
    public CommonResp<ShareResp> getShareById(@PathVariable Long id){
        ShareResp shareResp=shareService.findById(id);
        CommonResp<ShareResp> commonResp=new CommonResp<>();
        commonResp.setData(shareResp);
        return commonResp;
    }
    @PostMapping("/exchange")
    public CommonResp<Share> exchange(@RequestBody ExchangeDTO exchangeDTO){
        System.out.printf(String.valueOf(exchangeDTO));
        CommonResp<Share> commonResp = new CommonResp<>();
        commonResp.setData(shareService.exchange(exchangeDTO));
        return commonResp;
    }
    @PostMapping("/contribute")
    public CommonResp<Integer> contributeShare(@RequestBody ShareRequestDTO shareRequestDTO,@RequestHeader(value = "token",required = false)String token){
        long userId=getUserIdFromToken(token);
        shareRequestDTO.setUserId(userId);
        CommonResp<Integer> commonResp=new CommonResp<>();
        commonResp.setData(shareService.contribute(shareRequestDTO));
        return commonResp;
    }
    @GetMapping("/my-contribute")
    public CommonResp<List<Share>> myContribute(
            @RequestParam(required = false,defaultValue = "1") Integer pageNo,
            @RequestParam(required = false,defaultValue = "3")Integer pageSize,
            @RequestHeader(value = "token",required = false)String token){
                if (pageSize >MAX){
                    pageSize = MAX;
                }
                long userId=getUserIdFromToken(token);
                CommonResp<List<Share>> commonResp=new CommonResp<>();
                commonResp.setData(shareService.myContribute(pageNo,pageSize,userId));
                return commonResp;
    }
}