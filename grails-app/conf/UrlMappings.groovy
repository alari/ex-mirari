import mirari.validators.NameValidators

class UrlMappings {

    static mappings = {

        final Map nameCheck = NameValidators.CONSTRAINT_MATCHES
        //   List nodeTypes = NodeType.values().collect {it.toString()}
        //   Map nodeCheck = [matches:'^[-_a-zA-Z0-9]{2,32}$', validator: {!nodeTypes.contains(it)}]

        "/$spaceName/" {
            constraints {
                spaceName nameCheck
            }
            controller = "subject"
        }
        "/$spaceName/$unitName/$action?" {
            constraints {
                spaceName nameCheck
                unitName nameCheck
            }
            controller = "unit"
        }
        "/$spaceName/u/$action" {
            constraints {
                spaceName nameCheck
            }
            controller = "unit"
        }
/*    "/$name/add.$type" {
        constraints {
          name domainCheck
          type inList: nodeTypes
        }
      controller = "subjectNode"
      action = "addNode"
    }
    "/$name/$type" {
      constraints {
          name domainCheck
          type inList: nodeTypes
        }
      controller = "subject"
      action = "typeList"
    }
    "/$name/$node" {
      constraints {
        name domainCheck
        node nodeCheck
      }
      controller = "subject"
      action = "node"
    }
    "/$name/$node/$action" {
      constraints {
        name domainCheck
        node nodeCheck
      }
      controller = "subjectNode"
    }
    "/$name/adm.$action" {
      constraints {
        name domainCheck
      }
      controller = "subjectAdm"
    }


    "/own.talks/$id?" {
      constraints {
        id matches: '^[a-z0-9]{24,24}$'
      }
      controller = "talks"
      action = "talk"
    }

    "/own.talks/$action?" {
      controller = "talks"
    }        */

        "/x/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(view: "/index")
        "500"(view: '/error')
        "404"(view: "/404")
    }
}
