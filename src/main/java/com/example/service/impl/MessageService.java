package com.example.service.impl;

import com.example.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 多语言服务.
 * Created by yuheng.lin.
 * Date : 2017/5/19
 */
@Service
public class MessageService implements IMessageService {

    @Autowired
    private MessageSource messageSource;

    @Override
    public String getMessage(String code){
        return this.getMessage(code,new Object[]{});
    }

    @Override
    public String getMessage(String code,String defaultMessage){
        return this.getMessage(code, null,defaultMessage);
    }

    @Override
    public String getMessage(String code,String defaultMessage,Locale locale){
        return this.getMessage(code, null,defaultMessage,locale);
    }

    @Override
    public String getMessage(String code,Locale locale){
        return this.getMessage(code,null,"",locale);
    }

    /**
     *
     * @param code ：对应messages配置的key.
     * @param args : 数组参数.
     * @return
     */
    @Override
    public String getMessage(String code,Object[] args){
        return this.getMessage(code, args,"");
    }

    @Override
    public String getMessage(String code,Object[] args,Locale locale){
        return this.getMessage(code, args,"",locale);
    }

    /**
     *
     * @param code ：对应messages配置的key.
     * @param args : 数组参数.
     * @param defaultMessage : 没有设置key的时候的默认值.
     * @return
     */
    @Override
    public String getMessage(String code,Object[] args,String defaultMessage){
        //这里使用比较方便的方法，不依赖request.
        Locale locale =LocaleContextHolder.getLocale();
        return this.getMessage(code, args, defaultMessage, locale);

    }

    /**
     * 指定语言.
     * @param code
     * @param args
     * @param defaultMessage
     * @param locale
     * @return
     */
    @Override
    public String getMessage(String code,Object[]args,String defaultMessage,Locale locale){
        return messageSource.getMessage(code, args, defaultMessage,locale);
    }

    @Override
    public List<Map<String,String>> getAvailableLocales() {
        List<Map<String, String>> list = new ArrayList<>();
        Locale[] availableLocales = Locale.getAvailableLocales();
        for (Locale locale:availableLocales) {
            Map<String,String> map = new HashMap<>();
            map.put("locale",locale.toString());
            map.put("name",locale.getDisplayName(locale));
            list.add(map);
        }
        return list;
    }

}
