package mirari.model

import com.google.code.morphia.annotations.Index
import com.google.code.morphia.annotations.Indexed
import com.google.code.morphia.annotations.Indexes
import com.google.code.morphia.annotations.Reference

/**
 * @author alari
 * @since 12/22/11 6:18 PM
 */
@Indexes([
@Index(value = "site,displayName", unique = true, dropDups = true)
])
class Current {
    def s = """
    нужно сделать, чтобы потоки (current or stream) принадлежали сайтам,
    потоки пусть имеют набор допустимых типов креативов по базовым типам страниц,
    и пусть сами имеют тип -- блог, проза, стихи, статьи, музыка, подкасты
    автор выбирает при публикации, что это у него
    и оно попадает в нужный поток?
    """

    @Indexed
    @Reference(lazy = true) Site site

    String displayName
}
