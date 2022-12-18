package com.llaama.t2r2.utils

import com.google.protobuf.timestamp.Timestamp

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import scala.util.Try

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
object General {
  val localIsoDateFormatter = new ThreadLocal[SimpleDateFormat] {
    override def initialValue() = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  }

  def dateToIsoString(date: Date) = localIsoDateFormatter.get().format(date)

  //todo Either would be better
  def parseIsoDateString(date: String): Option[Date] = Try {
    localIsoDateFormatter.get().parse(date)
  }.toOption

  def nowTimeStamp: Timestamp = {
    val now = Instant.now
    Timestamp.of(now.getEpochSecond, now.getNano)
  }

  def asTimeStamp(time: Long): Timestamp = {
    val inst = Instant.ofEpochMilli(time)
    Timestamp.of(inst.getEpochSecond, inst.getNano)
  }

}
