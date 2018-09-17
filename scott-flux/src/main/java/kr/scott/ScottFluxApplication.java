package kr.scott;

import kr.scott.config.CoreConfig;
import kr.scott.config.FluxConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

public class ScottFluxApplication
{

    public static void main(String[] args)
    {
        new SpringApplicationBuilder(CoreConfig.class).child(FluxConfig.class)
                .listeners(new ApplicationListener<ApplicationFailedEvent>() {
                    @Override
                    public void onApplicationEvent(ApplicationFailedEvent event) {
                        System.out.println("Spring boot start fail.");
                    }
                })
                .listeners(new ApplicationListener<ApplicationReadyEvent>() {
                    @Override
                    public void onApplicationEvent(ApplicationReadyEvent event) {
                        System.out.println("Spring boot start complete");
                    }
                })
                .run(args);
    }
}
