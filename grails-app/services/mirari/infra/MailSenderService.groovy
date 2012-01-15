package mirari.infra

import grails.gsp.PageRenderer
import groovyx.gpars.GParsPool
import org.apache.log4j.Logger
import grails.util.Environment
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient
import org.jets3t.service.security.AWSCredentials
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest
import com.amazonaws.services.simpleemail.model.SendEmailRequest
import com.amazonaws.services.simpleemail.model.Destination
import com.amazonaws.services.simpleemail.model.Message
import com.amazonaws.services.simpleemail.model.Content
import com.amazonaws.services.simpleemail.model.Body
import com.amazonaws.services.simpleemail.model.SendEmailResult

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
        GParsPool.withPool(2) {GParsPool.executeAsync({
                handleMessage(args)
            })}
    }

    void handleMessage(Map<String, Object> message) {
        String s
        //try {
            AmazonSimpleEmailService client = new AmazonSimpleEmailServiceClient(new BasicAWSCredentials("AKIAINSHY2QZWHPJLZ5A", "Njo6goth5D2wumhg6wWE88BTisKzNXdY1Sxi04gK"))
            SendEmailRequest email = new SendEmailRequest()
            email.destination = new Destination([message.to])
            email.source = message.from ?: "noreply@mirari.ru"
            Body body = new Body()
            body.html = new Content(groovyPageRenderer.render(view: message.view, model: message.model))
            email.message = new Message(new Content((String)message.subject), body)
            SendEmailResult result = client.sendEmail(email)
            s = result.messageId

            println "Message sent ${s}"
        /*} catch (Exception e) {
            println "Exception"
            System.out.println e
            log.error(e)
        } */
        if(Environment.isDevelopmentMode()) {
            println s
        }
        log.info(s)
    }
}
