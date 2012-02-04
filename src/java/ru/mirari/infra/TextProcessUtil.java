package ru.mirari.infra;

import org.pegdown.PegDownProcessor;
import org.pegdown.Extensions;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * @author alari
 * @since 2/2/12 5:33 PM
 */
public class TextProcessUtil {
    static private ThreadLocal<PegDownProcessor> processorThreadLocal = new ThreadLocal<PegDownProcessor>();

    static public PegDownProcessor getPegDownProcessor() {
        if (processorThreadLocal.get() == null) {
            processorThreadLocal.set(new PegDownProcessor(Extensions.ALL));
        }
        return processorThreadLocal.get();
    }

    static public String cleanHtml(String unsafe) {
        return unsafe == null || unsafe.isEmpty() ? "" : Jsoup.clean(unsafe, Whitelist.basic());
    }

    static public String markdownToHtml(String text) {
        return text != null ? cleanHtml(getPegDownProcessor().markdownToHtml(text)) : "";
    }
}
