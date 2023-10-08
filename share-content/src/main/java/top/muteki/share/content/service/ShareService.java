package top.muteki.share.content.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.util.StringUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.muteki.share.content.domain.entity.MidUserShare;
import top.muteki.share.content.domain.entity.Share;
import top.muteki.share.content.mapper.MidUserShareMapper;
import top.muteki.share.content.mapper.ShareMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShareService {
    @Resource
    private ShareMapper shareMapper;
    @Resource
    private MidUserShareMapper midUserShareMapper;

    public List<Share> getList(String title, Long userId) {
        LambdaQueryWrapper<Share> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Share::getId);
        if (StringUtil.isNotEmpty(title)) {
            wrapper.like(Share::getTitle, title);
        }
        wrapper.eq(Share::getAuditStatus, "PASS").eq(Share::getShowFlag, true);
        List<Share> shares = shareMapper.selectList(wrapper);
        List<Share> sharesDeal;
        if (userId == null) {
            sharesDeal = shares.stream().peek(share -> share.setDownloadUrl(null)).collect(Collectors.toList());
        } else {
            sharesDeal = shares.stream().peek(share -> {
                MidUserShare midUserShare = midUserShareMapper.selectOne(new QueryWrapper<MidUserShare>().lambda()
                        .eq(MidUserShare::getUserId, userId)
                        .eq(MidUserShare::getShareId, share.getId()));
                if (midUserShare == null) {
                    share.setDownloadUrl(null);
                }


            }).collect(Collectors.toList());
        }
        return sharesDeal;
    }
}
