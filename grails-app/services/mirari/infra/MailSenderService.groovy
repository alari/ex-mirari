package mirari.infra

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClient
import grails.gsp.PageRenderer
import grails.util.Environment
import groovyx.gpars.GParsPool
import org.apache.log4j.Logger
import com.amazonaws.services.simpleemail.model.*

class MailSenderService {
    static transactional = false
    private Logger log = Logger.getLogger(getClass())

    //static rabbitQueue = "mailSenderQueue"

    PageRenderer groovyPageRenderer

    /**
     * @arg to
     * @arg from
     * @arg view
     * @arg model
     * @arg body
     * @arg subject
     */
    void putMessage(Map<String, Object> args) {
        //rabbitSend "mail", "mailSenderQueue", args
        GParsPool.withPool(2) {
            GParsPool.executeAsync({
                handleMessage(args)
            })
        }
    }

    void handleMessage(Map<String, Object> message) {
        String s
        AmazonSimpleEmailService client = new AmazonSimpleEmailServiceAsyncClient(new BasicAWSCredentials("AKIAINSHY2QZWHPJLZ5A", "Njo6goth5D2wumhg6wWE88BTisKzNXdY1Sxi04gK"))
        SendEmailRequest email = new SendEmailRequest()
        email.destination = new Destination([message.to])
        email.source = message.from ?: "noreply@mirari.ru"
        Body body = new Body()
        body.html = new Content(groovyPageRenderer.render(view: message.view, model: message.model))
        email.message = new Message(new Content((String) message.subject), body)
        SendEmailResult result = client.sendEmail(email)
        s = result.messageId

        println "Message sent ${s}"
        if (Environment.isDevelopmentMode()) {
            println s
        }
        log.info(s)
    }
}
