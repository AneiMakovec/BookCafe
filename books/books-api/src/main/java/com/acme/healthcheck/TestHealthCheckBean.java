package com.acme.healthcheck;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped
public class TestHealthCheckBean implements HealthCheck {

    private boolean failure = false;

    public HealthCheckResponse call() {
        if (this.failure)
            return HealthCheckResponse.named(TestHealthCheckBean.class.getSimpleName()).down().build();
        else
            return HealthCheckResponse.named(TestHealthCheckBean.class.getSimpleName()).up().build();
    }

    public void switchFailure() {
        this.failure = true;
        System.out.println("INFO: Resetting service.");
    }
}
