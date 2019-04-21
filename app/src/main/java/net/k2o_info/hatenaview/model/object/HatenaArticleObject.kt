package net.k2o_info.hatenaview.model.`object`

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

/**
 * はてなブックマークの記事オブジェクト
 *
 * @author katsuya
 * @since 1.0.0
 */
@Root(strict = false)
class HatenaArticleObject {

    @set:Element(name = "title")
    @get:Element(name = "title")
    var title: String? = null

    @set:Element(name = "link")
    @get:Element(name = "link")
    var link: String? = null

    @set:Element(name = "description")
    @get:Element(name = "description")
    var description: String? = null

    @Path(value = "dc")
    @set:Element(name = "date")
    @get:Element(name = "date")
    var date: String? = null

    @Path(value = "dc")
    @set:ElementList(entry = "subject", inline = true)
    @get:ElementList(entry = "subject", inline = true)
    var subjectList: List<String>? = null

    @Path(value = "hatena")
    @set:Element(name = "bookmarkcount")
    @get:Element(name = "bookmarkcount")
    var bookmarkCount: Int? = null

    @Path(value = "hatena")
    @set:Element(name = "bookmarkSiteEntriesListUrl")
    @get:Element(name = "bookmarkSiteEntriesListUrl")
    var bookmarkSiteEntriesListUrl: String? = null

    @Path(value = "hatena")
    @set:Element(name = "imageurl", required = false)
    @get:Element(name = "imageurl", required = false)
    var imageUrl: String? = null

    @Path(value = "hatena")
    @set:Element(name = "bookmarkCommentListPageUrl")
    @get:Element(name = "bookmarkCommentListPageUrl")
    var bookmarkCommentListPageUrl: String? = null
}
