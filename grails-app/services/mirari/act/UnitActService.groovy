package mirari.act

import mirari.site.AddFileCommand

import mirari.util.ServiceResponse
import mirari.model.Site
import mirari.model.Unit

import org.springframework.web.multipart.MultipartFile

//import mirari.ko.UnitBuilder


import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.file.FileStorage
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import mirari.repo.UnitRepo
import mirari.model.unit.ext.YouTubeUnit
import org.apache.http.client.utils.URLEncodedUtils

class UnitActService {

    static transactional = false

    @Autowired UnitRepo unitRepo
    def imageStorageService
    @Autowired FileStorage fileStorage

    def unitProducerService

    LinkGenerator grailsLinkGenerator

    ServiceResponse addFile(AddFileCommand command, MultipartFile file, Site site) {
        ServiceResponse resp = new ServiceResponse()
        if (command.hasErrors()) {
            resp.error(command.errors.toString())
            return resp
        }
        String fileExt = file.originalFilename.lastIndexOf(".") >= 0 ? file.originalFilename.substring(file.originalFilename.lastIndexOf(".")) : "tmp"
        File tmp = File.createTempFile("uploadUnit", "." + fileExt)
        file.transferTo(tmp)

        return unitProducerService.produce(tmp, site)
    }

    ServiceResponse setDraft(Unit unit, boolean draft) {
        unit.draft = draft
        unitRepo.save(unit)
        new ServiceResponse().redirect(grailsLinkGenerator.link(for: unit))
    }
    
    ServiceResponse getByUrl(String uri, Site site) {
        ServiceResponse resp = new ServiceResponse()
        try {
            URL url = new URL(uri)
            Unit unit = getUnitByURL(url, site)
            if(!unit) {
                resp.error("Wrong (unsupported) URL")
            } else {
                resp.success("OK!")
                resp.model(unit.viewModel)
            }
        } catch(MalformedURLException e) {
            resp.error(e.message)
        }
        resp
    }

    private Unit getUnitByURL(URL url, Site site) {
        Unit u = null;

        if (url.host == "youtu.be") {
            // http://youtu.be/zi3AqicZgEk
            u = new YouTubeUnit()
            u.youtubeId = url.path.substring(1)
        } else if(url.host == "www.youtube.com" && url.path == "watch") {
            // http://www.youtube.com/watch?v=zi3AqicZgEk&feature=g-logo&context=G2e33cabFOAAAAAAABAA
            u = new YouTubeUnit()
            u.youtubeId = URLEncodedUtils.parse(url.toURI(), "UTF-8").find {it.name == "v"}.value
        }
        if(!u) return;
        u.draft = true
        u.owner = site
        unitRepo.save(u)
        u
    }
}
