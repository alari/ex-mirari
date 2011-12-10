package mirari.infra

import org.jsoup.Jsoup
import org.jsoup.safety.Whitelist

class CleanHtmlService {

    String clean(String unsafe) {
        Jsoup.clean(unsafe, Whitelist.basic());
    }
}
