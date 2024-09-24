package org.aston.initializer;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


public class MainWebAppInitializer implements WebApplicationInitializer {


    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext root =
                new AnnotationConfigWebApplicationContext();

        root.scan("org.aston");
        servletContext.addListener(new ContextLoaderListener(root));

        ServletRegistration.Dynamic appServlet =
                servletContext.addServlet("mvc", new DispatcherServlet(new GenericWebApplicationContext()));
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/sp/*");
    }
}