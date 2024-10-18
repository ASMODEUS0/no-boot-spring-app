package configuration;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
//Внедряется в жизненный цикл теста и создает пустой контекст.
@ExtendWith(SpringExtension.class)
//Определяется для предоставления информации как загружать контекст, SpringExtension, видя эту аннотацию -
// добавляет все компоненты на которые она указывает в ApplicationContext
//ExtendWith(SpringExtension) и ContextConfiguration(TestConfiguration.class) можно заменить SpringJUnitConfig(TestConfiguration.class)
@ContextConfiguration(classes = TestConfiguration.class)
public @interface IT {

}
