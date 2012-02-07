@Typed package mirari.model

import com.google.code.morphia.annotations.Indexed
import mirari.ko.AvatarViewModel
import mirari.util.ApplicationContextHolder
import ru.mirari.infra.image.ImageFormat
import ru.mirari.infra.image.ImageHolder
import ru.mirari.infra.image.ImageStorageService
import ru.mirari.infra.mongo.MorphiaDomain
import com.google.code.morphia.annotations.PrePersist

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
    
    Date dateCreated = new Date()
    Date lastUpdated

    @PrePersist
    void prePersist() {
        lastUpdated = new Date()
    }
    
    String getSrcFeed() {
        imageStorageService.getUrl(this, FEED)
    }
    
    String getSrcLarge() {
        imageStorageService.getUrl(this, LARGE)
    }
    
    String getSrcTiny() {
        imageStorageService.getUrl(this, TINY)
    }

    AvatarViewModel getViewModel() {
        new AvatarViewModel(
                id: stringId,
                srcFeed: srcFeed,
                srcLarge: srcLarge,
                srcTiny: srcTiny,
                basic: basic,
                name: name
        )
    }
}