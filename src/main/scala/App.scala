import com.raquo.laminar.api.{*, given}
import com.raquo.laminar.builders.HtmlBuilders
import org.scalajs.dom
import scala.scalajs.js
import js.annotation._

object TagUtil:
    def tag(t: String, cls: String*) = 
        val e = dom.document.createElement(t)
        e.setAttribute("class", cls.mkString(" "))
        e

import TagUtil.tag

@js.native
@JSImport("@material/ripple", "MDCRipple")
class MDCRipple(e: dom.raw.Element) extends js.Object

@js.native
@JSImport("@material/top-app-bar", "MDCTopAppBar")
class MDCTopAppBar(val e: dom.raw.Element) extends js.Object

object MDCTopAppBar:
    private val pre = "mdc-top-app-bar"
    def apply(title: String) = 
        val h = tag("header", pre)
        val row = tag("div", pre ++ "__row")
        val s1 = tag("selection", pre ++ "__section", pre ++ "__section--align-start")
        val t = tag("span", pre ++ "__title")
        t.textContent = title
        s1.appendChild(t)

        val s2 = tag("selection", pre ++ "__section", pre ++ "__section--align-end")
        s2.setAttribute("role", "toolbar")

        row.appendChild(s1)
        row.appendChild(s2)
        h.appendChild(row)
        // new MDCTopAppBar(h)
        h

@js.native
@JSImport("@material/list", "MDCList")
class MDCList(e: dom.raw.Element) extends js.Object

@js.native
@JSImport("@material/drawer", "MDCDrawer")
class MDCDrawer(e: dom.raw.Element) extends js.Object:
    def attachTo(e: dom.raw.Element): dom.raw.Element  = js.native
    var open: Boolean = js.native

object MDCDrawer:
    val cls = "mdc-drawer"
    private def md(rest: String = "") = cls ++ rest
    private def l(rest: String = "") = "mdc-deprecated-list" ++ rest
    private def li(rest: String = "") = "mdc-deprecated-list-item" ++ rest

    private def item(icon: String, content: String, activated: Boolean = false) = 
        val a = tag("a")
        if (activated) 
            a.setAttribute("class", s"${li()} ${li("--activated")}")
            a.setAttribute("href", "#")
            a.setAttribute("aria-current", "page")
        else
            a.setAttribute("class", li())
        val rspan = tag("span", li("__ripple"))        
        val i = tag("i", "material-icons", li("__graphic"))
        i.setAttribute("aria-hidden", "true")
        i.textContent = icon
        val tspan = tag("span", li("__text"))
        tspan.textContent = content
        a.appendChild(rspan)
        a.appendChild(i)
        a.appendChild(tspan)
        a

    // modal인 경우 scrim div 다음에 content div가 들어오며
    // dismissible인 경우 app-content div안에 content가 들어간다
    def apply() = 
        val side = tag("aside", cls, md("--dismissible"))
        // val side = tag("aside", cls, md("--dismissible"))
        val scontent = tag("div", md("__content"))
        val list = tag("nav", l(""))
        Seq(("inbox", "받은 편지함", true), ("send", "Outgoing", false), 
            ("drafts", "Drafts", false)).map(item.tupled).foreach(list.appendChild)
        scontent.appendChild(list)
        side.appendChild(scontent)
        (side, tag("div", md("-app-content")))        

    def attachTo(e: dom.raw.Element) = new MDCDrawer(e)

@js.native
@JSImport("@material/textfield", "MDCTextField")
class MDCTextField(val e: dom.raw.Element) extends js.Object

object MDCTextField:
    private val pre = "mdc-text-field"
    private def tf(rest: String = "") = pre ++ rest
    def apply(id: String, hint: String, hintId: String) = 
        val l = tag("label", tf(), tf("--filled"))
        val sripple = tag("span", tf("__ripple"))
        val flabel = tag("span", "mdc-floating-label")
        flabel.setAttribute("id", hintId)
        flabel.textContent = hint
        val input = tag("input", tf("__input"))
        input.setAttribute("type", "text")
        input.setAttribute("aria-labelledby", hintId)
        val slripple = tag("span", "mdc-line-ripple")
        Seq(sripple, flabel, input, slripple).foreach(l.appendChild)
        l

object MDCTextArea:
    private val pre = "mdc-text-field"
    private def tf(rest: String = "") = pre ++ rest
    def apply(id: String, rows: Int = 8, cols: Int = 40) = 
        val l = tag("label", tf(), tf("--filled"), tf("--textarea"), tf("--no-label"))
        val sripple = tag("span", tf("__ripple"))
        val sresizer = tag("span", tf("__resizer"))
        val textarea = tag("textarea", tf("__input"))
        textarea.setAttribute("id", id)
        textarea.setAttribute("rows", rows.toString)
        textarea.setAttribute("cols", cols.toString)
        textarea.setAttribute("aria-label", "Label")
        sresizer.appendChild(textarea)
        val slripple = tag("span", "mdc-line-ripple")
        Seq(sripple, sresizer, slripple).foreach(l.appendChild)
        // mount하고 난 다음 함수 호출하도록 해보자!!!
        // new MDCTextField(l)
        l

object App:
    def main(as: Array[String]): Unit = 
        println("Hello, Web!")

        /*
        val h = MDCTopAppBar.apply("앱 바 시험")
        dom.document.body.appendChild(h)
        new MDCTopAppBar(h)
        */

        val (side, content) = MDCDrawer()
        val b = tag("button", "mdc-button mdc-button--outlined")
        val spans = Seq(("span", "mdc-button__ripple"), ("span", "mdc-button__touch"), 
            ("span", "mdc-button__focus-ring"), ("span", "mdc-button__label")).map(t => tag(t._1, t._2))
        spans.last.textContent = "버튼"
        spans.foreach(b.appendChild)
        MDCRipple(b)

        val h2 = tag("h2")
        h2.textContent = "멋진 내용"
        content.appendChild(h2)
        content.appendChild(b)
        
        val tf = MDCTextField("tf", "이름", "name")
        content.appendChild(tf)
        // new MDCTextField(tf)

        val ta = MDCTextArea("ta")
        content.appendChild(ta)
        new MDCTextField(ta)

        dom.document.body.appendChild(side)
        dom.document.body.appendChild(content)
        // dom.document.body.appendChild(h2)
        // dom.document.body.appendChild(b)

        val md = MDCDrawer.attachTo(side)
        md.open = true