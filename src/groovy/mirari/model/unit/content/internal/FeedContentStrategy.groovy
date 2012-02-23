package mirari.model.unit.content.internal

import mirari.model.unit.content.ContentHolder
import mirari.vm.UnitVM
import ru.mirari.infra.file.FileInfo
import mirari.model.unit.content.ContentData

/**
 * @author alari
 * @since 2/22/12 4:35 PM
 */
class FeedContentStrategy extends InternalContentStrategy{
    
    @Override
    void attachContentToViewModel(ContentHolder unit, UnitVM unitViewModel) {
        unitViewModel.params = [
                num: ContentData.FEED_NUM.getFrom(unit),
                style: ContentData.FEED_STYLE.getFrom(unit),
                source: ContentData.FEED_SOURCE.getFrom(unit),
                locked: ContentData.FEED_LOCKED.getFrom(unit),
                feedId: ContentData.FEED_ID.getFrom(unit),
        ]
    }

    @Override
    void setViewModelContent(ContentHolder unit, UnitVM unitViewModel) {
        ContentData.FEED_NUM.putTo(unit, unitViewModel.params.num ?: "10")
        ContentData.FEED_STYLE.putTo(unit, unitViewModel.params.style ?: "grid")

        if(!ContentData.FEED_LOCKED.getFrom(unit)) {
            ContentData.FEED_SOURCE.putTo(unit, unitViewModel.params.source ?: "all")
            ContentData.FEED_LOCKED.putTo(unit, unitViewModel.params.locked ?: "")
            ContentData.FEED_ID.putTo(unit, unitViewModel.params.feedId ?: "")
        }
    }

    @Override
    void setContentFile(ContentHolder unit, FileInfo fileInfo) {
    }

    @Override
    boolean isContentFileSupported(FileInfo info) {
        return false
    }

    @Override
    void saveContent(ContentHolder unit) {
    }

    @Override
    void deleteContent(ContentHolder unit) {
    }
}
