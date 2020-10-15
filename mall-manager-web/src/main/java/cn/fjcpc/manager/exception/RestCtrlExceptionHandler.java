package cn.fjcpc.manager.exception;

import cn.fjcpc.common.exception.MallException;
import cn.fjcpc.common.pojo.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestCtrlExceptionHandler {

    @ExceptionHandler(MallException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Result handleException(Exception e){
        System.out.println("RestCtrlExceptionHandler.handleException");
        return new Result(e.getMessage(),0);
    }

}
