package com.orange.shop.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handler() {
//        return ResponseEntity.status(400).body("에러");
//    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView handleTypeMismatchException(MethodArgumentTypeMismatchException ex, Model model) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        mav.setStatus(HttpStatus.BAD_REQUEST);
        mav.addObject("message", "잘못된 요청입니다. URL에 올바른 형식의 ID를 입력해주세요.");
        return mav;
    }
}
