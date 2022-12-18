package com.llaama.t2r2.utils

import com.llaama.t2r2.api.{Job, JobForView, JobMetaData => ApiMetaData, JobParameter => ApiJobParameter, JobProperty => ApiJobProperty, JobStatus => ApiJobStatus, RelatedDigest => ApiRelatedDigest}
import com.llaama.t2r2.domain._


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
object JobConversionHelpers {

  def propConvert(p: JobProperty): ApiJobProperty = ApiJobProperty(p.key, p.value)

  def paramConvert(p: JobParameter): ApiJobParameter = ApiJobParameter(p.key, p.value)

  def statusConvert(s: JobStatus): ApiJobStatus = ApiJobStatus.fromValue(s.value)

  def convertToApiJProp(dProps: Seq[JobProperty]): Seq[ApiJobProperty] =
    dProps.map(propConvert)

  def convertToApiJParam(dProps: Seq[JobParameter]): Seq[ApiJobParameter] =
    dProps.map(paramConvert)

  def convertToApi(jStatus: JobStatus): ApiJobStatus = ApiJobStatus.fromName(jStatus.name).getOrElse(ApiJobStatus.UNKNOWN)

  def convertToApi(relatedDigest: RelatedDigest): ApiRelatedDigest =
    ApiRelatedDigest(relatedDigest.key, relatedDigest.digest)

  def convertToApi(jA: JobAdded): JobForView = {
    JobForView(jobId = jA.jobId, jobDefinitionId = jA.jobDefinitionId, name = jA.name,
      description = jA.description, organization = jA.organization, ownerEmail = jA.ownerEmail,
      clonedFrom = jA.clonedFrom, date = jA.date, jobStatus = ApiJobStatus.NEW_JOB, duration = -1)
  }

  def convertToApi(md: JobMetaData): ApiMetaData = ApiMetaData(md.key, md.metaData)

  def convertToApi(js: JobState): Job = {
    val dur: Long = if (js.runEnd.nonEmpty && js.runStart.nonEmpty) {
      js.runEnd.get.seconds - js.runStart.get.seconds
    } else -10

    Job(jobId = js.jobId, jobDefinitionId = js.jobDefinitionId, name = js.name,
      description = js.description, organization = js.organization, ownerEmail = js.ownerEmail,
      timeout = js.timeout, clonedFrom = js.clonedFrom, date = js.date, runStart = js.runStart,
      runEnd = js.runEnd, `sealed` = js.`sealed`, jobParameters = convertToApiJParam(js.jobParameters),
      jobProperties = convertToApiJProp(js.jobProperties), jobStatus = convertToApi(js.jobStatus),
      jobDigest = js.jobDigest, parentJobs = js.parentJobs, relatedDigests = js.relatedDigests.map(convertToApi),
      jobMetaData = js.jobMetaData.map(convertToApi), duration = dur, deleted = js.deleted
    )
  }
}
