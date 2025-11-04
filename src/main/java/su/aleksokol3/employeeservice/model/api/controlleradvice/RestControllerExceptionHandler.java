package su.aleksokol3.employeeservice.model.api.controlleradvice;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackages = "su.aleksokol3.employeeservice.controller")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
    // TODO:

}
