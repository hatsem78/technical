package com.technicalchallenge.app.exceptionscustom;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/**
 * @BelongProjecet my-springboot-example
 * @BelongPackage com.saeyon.controller
 * @Date: 2021/6/27 7:29 afternoon
 * @Version V1.0
 * @Description:
 */
@ControllerAdvice
@EnableWebMvc
class DefaultExceptionHandler implements HandlerExceptionResolver {


    @Override
    public @ResponseBody
    ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(view);
        if (e instanceof ConstraintViolationException) {
            modelAndView.addObject("code", 400);
            modelAndView.addObject("msg", "Request parameter verification failed:" + e.getMessage());
            return modelAndView;
        }
        String msg = e.getMessage().replace("class com.technicalchallenge.app.models.entity.Customers", "");
        modelAndView.addObject("code", 400);
        modelAndView.addObject("msg", msg);
        return modelAndView;
    }
}
