package com.github.wz2cool.elasticsearchdynamicquerydemo.query;

import com.github.wz2cool.elasticsearchdynamicquerydemo.TestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ContextConfiguration(classes = TestApplication.class)
public class ScanPackageTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void whenAssignableTypeFilterIsUsed_thenComponentScanShouldRegisterBean() {
        List<String> beans = Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(bean -> !bean.contains("org.springframework")
                        && !bean.contains("componentScanAssignableTypeFilterApp"))
                .collect(Collectors.toList());

        assertFalse(beans.contains("noExtendClassEsMpper"));
        assertFalse(beans.contains("noExtendInterfaceEsMapper"));
        assertTrue(beans.contains("studentEsMapper"));
        assertTrue(beans.contains("testExampleEsMapper"));
    }
}
