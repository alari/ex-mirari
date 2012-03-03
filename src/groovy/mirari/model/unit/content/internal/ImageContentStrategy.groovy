@Typed package mirari.model.unit.content.internal

import mirari.model.unit.content.ContentHolder
import mirari.model.unit.content.ImageFormats
import mirari.vm.UnitVM
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.file.FileInfo
import ru.mirari.infra.image.ImageFormat
import ru.mirari.infra.image.ImageHolder
import ru.mirari.infra.image.ImageStorageService

/**
 * @author alari
 * @since 1/6/12 6:30 PM
 */
class ImageContentStrategy extends InternalContentStrategy {
    @Autowired
    private ImageStorageService imageStorageService

    private ImageHolder getImageHolder(ContentHolder unit) {
        new ImgHolder(unit.stringId)
    }

    @Override
    void attachContentToViewModel(ContentHolder unit, UnitVM unitViewModel) {
        ImageHolder holder = getImageHolder(unit)
        unitViewModel.params.putAll(
                srcPage: imageStorageService.getUrl(holder, ImageFormats.PAGE),
                srcFeed: imageStorageService.getUrl(holder, ImageFormats.FEED),
                srcMax: imageStorageService.getUrl(holder, ImageFormats.MAX),
                srcThumb: imageStorageService.getUrl(holder, ImageFormats.THUMB)
        )
    }

    @Override
    void setViewModelContent(ContentHolder unit, UnitVM unitVM) {
        void
    }

    @Override
    void setContentFile(ContentHolder unit, FileInfo fileInfo) {
        if (!isContentFileSupported(fileInfo)) return;
        imageStorageService.format(getImageHolder(unit), fileInfo.file)
        unit.title = fileInfo.title
    }

    @Override
    boolean isContentFileSupported(FileInfo type) {
        type.mediaType == "image"
    }

    @Override
    void saveContent(ContentHolder unit) {
        void
    }

    @Override
    void deleteContent(ContentHolder unit) {
        imageStorageService.delete(getImageHolder(unit))
    }

    static public class ImgHolder implements ImageHolder {
        private final String unitId
        public final String imagesPath
        public final String imagesBucket = "mirariimages"

        ImgHolder(String unitId) {
            this.unitId = unitId
            imagesPath = "i/".concat(unitId)
        }

        @Override
        String getImagesPath() {
            imagesPath
        }

        @Override
        String getImagesBucket() {
            imagesBucket
        }

        @Override
        List<ImageFormat> getImageFormats() {
            [
                    ImageFormats.MAX,
                    ImageFormats.PAGE,
                    ImageFormats.FEED,
                    ImageFormats.THUMB,
            ]
        }

        @Override
        ImageFormat getDefaultImageFormat() {
            ImageFormats.FEED
        }
    }
}
