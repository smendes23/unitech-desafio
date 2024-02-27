package br.com.unitechdesafio;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisabledInAotMode
public abstract class BaseTestRunner {
}
