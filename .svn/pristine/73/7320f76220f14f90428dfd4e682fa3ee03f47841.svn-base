package com.tk.oms.cache;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.tk.oms.sysuser.service.IpdbService;

/**
 * 应用启动初始化资源
 *
 */
public class CacheBeanPostProcess implements BeanPostProcessor{
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        try{
            if(o instanceof IpdbService) {
                ((IpdbService) o).load(IpdbService.DB_PATH_s);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return o;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }
}
