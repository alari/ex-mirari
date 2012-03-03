package ru.mirari.infra.mail

import mirari.event.Event
import mirari.event.EventType
import org.springframework.context.i18n.LocaleContextHolder

/**
 * @author alari
 * @since 2/13/12 11:02 PM
 */
class MailEvent extends Event{
    MailEvent() {
        super(EventType.EMAIL_SEND)
        final Locale locale = LocaleContextHolder.getLocale()
        putParams(locale: [language: locale.language, country: locale.country])
    }
    
    MailEvent view(String view) {
        putParams(view: view)
        this
    }
    
    MailEvent model(Map<String,Object> model) {
        putParams(model: model)
        this
    }
    
    MailEvent to(String to) {
        putParams(to: to)
        this
    }
    
    MailEvent subject(String subject) {
        putParams(subject: subject)
        this
    }
}
