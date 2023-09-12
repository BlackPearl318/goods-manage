package utils;

import org.example.pojo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//异常处理
@ControllerAdvice
public class FormatException {

    //指定统一捕获的异常类型
    @ExceptionHandler(NumberFormatException.class)
    @ResponseBody
    public Result doNumberFormatException(NumberFormatException e){
        e.printStackTrace();
        return new Result(false,null,"参数异常");
    }

}
