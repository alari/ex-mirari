@Typed package mirari.morphia.unit.single

import mirari.morphia.unit.SingleUnit
import com.google.code.morphia.annotations.*
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.Domain
import mirari.ko.UnitViewModel
import mirari.infra.CleanHtmlService
import com.google.code.morphia.Key

/**
 * @author alari
 * @since 11/22/11 9:20 PM
 */
class TextUnit extends SingleUnit{
    @Reference(lazy=true) Content content

    void setViewModel(UnitViewModel viewModel) {
        super.setViewModel(viewModel)
        if(!content) content = new Content()
        content.text = viewModel.text
    }

    UnitViewModel getViewModel() {
        UnitViewModel uvm = super.viewModel
        uvm.text = content.text
        uvm
    }

    @Entity("unit.text")
    static public class Content extends Domain{
        String text

        static public class Dao extends BaseDao<TextUnit.Content>{
            @Autowired CleanHtmlService cleanHtmlService
            
            @Autowired Dao(MorphiaDriver morphiaDriver) {
                super(morphiaDriver)
            }
            
            Key<TextUnit.Content> save(TextUnit.Content content) {
                content.text = cleanHtmlService.clean(content.text)
                super.save(content)
            }
        }
    }
}
