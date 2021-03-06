package com.github.nlim.sleek_json
import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import Json._

class FinderSpec extends FunSpec with ShouldMatchers {

  val rawJson = "{\"foo\": {\"x\": [1, 2]}, \"bar\": 2.0, \"baz\": [\"a\", \"b\"]}"

  val jElement = parseJson(rawJson)

  val Foo = OBJ ~> "foo"

  val X = Foo ~> OBJ ~> "x" ~> ARR

  val SecondXArrayValue = X ~> 1 ~> INT

  val BarValue = OBJ ~> "bar" ~> DOUBLE

  it("should find the nested Json Object") {
    (jElement >>: Foo) should be (Some(JObject(Map("x" -> JArray(Vector(JInteger(1),JInteger(2)))))))
  }

  it("should find the second element of the x array") {
    (jElement >>: X ~> 1 ~> INT) should be (Some(2))
  }

  it("should find the bar value") {
    (jElement >>: BarValue) should be (Some(2.0))
  }
}