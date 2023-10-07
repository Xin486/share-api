package top.muteki.share.user.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.muteki.share.user.exception.BusinessException;
import top.muteki.share.user.exception.BusinessExceptionEnum;
import top.muteki.share.user.resp.CommonResp;
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public CommonResp<?> exceptionHandler(BusinessException e)throws Exception{
        CommonResp<?> resp=new CommonResp<>();
        log.error("系统异常",e);
        resp.setSuccess(false);
        resp.setMessage(e.getE().getDesc());
        return resp;
    }
}
