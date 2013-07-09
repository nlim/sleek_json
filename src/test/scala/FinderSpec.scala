package com.github.nlim.sleek_json
import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import java.lang.Double


class FinderSpec extends FunSpec with ShouldMatchers {

  val rawJson = "{\"foo\": {\"x\": [1, 2]}, \"bar\": 2.0, \"baz\": [\"a\", \"b\"]}"

  val jElement = JElement.construct(rawJson)

  val FooValue: Finder[JElement, JObject] = JObjectFinder ~> ValueAtKey("foo") ~> JObjectFinder

  val XValue: Finder[JElement, JArray] = FooValue ~> ValueAtKey("x") ~> JArrayFinder

  val SecondXArrayValue: Finder[JElement, Integer] = XValue ~> ValueAtIndex(1) ~> JIntegerFinder

  val BarValue: Finder[JElement, Double] = JObjectFinder ~> ValueAtKey("bar") ~> JDoubleFinder

  it("should find the nested Json Object") {
    FooValue.find(jElement) should be (Some(JObject(Map("x" -> JArray(Vector(JInteger(1),JInteger(2)))))))
  }

  it("should find the second element of the x array") {
    SecondXArrayValue.find(jElement) should be (Some(2))
  }

  it("should find the bar value") {
    BarValue.find(jElement) should be (Some(2.0))
  }
}