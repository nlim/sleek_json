package com.github.nlim.sleek_json

import com.fasterxml.jackson.databind.{ObjectMapper, JsonNode}
import java.io.StringWriter
import com.fasterxml.jackson.core.{JsonFactory, JsonGenerator}
import scala.collection.JavaConversions.asScalaIterator

object Json {

  def parse(s: String): JElement = construct(parser(s))

  private val parser: String => JsonNode = (new ObjectMapper()).readTree

  private def construct(node: JsonNode): JElement = {
    if (node.isArray) {
      JArray(node.elements.foldLeft(Vector[JElement]())((vec, n) => vec :+ construct(n)))
    } else if (node.isObject) {
      JObject(node.fieldNames.foldLeft(Map[String, JElement]())((m, key) => m + (key -> construct(node.get(key)))))
    } else if (node.isInt) {
      JInteger(node.intValue)
    } else if (node.isDouble) {
      JDouble(node.doubleValue)
    } else if (node.isTextual) {
      JString(node.textValue)
    } else if (node.isBoolean) {
      JBoolean(node.booleanValue)
    } else {
      JNull
    }
  }

  def toJson(inputValue: JElement): String = {
    val writer: StringWriter = new StringWriter(1024)
    val jg: JsonGenerator = (new JsonFactory).createJsonGenerator(writer)

    def writeJson(value: JElement): Unit = {
      value match {
        case JNull => jg.writeNull()
        case JString(s) => jg.writeString(s)
        case JDouble(d) => jg.writeNumber(d)
        case JInteger(i) => jg.writeNumber(i)
        case JFloat(f) => jg.writeNumber(f)
        case JBoolean(b) => jg.writeBoolean(b)
        case JArray(a) =>
          jg.writeStartArray()
          a.foreach(v => writeJson(v))
          jg.writeEndArray()
        case JObject(m) =>
          jg.writeStartObject()
          m.foreach {
            case (k, v) =>
              jg.writeFieldName(k)
              writeJson(v)
          }
          jg.writeEndObject()
      }
    }

    writeJson(inputValue)
    jg.close()
    val output = writer.toString()
    writer.close()
    return output
  }
}

