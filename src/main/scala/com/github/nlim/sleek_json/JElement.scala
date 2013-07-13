package com.github.nlim.sleek_json

import com.fasterxml.jackson.databind.{ObjectMapper, JsonNode}
import scala.collection.JavaConversions.asScalaIterator
import java.io.StringWriter
import com.fasterxml.jackson.core.{JsonFactory, JsonGenerator}




sealed trait JElement {
 def toJson: String = Json.toJson(this)
}

case object JNull extends JElement

case class JArray(value: Vector[JElement]) extends JElement {
  def get(i: Int) = value(i)
}

case class JObject(value: Map[String, JElement]) extends JElement {
  def get(k: String) = value(k)
}

case class JInteger(value: Integer) extends JElement

case class JString(value: String) extends JElement

case class JDouble(value: java.lang.Double) extends JElement

case class JFloat(value: java.lang.Float) extends JElement

case class JBoolean(value: Boolean) extends JElement



