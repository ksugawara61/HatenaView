package net.k2o_info.hatenaview.model.`object`

import org.simpleframework.xml.*

@Path(value = "rdf")
@Root(name = "RDF", strict = false)
class HatenaRssObject {

    @set:ElementList(entry = "item", inline = true)
    @get:ElementList(entry = "item", inline = true)
    var itemList: List<HatenaArticleObject>? = null
}
