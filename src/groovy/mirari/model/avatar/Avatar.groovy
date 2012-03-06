@Typed package mirari.model.avatar

import com.google.code.morphia.annotations.Indexed
import com.google.code.morphia.annotations.PrePersist
import com.google.code.morphia.annotations.Transient
import mirari.vm.AvatarVM
import ru.mirari.infra.image.ImageFormat
import ru.mirari.infra.image.ImageHolder
import ru.mirari.infra.mongo.MorphiaDomain
import mirari.model.image.CommonImage
import mirari.model.image.CommonImageSrc

/**
 * @author alari
 * @since 12/13/11 5:07 PM
 */
class Avatar extends MorphiaDomain implements ImageHolder {
    @Override
    String getImagesBucket() {
        "mirariavatars"
    }

    @Override
    String getImagesPath() {
        "a/".concat(this.stringId)
    }

    @Override
    List<ImageFormat> getImageFormats() {
        DEFAULT_FORMATS
    }

    @Override
    ImageFormat getDefaultImageFormat() {
        IM_SMALL
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

    @Transient
    @Delegate private final CommonImage src = new CommonImageSrc(this)

    AvatarVM getViewModel() {
        AvatarVM.build(this)
    }
}