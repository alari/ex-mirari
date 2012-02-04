import mirari.util.validators.NameValidators
import mirari.model.page.PageType

class UrlMappings {
    static mappings = {
        final Map nameCheck = NameValidators.CONSTRAINT_MATCHES
        final Map mongoIdCheck = [matches: '^[a-z0-9]{24,24}$']
        final Map pageNumCheck = [matches: '^-[0-9]+-$']
        final List<String> pageTypes = PageType.values()*.name

        /*      Feeds       */
        "/$pageNum?" {
            constraints {
                pageNum pageNumCheck
            }
            controller = "siteFeed"
            action = "root"
        }
        "/t/$id/$page?" {
            constraints {
                id mongoIdCheck
                page matches: '^[0-9]+$'
            }
            controller = "siteFeed"
            action = "tag"
        }
        "/l/$type?" {
            constraints {
                type inList: pageTypes
            }
            controller = "siteFeed"
            action = "type"
        }
        
        /*      Site Configuration, Settings, Preferences       */
        "/s/$action" {
            constraints {
            }
            controller = "sitePreferences"
        }

        /*      Page object and actions     */
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
                type inList: pageTypes
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
        

        "/x/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }


        "/robots.txt" {
            controller = "root"
            action = "robots"
        }

        "/"(controller: "siteFeed", action: "root")
        "500"(view: '/error')
        "404"(view: "/404")
    }
}
