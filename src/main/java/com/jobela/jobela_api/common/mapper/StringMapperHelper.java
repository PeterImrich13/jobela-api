package com.jobela.jobela_api.common.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;


@Component
public class StringMapperHelper {

    @Named("clean")
    public String clean(String value) {
        return value == null ? null : value.strip();
    }
}
