package com.llaama.t2r2.utils

import scala.util.Random

/**
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version. 
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */
object NameGenerator {

  val animals = readFileFromResources("/name_gen/animals.txt")
  val aL = animals.size
  val colors = readFileFromResources("/name_gen/colors.txt")
  val cL = colors.size
  val adjectives = readFileFromResources("/name_gen/adjectives.txt")
  val adL = adjectives.size

  val vs = "aeiouy"
  val co = "bcdfghjklmnpqrstvwxz"

  def getName: String = {
    val animal = firstLetterUpperCase(animals(Random.nextInt(aL)))
    val color = firstLetterUpperCase(colors(Random.nextInt(cL)))
    val adjective = firstLetterUpperCase(adjectives(Random.nextInt(adL)))

    s"${adjective}_${color}_${animal}_${nickN}"
  }

  def readFileFromResources(name: String): List[String] = {
    import scala.io.Source
    Source.fromInputStream(getClass.getResourceAsStream(name))
      .getLines().map(_.trim).toList
  }

  def firstLetterUpperCase(s: String) = {
    require(s.nonEmpty)
    s.substring(0,1).toUpperCase + s.substring(1)
  }

  def rndVo: Char = vs.charAt(Random.nextInt(vs.length))
  def rndCs: Char = co.charAt(Random.nextInt(co.length))

  def nickN: String = firstLetterUpperCase(s"$rndCs$rndVo$rndCs$rndVo")

}

