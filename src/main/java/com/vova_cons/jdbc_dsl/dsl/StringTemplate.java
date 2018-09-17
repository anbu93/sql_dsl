package com.vova_cons.jdbc_dsl.dsl;

import java.util.Objects;

/**
 * Created by anbu on 17.09.18.
 **/
public class StringTemplate {
    private String template;

    public StringTemplate(String template) {
        this.template = template;
    }

    public StringTemplate set(String key, Object value) {
        return new StringTemplate(template.replaceAll("\\{"+key+"\\}", Objects.toString(value)));
    }

    public String get() {
        return template;
    }
}
