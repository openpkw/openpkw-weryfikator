package org.openpkw.services.user.dto;

import org.openpkw.validation.RestClientErrorMessage;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ResponseDTO implements Serializable {

    private List<ErrorDTO> errors;

    public ResponseDTO() {
    }

    public List<ErrorDTO> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDTO> errors) {
        this.errors = errors;
    }

    public void addError(ErrorDTO error) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(error);
    }

    public static <T extends ResponseDTO> T buildErrorMessage(RestClientErrorMessage errorMessage, Class<T> type) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        T result = type.getDeclaredConstructor().newInstance();
        result.addError(new ErrorDTO(errorMessage.getErrorCode(), errorMessage.getErrorMessage()));
        return result;
    }
}
