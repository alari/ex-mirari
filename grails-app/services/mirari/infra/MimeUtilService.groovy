package mirari.infra

import eu.medsea.mimeutil.MimeType
import eu.medsea.mimeutil.MimeUtil

class MimeUtilService {

    static transactional = false

    MimeType getMimeType(File file) {
        MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
        MimeUtil.getMostSpecificMimeType(MimeUtil.getMimeTypes(file))
    }
}
