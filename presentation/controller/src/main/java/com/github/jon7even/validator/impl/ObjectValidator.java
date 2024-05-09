package com.github.jon7even.validator.impl;

import com.github.jon7even.core.domain.v1.exception.MethodArgumentNotValidException;
import com.github.jon7even.validator.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.maven.shared.utils.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectValidator implements Validator<Object> {
    private static ObjectValidator instance;

    public static ObjectValidator getInstance() {
        if (instance == null) {
            instance = new ObjectValidator();
        }
        return instance;
    }

    @Override
    public void validate(Object obj, String name) {
        if (obj == null) {
            throw new MethodArgumentNotValidException(name, "не может быть пустым");
        }

        if (obj instanceof String) {
            String objString = (String) obj;
            if (StringUtils.isBlank(objString)) {
                throw new MethodArgumentNotValidException(name, "не может быть пустым");
            }
        }
    }
}