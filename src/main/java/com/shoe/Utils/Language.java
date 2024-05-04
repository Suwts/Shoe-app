package com.shoe.Utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@RequiredArgsConstructor
@Configuration
public class Language {
    @Autowired
    private MessageSource messageSource;

    private final LocaleResolver localeResolver;

    public String getLocale(String message){
        HttpServletRequest request = WebUtils.getRequest();
        Locale locale = localeResolver.resolveLocale(request);
        return  messageSource.getMessage(message,null, locale);
    }
}
