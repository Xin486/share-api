package top.muteki.share.content.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.muteki.share.content.domain.entity.User;
import top.muteki.share.user.resp.CommonResp;

@FeignClient(value = "user-service",path = "/user")
public interface UserService {
    @GetMapping("/{id}")
    CommonResp<User> getUser(@PathVariable Long id);
}
