package kr.scott.api.config;


import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventConfig
{

    @EventListener
    public void handleApplicationReadyEvent(ApplicationReadyEvent event)
    {
        log.info("Spring boot start complete");
    }


    @EventListener
    public void handleApplicationFailedEvent(ApplicationFailedEvent event)
    {
        log.error("Spring boot start fail.");
        log.error(Throwables.getStackTraceAsString(event.getException()));
    }


    @EventListener
    public void handleContextRefreshe(ContextRefreshedEvent event)
    {
        log.debug("Context refresh ==");
    }


    @EventListener
    public void handleAuthenticationFailureBadCredential(AuthenticationFailureBadCredentialsEvent event)
    {
        log.debug("event => {}", event);
        log.debug("source source => {}", event.getSource());

        Authentication authentication = event.getAuthentication();
        if(authentication != null) {
            log.debug("authentication name => {}", authentication.getName());
            log.debug("authentication details => {}", authentication.getDetails());
        }
    }


}
