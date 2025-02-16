package com.eazybytes.accounts;

import com.eazybytes.accounts.command.interceptor.AccountsCommandInterceptor;
import com.eazybytes.common.config.AxonConfig;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@Import(AxonConfig.class)
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

    @Autowired
    public void registerAccountsCommandInterceptor(ApplicationContext context,
                                                   CommandGateway commandGateway){
        commandGateway.registerDispatchInterceptor(context.getBean(AccountsCommandInterceptor.class));
    }

    @Autowired
    public void configure(EventProcessingConfigurer configurer){
        configurer.registerListenerInvocationErrorHandler("accounts-group",
                conf -> PropagatingErrorHandler.instance());
    }

}
