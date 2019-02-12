package com.magic.spring.jmx;

import java.util.Map;

import javax.management.MBeanServer;

import com.magic.spring.DefaultDisruptorConfig;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Identify all disruptor beans and register them as MBeans.
 * <p> Add this to your spring configuration file and pass in the mbeanserver.
 * <pre>{@code
 *  <bean class="org.anair.disruptor.jmx.JmxDisruptorManager"
p:mBeanServer-ref="mbeanServer"/>
 * }</pre>
 *
 * @author Anoop Nair
 *
 */
@SuppressWarnings("rawtypes")
public class JmxDisruptorManager implements ApplicationContextAware{
    private static final Logger LOG = LoggerFactory.getLogger(JmxDisruptorManager.class);

    private ApplicationContext applicationContext;
    private MBeanServer mBeanServer;

    private void registerDisruptorMBeans()  {
        Map<String, DefaultDisruptorConfig> disruptorsMBeanMap = getDisruptorMBeans();

        if(MapUtils.isEmpty(disruptorsMBeanMap)){
            LOG.warn("No Disruptor beans identified.");
        }else{
            for(Map.Entry<String, DefaultDisruptorConfig> entry: disruptorsMBeanMap.entrySet()){
                try {
                    JmxDisruptor jmxDisruptor = new JmxDisruptor(entry.getValue(), entry.getKey());
                    mBeanServer.registerMBean(jmxDisruptor, jmxDisruptor.getObjectName());
                    LOG.debug(entry.getKey() + " Disruptor bean is resgistered in the MBeanServer.");
                } catch (Exception e) {
                    LOG.error("Error registering Disruptor MBean.", e);
                }
            }
            LOG.debug(disruptorsMBeanMap.size() + " Disruptor beans regsitered in the MBeanServer");
        }
    }

    private Map<String, DefaultDisruptorConfig> getDisruptorMBeans() {
        return this.applicationContext.getBeansOfType(DefaultDisruptorConfig.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException {
        this.applicationContext = applicationContext;
        registerDisruptorMBeans();
    }

    public void setmBeanServer(MBeanServer mBeanServer) {
        this.mBeanServer = mBeanServer;
    }

}
