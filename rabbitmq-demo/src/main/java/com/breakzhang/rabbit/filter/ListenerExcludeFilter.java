package com.breakzhang.rabbit.filter;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;


/**
 * @author: Created by zhangsf
 * @datetime: Created in 15:50 2021/4/8
 * @description: 停止监听
 */
public class ListenerExcludeFilter extends TypeExcludeFilter {

    private static final String RABBIT_LISTENER = "org.springframework.amqp.rabbit.annotation.RabbitListener";
    private static final String RABBIT_HANDLER = "org.springframework.amqp.rabbit.annotation.RabbitHandler";

    @Override
    public boolean match(MetadataReader metadataReader,
                         MetadataReaderFactory metadataReaderFactory) {
        AnnotationMetadata annotationMetadata =
                metadataReader.getAnnotationMetadata();
        return annotationMetadata.hasAnnotatedMethods(RABBIT_LISTENER)
                || annotationMetadata.hasAnnotatedMethods(RABBIT_HANDLER);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return 1;
    }

}
