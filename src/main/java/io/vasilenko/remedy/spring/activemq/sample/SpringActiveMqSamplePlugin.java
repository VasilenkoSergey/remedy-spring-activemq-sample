package io.vasilenko.remedy.spring.activemq.sample;

import com.bmc.arsys.api.Value;
import com.bmc.arsys.pluginsvr.plugins.ARFilterAPIPlugin;
import com.bmc.arsys.pluginsvr.plugins.ARPluginContext;
import com.bmc.thirdparty.org.slf4j.Logger;
import com.bmc.thirdparty.org.slf4j.LoggerFactory;
import io.vasilenko.remedy.spring.activemq.sample.mq.MqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class SpringActiveMqSamplePlugin extends ARFilterAPIPlugin {

    private static final int INPUT_MESSAGE_VALUE_INDEX = 0;

    private final Logger log = LoggerFactory.getLogger(SpringActiveMqSamplePlugin.class);

    private AnnotationConfigApplicationContext applicationContext;

    @Autowired
    private MqSender sender;

    @Override
    public void initialize(ARPluginContext context) {
        applicationContext = new AnnotationConfigApplicationContext(SpringActiveMqSamplePlugin.class.getPackage().getName());
        applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
        log.info("initialized");
    }

    @Override
    public List<Value> filterAPICall(ARPluginContext arPluginContext, List<Value> list) {
        String message = String.valueOf(list.get(INPUT_MESSAGE_VALUE_INDEX));
        sender.sendMessage(message);
        List<Value> output = new ArrayList<>();
        output.add(new Value(message + " sent"));
        return output;
    }

    @Override
    public void terminate(ARPluginContext context) {
        applicationContext.close();
    }
}
