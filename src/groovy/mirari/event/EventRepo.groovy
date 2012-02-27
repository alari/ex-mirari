package mirari.event

import com.google.code.morphia.query.MorphiaIterator
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver

/**
 * @author alari
 * @since 2/27/12 9:00 PM
 */
class EventRepo extends BaseDao<EventDomain> {
    @Autowired EventRepo(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    void put(Event event) {
        EventDomain domain = new EventDomain()
        domain.event = event
        save(domain)
    }

    MorphiaIterator<EventDomain, EventDomain> getCursorIterator() {
        (MorphiaIterator<EventDomain, EventDomain>) createQuery().disableCursorTimeout().fetch()
    }

    List<EventDomain> getToProcess() {
        List<EventDomain> eventDomains = []
        EventDomain domain
        while (domain = ds.findAndModify(
                createQuery().filter("inProcess", false),
                createUpdateOperations().set("inProcess", true)
        )) {
            eventDomains.add(domain)
        }

        eventDomains
    }
}
