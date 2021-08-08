package me.andrew;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContext implements ApplicationContextAware {
    private static ApplicationContext context;

    public SpringContext(ApplicationContext context) {
       this.context = context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
    public ApplicationContext getAppContext() {
        return this.context;
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }
}
