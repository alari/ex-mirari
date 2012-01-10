package mirari.model

import com.google.code.morphia.annotations.Reference
import com.google.code.morphia.annotations.Indexed
import ru.mirari.infra.mongo.MorphiaDomain
import com.google.code.morphia.annotations.Indexes
import com.google.code.morphia.annotations.Index

/**
 * @author alari
 * @since 12/22/11 6:22 PM
 */
@Indexes([
@Index(value = "site,displayName", unique = true, dropDups = true)
])
class Tag extends MorphiaDomain{
    def s = """
    могут содержать любое кол-во юнитов, и наоборот. многие-ко-многим
    могут содержать в себе другие теги
    принадлежат сайту, имеют список потоков, в которых используются
    """

    @Indexed
    @Reference(lazy=true) Site site
    
    String displayName
}
