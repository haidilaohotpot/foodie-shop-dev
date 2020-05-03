package com.wonder4work.exception;

import com.wonder4work.utils.JSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @since 1.0.0 2020/4/27
 *
 * 统一异常捕获
 */
@RestControllerAdvice
public class CustomExceptionHandler {



    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public JSONResult handlerMaxUploadFile(MaxUploadSizeExceededException e) {

        return JSONResult.errorMsg("文件大小不能超过500K,请重新选择文件上传");
    }

}
