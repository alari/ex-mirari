@Typed package mirari.model.unit.content.internal

import mirari.model.unit.content.ContentHolder
import mirari.model.unit.UnitContent
import mirari.repo.UnitContentRepo
import mirari.vm.UnitVM
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.TextProcessUtil
import ru.mirari.infra.file.FileInfo
/**
 * @author alari
 * @since 1/6/12 5:41 PM
 */
class TextContentStrategy extends InternalContentStrategy {
    @Autowired private UnitContentRepo unitContentRepo

    @Override
    void attachContentToViewModel(ContentHolder unit, UnitVM unitViewModel) {
        unitViewModel.params.text = unit.content?.text
        unitViewModel.params.html = TextProcessUtil.markdownToHtml(unitViewModel.params.text)
    }

    @Override
    void setViewModelContent(ContentHolder unit, UnitVM unitViewModel) {
        if (!unit.content) unit.content = new UnitContent()
        unit.content.text = unitViewModel.params.text
    }

    @Override
    void setContentFile(ContentHolder unit, FileInfo fileInfo) {
        if (!isContentFileSupported(fileInfo)) return;
        if (!unit.content) unit.content = new UnitContent()
        
        unit.title = fileInfo.title
        
        if(fileInfo.extension.equalsIgnoreCase("txt")) {
            unit.content.text = fileInfo.file.getText()
        } else if(fileInfo.extension in ["htm", "html"]) {
            unit.content.text = TextProcessUtil.htmlToMarkdown(fileInfo.file.getText())
        }
    }

    @Override
    boolean isEmpty(ContentHolder unit) {
        !unit?.content || !unit.content?.text
    }

    @Override
    boolean isContentFileSupported(FileInfo type) {
        type.extension in ["txt", "htm", "html"]
    }

    @Override
    void saveContent(ContentHolder unit) {
        if (unit.content) {
            unitContentRepo.save(unit.content)
        }
    }

    @Override
    void deleteContent(ContentHolder unit) {
        if (unit.content) {
            unitContentRepo.delete(unit.content)
            unit.content = null
        }
    }
}
