package net.droidpla.wordcounter.restws.wordcounterservice.controller.advice;

/**
 * Created by min2ha on 28/10/2017.
 */
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

@ControllerAdvice
public class JsonpControllerAdvice extends AbstractJsonpResponseBodyAdvice {

    public JsonpControllerAdvice() {
        super("callback");
    }

}
