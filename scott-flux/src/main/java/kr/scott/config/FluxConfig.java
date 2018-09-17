package kr.scott.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication(scanBasePackages = {"kr.scott.flux"})
@EnableWebFlux
public class FluxConfig {
}
