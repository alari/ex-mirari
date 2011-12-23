import mirari.validators.NameValidators

class UrlMappings {
    static mappings = {
        final Map nameCheck = NameValidators.CONSTRAINT_MATCHES
        final Map mongoIdCheck = [matches: '^[a-z0-9]{24,24}$']
        final Map pageNumCheck = [matches: '^-[0-9]+-$']

        "/$siteName/$pageNum?" {
            constraints {
                siteName nameCheck
                pageNum pageNumCheck
            }
            controller = "site"
        }
        "/$siteName/s/$action" {
            constraints {
                siteName nameCheck
            }
            controller = "site"
        }
        "/$siteName/$pageName"{
            constraints {
                siteName nameCheck
                pageName nameCheck
            }
            controller = "sitePage"
            action = "index"
        }
        "/$siteName/$pageName/$action?" {
            constraints {
                siteName nameCheck
                pageName nameCheck
            }
            controller = "sitePage"
        }
        "/$siteName/p/$action?" {
            constraints {
                siteName nameCheck
            }
            controller = "sitePageStatic"
        }
        "/$siteName/u/$id" {
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

        "/"(controller: "root")
        "500"(view: '/error')
        "404"(view: "/404")
    }
}
