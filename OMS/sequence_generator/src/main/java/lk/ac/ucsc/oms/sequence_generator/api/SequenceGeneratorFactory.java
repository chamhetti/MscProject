package lk.ac.ucsc.oms.sequence_generator.api;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SequenceGeneratorFactory {

    private static ApplicationContext ctx = new ClassPathXmlApplicationContext("/implGeneral/spring-config-sequence-generate.xml");

    private SequenceGeneratorFactory() {
    }


    public static AbstractSequenceGenerator getInstance(SequenceType type) {
        return ctx.getBean(type.toString(), AbstractSequenceGenerator.class);
    }

}
