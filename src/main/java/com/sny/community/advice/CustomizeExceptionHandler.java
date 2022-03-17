package com.sny.community.advice;

import com.mysql.cj.util.StringUtils;
import com.sny.community.dto.ResultDTO;
import com.sny.community.exception.CustomizeErrorCode;
import com.sny.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice()
public class CustomizeExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object handleControllerException(Throwable e, Model model, HttpServletRequest request) {

        String contentType = request.getContentType();

        if("application/json".equals(contentType)){
            //返回json
            if(e instanceof com.sny.community.exception.CustomizeException){
                return ResultDTO.errorOf((CustomizeException) e);
            }else {
                //显示不出来
                return ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }
        }else {
            //错误页面跳转
            if(e instanceof com.sny.community.exception.CustomizeException){
                model.addAttribute("message", e.getMessage());
            }else {
                //显示不出来
                model.addAttribute("message", CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer code = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus status = HttpStatus.resolve(code);
        return (status != null) ? status : HttpStatus.INTERNAL_SERVER_ERROR;
    }

}

