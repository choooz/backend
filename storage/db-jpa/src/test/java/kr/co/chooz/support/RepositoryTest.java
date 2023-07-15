package kr.co.chooz.support;

import kr.co.chooz.TestConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@DataJpaTest
@TestPropertySource("classpath:application-jpa-test.yml")
@ContextConfiguration(classes = TestConfiguration.class)
public @interface RepositoryTest {}
