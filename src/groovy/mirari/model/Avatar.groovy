@Typed package mirari.model

import com.google.code.morphia.annotations.Indexed
import ru.mirari.infra.image.ImageFormat
import ru.mirari.infra.image.ImageHolder

import ru.mirari.infra.mongo.MorphiaDomain
import mirari.ko.AvatarViewModel
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import mirari.util.ApplicationContextHolder
import ru.mirari.infra.image.ImageStorageService

/**
 * @author alari
 * @since 12/13/11 5:07 PM
 */
class Avatar extends MorphiaDomain implements ImageHolder {
    transient static public final ImageFormat LARGE = new ImageFormat("210*336", "ava-large")
    transient static public final ImageFormat FEED = new ImageFormat("100*160", "ava-feed")
    transient static public final ImageFormat TINY = new ImageFormat("90*90", "ava-tiny")

    static protected transient ImageStorageService imageStorageService

    static {
        imageStorageService = (ImageStorageService) ApplicationContextHolder.getBean("imageStorageService")
    }

    String getImagesBucket() {
        null
    }

    String getImagesPath() {
        "a/".concat(this.stringId)
    }

    List<ImageFormat> getImageFormats() {
        [
                FEED,
                LARGE,
                TINY
        ]
    }

    ImageFormat getDefaultImageFormat() {
        FEED
    }

    boolean basic = false
    @Indexed(unique = true)
    String name
    
    AvatarViewModel getViewModel() {
        new AvatarViewModel(
                id: stringId,
                srcFeed: imageStorageService.getUrl(this, FEED),
                srcLarge: imageStorageService.getUrl(this, LARGE),
                srcTiny: imageStorageService.getUrl(this, TINY),
                basic: basic,
                name: name
        )
    }
}
