package com.tdd.parallel.service.tcContainer.annotation;

import com.tdd.testsconfig.globalAnnotations.GlobalConfig;
import com.tdd.testsconfig.globalAnnotations.MongoDbConfig;
import com.tdd.testsconfig.tcContainer.annotations.TcContainer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@TcContainer
@MongoDbConfig
@GlobalConfig
public @interface MergedAnnotations {
}
