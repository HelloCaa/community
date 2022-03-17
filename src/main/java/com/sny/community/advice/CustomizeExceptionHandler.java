package com.sny.community.advice;

import com.alibaba.fastjson.JSON;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice()
public class CustomizeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleControllerException(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {

        String contentType = request.getContentType();

        if("application/json".equals(contentType)){
            ResultDTO resultDTO;
            //返回json
            if(e instanceof com.sny.community.exception.CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            }else {
                //显示不出来
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }

            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return null;
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
}

