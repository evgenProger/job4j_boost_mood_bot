package ru.job4j;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanNameAware implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("ApplicationContext set in ApplicationContextAwareExample");
    }

    public void displayAllBeanNames() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        System.out.println("Beans in ApplicationContext:");
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }

}
