package com.example.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 多语言服务.
 * Created by yuheng.lin.
 * Date : 2017/5/19
 */
public interface IMessageService {
    public String getMessage(String code);

    public String getMessage(String code,String defaultMessage);

    public String getMessage(String code,String defaultMessage,Locale locale);

    public String getMessage(String code,Locale locale);

    public String getMessage(String code,Object[] args);

    public String getMessage(String code,Object[] args,Locale locale);

    public String getMessage(String code,Object[] args,String defaultMessage);

    String getMessage(String code,Object[]args,String defaultMessage,Locale locale);

    List<Map<String,String>> getAvailableLocales();
}
