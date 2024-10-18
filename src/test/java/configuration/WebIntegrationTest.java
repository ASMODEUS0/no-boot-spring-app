package configuration;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

//Гарантирует, что контекст, создаваемый при помощи SpringExtension будет WebApplicationContext
@WebAppConfiguration
@ActiveProfiles("web")
public abstract class WebIntegrationTest extends IntegrationTest{
}
