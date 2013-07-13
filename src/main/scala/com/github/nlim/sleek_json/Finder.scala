package com.github.nlim.sleek_json

object Finder {
  def compose[A, B, C](outer: Finder[A, B], inner: Finder[B, C]): Finder[A, C] = {
    new Finder[A, C] {
      def find(a: A): Option[C] = outer.find(a).flatMap(b => inner.find(b))
    }
  }

  implicit def intToIndexFinder(i: Int): ValueAtIndex = ValueAtIndex(i)

  implicit def stringToKeyValueFinder(s: String): ValueAtKey = ValueAtKey(s)


  val OBJ = JObjectFinder
  val ARR = JArrayFinder
  val STRING = JStringFinder
  val INT = JIntegerFinder
  val DOUBLE = JDoubleFinder
}

sealed trait Finder[-A, +B] {
  def find(e: A): Option[B]
  def obtain(e: A) = find(e).getOrElse(null)
  def apply(e: A) = find(e)
  def unapply(e: A): Option[B] = find(e)
  def ~>[C](f: Finder[B, C]) = Finder.compose(this, f)
}


object JObjectFinder extends Finder[JElement, JObject] {
  def find(e: JElement) = e match { case jo @ JObject(_) => Some(jo) case _ => None }
}

object JArrayFinder extends Finder[JElement, JArray] {
  def find(e: JElement) = e match { case ja @ JArray(_) => Some(ja) case _ => None }
}

object JIntegerFinder extends Finder[JElement, Integer] {
  def find(e: JElement) = e match { case JInteger(i) => Some(i) case _ => None }
}

object JStringFinder extends Finder[JElement, String] {
  def find(e: JElement) = e match { case JString(s) => Some(s) case _ => None }
}

object JDoubleFinder extends Finder[JElement, java.lang.Double] {
  def find(e: JElement) = e match { case JDouble(d) => Some(d) case _ => None }
}

object JBooleanFinder extends Finder[JElement, Boolean] {
  def find(e: JElement) = e match { case JBoolean(b) => Some(b) case _ => None }
}

case class ValueAtIndex(i: Int) extends Finder[JArray, JElement] {
  def find(a: JArray) = a.value.lift(i)
}

case class ValueAtKey(k: String) extends Finder[JObject, JElement] {
  def find(a: JObject) = a.value.get(k)
}
