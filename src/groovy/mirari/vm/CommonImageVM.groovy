package mirari.vm

import mirari.model.image.CommonImage

/**
 * @author alari
 * @since 3/6/12 7:01 PM
 */
class CommonImageVM implements CommonImage{

    static public build(CommonImage image) {
        new CommonImageVM(image)
    }

    CommonImageVM(){}

    CommonImageVM(CommonImage image) {
        thumbSrc = image.thumbSrc
        smallSrc = image.smallSrc
        mediumSrc = image.mediumSrc
        standardSrc = image.standardSrc
        maxSrc = image.maxSrc
    }

    String thumbSrc
    String smallSrc
    String mediumSrc
    String standardSrc
    String maxSrc
}
