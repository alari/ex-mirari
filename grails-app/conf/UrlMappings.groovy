import mirari.util.validators.NameValidators
import mirari.model.PageType

class UrlMappings {
    static mappings = {
        final Map nameCheck = NameValidators.CONSTRAINT_MATCHES
        final Map mongoIdCheck = [matches: '^[a-z0-9]{24,24}$']
        final Map pageNumCheck = [matches: '^-[0-9]+-$']

        "/$pageNum?" {
            constraints {
                pageNum pageNumCheck
            }
            controller = "site"
        }
        "/s/$action" {
            constraints {
            }
            controller = "site"
        }
        "/$pageName"{
            constraints {
                pageName nameCheck
            }
            controller = "sitePage"
            action = "index"
        }
        "/$pageName/$action?" {
            constraints {
                pageName nameCheck
            }
            controller = "sitePage"
        }
        "/p/add-$type" {
            constraints {
                type inList: PageType.values()*.name
            }
            controller = "sitePageStatic"
            action = "add"
        }
        "/p/$action?" {
            constraints {
            }
            controller = "sitePageStatic"
        }
        "/u/$id" {
            constraints {
                id mongoIdCheck
            }
            controller = "siteUnit"
        }
        "/t/$id/$page?" {
            constraints {
                id mongoIdCheck
                page matches: '^[0-9]+$'
            }
            controller = "siteTag"
            action = "feed"
        }
        "/l/$type?" {
            constraints {
                type inList: PageType.values()*.name
            }
            controller = "sitePagesList"
        }

        "/x/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }


        "/robots.txt" {
            controller = "root"
            action = "robots"
        }

        "/"(controller: "root")
        "500"(view: '/error')
        "404"(view: "/404")
    }
}
