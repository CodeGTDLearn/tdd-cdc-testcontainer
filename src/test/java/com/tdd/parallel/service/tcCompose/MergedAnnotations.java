package com.tdd.parallel.service.tcCompose;

import com.tdd.testsconfig.globalAnnotations.GlobalConfig;
import com.tdd.testsconfig.globalAnnotations.MongoDbConfig;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Testcontainers
@MongoDbConfig
@GlobalConfig
public @interface MergedAnnotations {
}