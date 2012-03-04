package ru.mirari.infra.mail

import mirari.event.EventListenerBean
import mirari.event.EventType
import mirari.event.Event
import grails.gsp.PageRenderer
import grails.plugin.mail.MailService
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author alari
 * @since 2/13/12 10:58 PM
 */
class MailSendListenerBean extends EventListenerBean{
    @Autowired PageRenderer groovyPageRenderer
    @Autowired MailService mailService
    
    @Override
    boolean filter(EventType type) {
        EventType.EMAIL_SEND == type
    }

    @Override
    void handle(Event event) {
        Map message = event.params
        ((Map)message.model).put("locale", new Locale((String)message.locale.language, (String)message.locale.country))
        mailService.sendMail {
            to message.to
            subject message.subject
            html groovyPageRenderer.render(view: message.view, model: message.model)
        }
    }
}
