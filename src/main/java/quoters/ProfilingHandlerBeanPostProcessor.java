package quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Serg on 20.06.2019.
 */
public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {

    private Map<String,Class> map=new HashMap<>();
    private ProfilingController controller=new ProfilingController();


    public ProfilingHandlerBeanPostProcessor() throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(controller,new ObjectName("profiling","name","controller"));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?>beanClass=bean.getClass();
        if(beanClass.isAnnotationPresent(Profiling.class)){
            map.put(beanName,beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass=map.get(beanName);
        if(beanClass != null){
            return Proxy.newProxyInstance(beanClass.getClassLoader()
                    , beanClass.getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object o
                                , Method method
                                , Object[] objects) throws Throwable {
                            if(controller.isEnable()){
                                System.out.println("Profiling....");
                                long before = System.nanoTime();
                                Object retVal = method.invoke(bean, objects);
                                long after=System.nanoTime();
                                System.out.println("total "+ (after-before)+" nonosec");
                                System.out.println("End profiling ...");
                                return retVal;
                            }else{
                                return method.invoke(bean,objects);
                            }
                        }
                    });
        }
        return bean;
    }
}
