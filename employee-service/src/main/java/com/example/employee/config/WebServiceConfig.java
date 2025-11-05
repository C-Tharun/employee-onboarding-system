package com.example.employee.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import com.example.employee.soap.EmployeeSoapEndpoint;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean
    public XsdSchema employeeSchema() {
        return new SimpleXsdSchema(new ClassPathResource("employee.xsd"));
    }

    @Bean(name = "employee")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema employeeSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("EmployeePort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://example.com/employee/soap");
        wsdl11Definition.setSchema(employeeSchema);
        return wsdl11Definition;
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(
                EmployeeSoapEndpoint.GetEmployeeRequest.class,
                EmployeeSoapEndpoint.GetEmployeeResponse.class,
                EmployeeSoapEndpoint.CreateEmployeeRequest.class,
                EmployeeSoapEndpoint.CreateEmployeeResponse.class,
                EmployeeSoapEndpoint.UpdateEmployeeRequest.class,
                EmployeeSoapEndpoint.UpdateEmployeeResponse.class,
                EmployeeSoapEndpoint.DeleteEmployeeRequest.class,
                EmployeeSoapEndpoint.DeleteEmployeeResponse.class
        );
        return marshaller;
    }
}


