package com.nexus.hospitality_reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class NexusHospitalityReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexusHospitalityReservationApplication.class, args);
    }
}