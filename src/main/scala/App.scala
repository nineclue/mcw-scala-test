import com.raquo.laminar.api.{*, given}
import com.raquo.laminar.builders.HtmlBuilders
import org.scalajs.dom
import scala.scalajs.js
import js.annotation._

@js.native
@JSImport("@material/ripple", "MDCRipple")
class MDCRipple(e: dom.raw.Element) extends js.Object

object App:
    def tag(t: String, cls: String) = 
        val e = dom.document.createElement(t)
        e.setAttribute("class", cls)
        e

    def main(as: Array[String]): Unit = 
        println("Hello, Web!")
        val b = tag("button", "mdc-button mdc-button--outlined")
        val spans = Seq(("span", "mdc-button__ripple"), ("span", "mdc-button__touch"), 
            ("span", "mdc-button__focus-ring"), ("span", "mdc-button__label")).map(tag.tupled)
        spans.last.textContent = "버튼"
        spans.foreach(b.appendChild)
        dom.document.body.appendChild(b)
        MDCRipple(b)