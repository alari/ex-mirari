package mirari.model.image

import com.google.code.morphia.annotations.Embedded
import mirari.model.image.thumb.ThumbOrigin

/**
 * @author alari
 * @since 3/6/12 6:49 PM
 */
@Embedded
class PageImage implements CommonImage{
    PageImage(){}
    PageImage(final CommonImage image) {
        src = image
    }
    PageImage(final CommonImage image, int origin) {
        src = image
        this.origin = origin
    }

    void setSrc(final CommonImage image) {
        thumbSrc = image.thumbSrc
        smallSrc = image.smallSrc
        mediumSrc = image.mediumSrc
        standardSrc = image.standardSrc
        maxSrc = image.maxSrc
    }

    int origin = ThumbOrigin.TYPE_DEFAULT

    String thumbSrc
    String smallSrc
    String mediumSrc
    String standardSrc
    String maxSrc
}
