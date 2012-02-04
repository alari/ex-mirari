package mirari.infra

import grails.gsp.PageRenderer
import groovyx.gpars.GParsPool
import org.apache.log4j.Logger

class MailSenderService {
    static transactional = false
    private Logger log = Logger.getLogger(getClass())

    //static rabbitQueue = "mailSenderQueue"

    PageRenderer groovyPageRenderer
    def mailService

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
        mailService.sendMail {
            to message.to
            subject message.subject
            html groovyPageRenderer.render(view: message.view, model: message.model)
        }
    }
}
