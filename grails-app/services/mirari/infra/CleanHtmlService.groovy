package mirari.infra

import org.jsoup.Jsoup
import org.jsoup.safety.Whitelist

class CleanHtmlService {

    static transactional = false

    String clean(String unsafe) {
        Jsoup.clean(unsafe, Whitelist.basic());
    }
}
