import org.specs._
import dispatch._
import net.liftweb.json.JsonParser._
import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonDSL._
import org.apache.http.util.EntityUtils

class BeerSpec extends SpecificationWithJUnit {

  implicit val formats = net.liftweb.json.DefaultFormats // Brings in default date formats etc.
  val app = "scala_test_jersey"
  setSequential()
  shareVariables()

  "Calling GET /beers" should {
    "when called with" in {

      "name 'Budweiser'" in {
        val getBeerRes = toResponse(:/("localhost", 2342) / app / "beers" / "Budweiser")
        "return status 404" >> { getBeerRes.status must_== 404 }
      }

      "name 'Corona' and sizeOunces 4" in {
        val getBeerRes = toResponse(:/("localhost", 2342) / app / "beers" / "Corona" <<? Map("sizeOunces" -> 4))
        "return status 400" >> { getBeerRes.status must_== 400 }
      }

      "beer name 'Guiness' and sizeOunces 16" in {
        val getBeerRes = toResponse(:/("localhost", 2342) / app / "beers" / "Guiness" <<? Map("sizeOunces" -> 16))
        println(getBeerRes.body)
        "return status 200" >> { getBeerRes.status must_== 200 }
        "return a Beer representation" in {
          val beer = parse(getBeerRes.body).extract[JsonBeer]
          "with" in {
            "name 'Guiness'" >> {beer.name mustEqual "Guiness"}
            "sizeOunces 16" >> {beer.sizeOunces must_== 16}
          }
        }
      }
    }
  }

  def toResponse(req: Request): StatusCodeBody = {
    val http = new Http
    http x (req as_str) {
      case (status, _, ent, _) => StatusCodeBody(status, EntityUtils.toString(ent.get))
    }
  }

}

case class JsonBeer(name: String, sizeOunces: Int)
case class StatusCodeBody (status: Int, body: String)


/*
 "Calling GET /beer" should {
 "when called with" in {
 "a sizeOunces argument of 4" in {
 "return status 400" >> { }
 }

 "a beer name 'Budweiser'" in {
 "return status 404" >> { }
 }

 "beer name 'Guiness' and sizeOunces 16" in {
 "return status 200" >> { }
 "return a Beer representation" in {
 "with" in {
 "name 'Guiness'" >> {}
 "sizeOunces 16" >> {}
 }
 }
 }
 }
 }
 */