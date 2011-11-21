@Typed package mirari.morphia.unit.coll

import mirari.morphia.unit.CollUnit
import mirari.morphia.unit.single.ImageUnit

/**
 * @author alari
 * @since 11/16/11 10:54 PM
 */
class ImageCollUnit extends CollUnit{
    ImageUnit getNext(ImageUnit u) {
        int i
        final ImageUnit next
        for(i = 0; i<units.size(); i++) {
            if(u.id == units[i].id) {
                next = (ImageUnit)(i == units.size()-1 ? units.first() : units[i+1])
            }
        }
        next ?: u
    }
}
