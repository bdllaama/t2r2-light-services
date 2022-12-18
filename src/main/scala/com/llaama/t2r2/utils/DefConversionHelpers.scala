package com.llaama.t2r2.utils

import com.llaama.t2r2.api.{JobDefinition => ApiJobDefinition, JobDefinitionProperty => ApiJobDefinitionProperty}
import com.llaama.t2r2.domain._
import com.llaama.t2r2.api.{ContainerLocation => ContainerAPI}
import com.llaama.t2r2.domain.{ContainerLocation => ContainerADT}

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
object DefConversionHelpers {

  def convertToApiJDParam(dProps: Seq[JobDefinitionProperty]): Seq[ApiJobDefinitionProperty] =
    dProps.map(p => ApiJobDefinitionProperty(key = p.key, value = p.value))

  def convertToApi(jDefAdded: JobDefinitionAdded): ApiJobDefinition = {
    ApiJobDefinition(jobDefinitionId = jDefAdded.jobDefinitionId,
      name = jDefAdded.name, shortName = jDefAdded.shortName,
      organization = jDefAdded.organization, buildVersion = jDefAdded.buildVersion,
      ownerEmail = jDefAdded.ownerEmail, date = jDefAdded.date, definitionProperties = Seq.empty)
  }

  def convertToApi(jDefState: JobDefinitionState): ApiJobDefinition = {
    ApiJobDefinition(jobDefinitionId = jDefState.jobDefinitionId,
      name = jDefState.name, shortName = jDefState.shortName,
      organization = jDefState.organization, buildVersion = jDefState.buildVersion,
      ownerEmail = jDefState.ownerEmail, date = jDefState.date,
      `sealed` = jDefState.`sealed`,
      definitionProperties = convertToApiJDParam(jDefState.definitionProperties))
  }

  def convertContainerAPI2Domain(c: ContainerAPI) : ContainerADT = {
    val contId = c.server + "@" + c.repository + "@" + c.imageId
    ContainerADT(contId, c.server, c.repository, c.tag, c.imageId, c.user, c.comments,Some(General.nowTimeStamp))
  }

  def convertContainerADT2Api(c: ContainerADT): ContainerAPI =
    ContainerAPI(c.server, c.repository, c.tag, c.imageId, c.comments, c.user)
}
