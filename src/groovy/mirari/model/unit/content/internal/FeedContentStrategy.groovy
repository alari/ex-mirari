package mirari.model.unit.content.internal

import mirari.model.unit.content.ContentHolder
import mirari.vm.UnitVM
import ru.mirari.infra.file.FileInfo
import org.springframework.beans.factory.annotation.Autowired
import mirari.repo.PageRepo

/**
 * @author alari
 * @since 2/22/12 4:35 PM
 */
class FeedContentStrategy extends InternalContentStrategy{
    @Autowired PageRepo pageRepo
    
    @Override
    void attachContentToViewModel(ContentHolder unit, UnitVM unitViewModel) {
        // TODO: get site from unit,
        // TODO: retrieve (yet const) unit count from unit
        // TODO: create announces, somehow add them into view model
    }

    @Override
    void setViewModelContent(ContentHolder unit, UnitVM unitViewModel) {
        // yet do nothing
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
