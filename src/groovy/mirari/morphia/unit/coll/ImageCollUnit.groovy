@Typed package mirari.morphia.unit.coll

import mirari.morphia.unit.CollUnit
import mirari.morphia.unit.single.ImageUnit
import mirari.morphia.Unit

/**
 * @author alari
 * @since 11/16/11 10:54 PM
 */
class ImageCollUnit extends CollUnit{
    ImageUnit getNext(ImageUnit u) {
        int i
        final ImageUnit next
        for(i = 0; i<inners.size(); i++) {
            if(u.id == inners[i].id) {
                next = (ImageUnit)(i == inners.size()-1 ? inners.first() : inners[i+1])
            }
        }
        next ?: u
    }
}
