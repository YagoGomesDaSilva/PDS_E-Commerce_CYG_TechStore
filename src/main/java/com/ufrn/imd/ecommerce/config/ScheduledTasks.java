package com.ufrn.imd.ecommerce.config;

import com.ufrn.imd.ecommerce.services.AssinaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {


    private final AssinaturaService assinaturaService;

    public ScheduledTasks(AssinaturaService assinaturaService) {
        this.assinaturaService = assinaturaService;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Executa todos os dias à meia-noite
    public void performRenewals() {
        assinaturaService.renovarAssinaturas();
    }
}
