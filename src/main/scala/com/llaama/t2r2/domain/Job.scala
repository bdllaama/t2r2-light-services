package com.llaama.t2r2.domain

import com.llaama.t2r2.api._
import com.llaama.t2r2.api.{Job => ApiJob}

import com.llaama.t2r2.domain.{JobParameter => DomainJobParameter,
  JobProperty => DomainJobProperty,
  JobStatus => DomainJobStatus,
  RelatedDigest => DomainRelatedDigest,
  JobMetaData => DomainJobMetaData
}

import com.llaama.t2r2.utils.{General, JobConversionHelpers}

import kalix.scalasdk.eventsourcedentity.{EventSourcedEntity, EventSourcedEntityContext}

import scala.annotation.nowarn

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** An event sourced entity. */
class Job(context: EventSourcedEntityContext) extends AbstractJob {

  val error_msg_sealed = "Job is sealed and cannot be changed anymore. "

  def error_msg_jobid(jobId: String = "?") = s"No corresponding Job id ${jobId} "

  @nowarn("msg=unused")
  private val entityId = context.entityId

  override def emptyState: JobState = JobState.defaultInstance

  override def newJob(currentState: JobState, addJob: AddJobWrap): EventSourcedEntity.Effect[JobModifInfo] = {
    if (currentState.`sealed`) effects.error(error_msg_sealed)
    else {
      val jobA = JobAdded(addJob.jobId, addJob.jobDefinitionId, addJob.name, addJob.description,
        addJob.organization, addJob.ownerEmail, addJob.timeout, addJob.clonedFrom, Some(General.nowTimeStamp))

      effects.emitEvent(jobA).thenReply(_ => JobModifInfo(addJob.jobId, addJob.name, "Job created. "))
    }
  }

  override def addProperty(currentState: JobState,
                           addJobProperty: AddPropertyWrap): EventSourcedEntity.Effect[JobModifInfo] = {
    if (currentState.`sealed`) effects.error(error_msg_sealed)
    else if (currentState.jobId == addJobProperty.jobId) {
      val event = JobPropertyAdded(Some(DomainJobProperty(addJobProperty.key,
        addJobProperty.property)), Some(General.nowTimeStamp))
      effects.emitEvent(event).thenReply(_ => JobModifInfo(currentState.jobId, currentState.name,
        s"Property ${addJobProperty.property} added."))
    } else {
      effects.error(error_msg_jobid(addJobProperty.jobId))
    }
  }

  override def removeProperty(currentState: JobState,
                              removeJobProperty: RemovePropertyWrap): EventSourcedEntity.Effect[JobModifInfo] = {
    if (currentState.`sealed`) effects.error(error_msg_sealed)
    else if (currentState.jobId == removeJobProperty.jobId) {
      val event = JobPropertyRemoved(removeJobProperty.key, Some(General.nowTimeStamp))
      effects.emitEvent(event).thenReply(_ => JobModifInfo(currentState.jobId,
        currentState.name, s"Property removed"))
    } else effects.error(error_msg_jobid(removeJobProperty.jobId))
  }

  override def addParameter(currentState: JobState,
                            addJobParameter: AddParameterWrap): EventSourcedEntity.Effect[JobModifInfo] = {
    if (currentState.`sealed`) effects.error(error_msg_sealed)
    else if (currentState.jobId == addJobParameter.jobId) {
      val event = JobParameterAdded(Some(DomainJobParameter(addJobParameter.key,
        addJobParameter.parameter)), Some(General.nowTimeStamp))
      effects.emitEvent(event).thenReply(_ => JobModifInfo(currentState.jobId,
        currentState.name, s"Parameter added. "))
    } else {
      effects.error(error_msg_jobid(addJobParameter.jobId))
    }
  }

  override def removeParameter(currentState: JobState,
                               removeJobParameter: RemoveParameterWrap): EventSourcedEntity.Effect[JobModifInfo] = {
    if (currentState.`sealed`) effects.error(error_msg_sealed)
    else if (currentState.jobId == removeJobParameter.jobId) {
      val event = JobParameterRemoved(removeJobParameter.key, Some(General.nowTimeStamp))
      effects.emitEvent(event).thenReply(_ => JobModifInfo(currentState.jobId,
        currentState.name, s"Parameter removed"))
    } else effects.error(error_msg_jobid(removeJobParameter.jobId))
  }

  override def addMetaData(currentState: JobState,
                           addMetaDataWrap: AddMetaDataWrap): EventSourcedEntity.Effect[JobModifInfo] = {
    if (currentState.jobId == addMetaDataWrap.jobId) {
      val event = JobMetaDataAdded(Some(DomainJobMetaData(addMetaDataWrap.key,
        addMetaDataWrap.metaData)), Some(General.nowTimeStamp))
      effects.emitEvent(event)
        .thenReply(_ => JobModifInfo(currentState.jobId, currentState.name, s"metadata added. "))
    } else {
      effects.error(error_msg_jobid(addMetaDataWrap.jobId))
    }
  }

  override def removeMetaData(currentState: JobState,
                              removeMetaDataWrap: RemoveMetaDataWrap): EventSourcedEntity.Effect[JobModifInfo] = {
    if (currentState.jobId == removeMetaDataWrap.jobId) {
      val event = JobMetaDataRemoved(removeMetaDataWrap.key, Some(General.nowTimeStamp))
      effects.emitEvent(event).thenReply(_ => JobModifInfo(currentState.jobId,
        currentState.name, s"Metadata removed"))
    } else effects.error(error_msg_jobid(removeMetaDataWrap.jobId))
  }

  override def addDigest(currentState: JobState,
                         addJobDigest: SetDigestWrap): EventSourcedEntity.Effect[JobModifInfo] = {
    if (currentState.`sealed`) effects.error(error_msg_sealed)
    else if (currentState.jobId == addJobDigest.jobId) {
      val event = JobDigestSet(addJobDigest.jobDigest, Some(General.nowTimeStamp))
      effects.emitEvent(event)
        .thenReply(_ => JobModifInfo(currentState.jobId, currentState.name, s"digest added. "))
    } else {
      effects.error(error_msg_jobid(addJobDigest.jobId))
    }
  }

  override def removeDigest(currentState: JobState,
                            removeJobDigest: RemoveDigestWrap): EventSourcedEntity.Effect[JobModifInfo] = {
    if (currentState.`sealed`) effects.error(error_msg_sealed)
    else if (currentState.jobId == removeJobDigest.jobId) {
      val event = JobDigestSet("-", Some(General.nowTimeStamp))
      effects.emitEvent(event).thenReply(_ => JobModifInfo(currentState.jobId, currentState.name, " digest removed. "))
    } else {
      effects.error(error_msg_jobid(removeJobDigest.jobId))
    }
  }

  override def addRelatedDigest(currentState: JobState,
                                addJobRelatedDigest: AddRelatedDigestWrap): EventSourcedEntity.Effect[JobModifInfo] = {
    if (currentState.`sealed`) effects.error(error_msg_sealed)
    else if (currentState.jobId == addJobRelatedDigest.jobId) {
      val event = RelatedDigestAdded(Some(DomainRelatedDigest(addJobRelatedDigest.key,
        addJobRelatedDigest.relatedDigest)), Some(General.nowTimeStamp))
      effects.emitEvent(event).thenReply(_ => JobModifInfo(currentState.jobId, currentState.name, " digest added. "))
    } else {
      effects.error(error_msg_jobid(addJobRelatedDigest.jobId))
    }
  }

  override def removeRelatedDigest(currentState: JobState,
                                   rmRelatedDigest: RemoveRelatedDigestWrap): EventSourcedEntity.Effect[JobModifInfo] = {
    if (currentState.`sealed`) effects.error(error_msg_sealed)
    else if (currentState.jobId == rmRelatedDigest.jobId) {
      val event = RelatedDigestRemoved(rmRelatedDigest.key, Some(General.nowTimeStamp))
      effects.emitEvent(event).thenReply(_ => JobModifInfo(currentState.jobId, currentState.name, " digest removed.  "))
    } else effects.error(error_msg_jobid(rmRelatedDigest.jobId))
  }

  override def seal(currentState: JobState, sealJob: SealJobWrap): EventSourcedEntity.Effect[JobModifInfo] = {
    if (currentState.`sealed`) effects.error(error_msg_sealed)
    else if (currentState.jobId == sealJob.jobId) {
      val event = JobSealed(Some(General.nowTimeStamp))
      effects.emitEvent(event).thenReply(_ => JobModifInfo(currentState.jobId, currentState.name, " job sealed. "))
    } else effects.error(error_msg_jobid(sealJob.jobId))
  }

  override def delete(currentState: JobState, deleteJobWrap: DeleteJobWrap): EventSourcedEntity.Effect[JobModifInfo] = {
    if (currentState.jobId == deleteJobWrap.jobId && !currentState.deleted) {
      val event = JobDeleted(Some(General.nowTimeStamp))
      effects.emitEvent(event).thenReply(_ => JobModifInfo(currentState.jobId, currentState.name, " job deleted. "))
    } else effects.error(error_msg_jobid(deleteJobWrap.jobId))
  }

  override def restore(currentState: JobState, restoreJobWrap: RestoreJobWrap): EventSourcedEntity.Effect[JobModifInfo] = {
    if (currentState.jobId == restoreJobWrap.jobId && currentState.deleted) {
      val event = JobRestored(Some(General.nowTimeStamp))
      effects.emitEvent(event).thenReply(_ => JobModifInfo(currentState.jobId, currentState.name, " job restored. "))
    } else effects.error(error_msg_jobid(restoreJobWrap.jobId))
  }

  override def setStartDate(currentState: JobState, setStartDateWrap: SetStartDateWrap): EventSourcedEntity.Effect[JobModifInfo] = {
    if (currentState.`sealed`) effects.error(error_msg_sealed)
    else if (currentState.jobId == setStartDateWrap.jobId) {
      val event = StartRunDefined(setStartDateWrap.runStart, Some(General.nowTimeStamp))
      effects.emitEvent(event).thenReply(_ => JobModifInfo(currentState.jobId, currentState.name, " set start date. "))
    } else effects.error(error_msg_jobid(setStartDateWrap.jobId))
  }

  override def setEndDate(currentState: JobState, setEndDateWrap: SetEndDateWrap): EventSourcedEntity.Effect[JobModifInfo] = {
    if (currentState.`sealed`) effects.error(error_msg_sealed)
    else if (currentState.jobId == setEndDateWrap.jobId) {
      val event = EndRunDefined(setEndDateWrap.runEnd, Some(General.nowTimeStamp))
      effects.emitEvent(event).thenReply(_ => JobModifInfo(currentState.jobId, currentState.name, " set end date. "))
    } else effects.error(error_msg_jobid(setEndDateWrap.jobId))
  }

  override def setStatus(currentState: JobState, setStatusWrap: SetStatusWrap): EventSourcedEntity.Effect[JobModifInfo] = {
    if (currentState.`sealed`) effects.error(error_msg_sealed)
    else if (currentState.jobId == setStatusWrap.jobId) {
      val event = JobStatusChanged(status = DomainJobStatus.fromName(setStatusWrap.jobStatus.name)
        .getOrElse(DomainJobStatus.UNKNOWN), Some(General.nowTimeStamp))
      effects.emitEvent(event).thenReply(_ => JobModifInfo(currentState.jobId, currentState.name, " status changed. "))
    } else effects.error(error_msg_jobid(setStatusWrap.jobId))
  }

  override def retrieveJob(currentState: JobState, getJob: GetJobWrap): EventSourcedEntity.Effect[ApiJob] = {
    effects.reply(JobConversionHelpers.convertToApi(currentState))
  }

  override def jobAdded(currentState: JobState, jobAdded: JobAdded): JobState = {
    JobState(
      jobId = jobAdded.jobId,
      jobDefinitionId = jobAdded.jobDefinitionId,
      name = jobAdded.name,
      description = jobAdded.description,
      organization = jobAdded.organization,
      ownerEmail = jobAdded.ownerEmail,
      timeout = jobAdded.timeout,
      clonedFrom = jobAdded.clonedFrom,
      deleted = false,
      date = jobAdded.date)
  }

  override def parentJobAdded(currentState: JobState, parentJobAdded: ParentJobAdded): JobState = {
    val par = currentState.parentJobs :+ parentJobAdded.parentJobId
    currentState.copy(parentJobs = par, date = parentJobAdded.date)
  }

  override def parentJobRemoved(currentState: JobState, pjr: ParentJobRemoved): JobState =
    currentState.copy(parentJobs = currentState.parentJobs.filterNot(_ == pjr.key), date = pjr.date)

  override def jobParameterAdded(currentState: JobState, jobParameterAdded: JobParameterAdded): JobState = {
    val prs = currentState.jobParameters
      .filterNot(_.key == jobParameterAdded.getJobParameter.key) :+ jobParameterAdded.getJobParameter
    currentState.copy(jobParameters = prs, date = jobParameterAdded.date)
  }

  override def jobParameterRemoved(currentState: JobState, jpr: JobParameterRemoved): JobState =
    currentState.copy(jobParameters = currentState.jobParameters.filterNot(_.key == jpr.key),
      date = jpr.date)

  override def relatedDigestAdded(currentState: JobState, relatedDigestAdded: RelatedDigestAdded): JobState = {
    val rd = currentState.relatedDigests
      .filterNot(_.key == relatedDigestAdded.getRelatedDigest.key) :+ relatedDigestAdded.getRelatedDigest
    currentState.copy(relatedDigests = rd, date = relatedDigestAdded.date)
  }

  override def relatedDigestRemoved(currentState: JobState, rdr: RelatedDigestRemoved): JobState =
    currentState.copy(relatedDigests = currentState.relatedDigests.filterNot(_.key == rdr.key),
      date = rdr.date)

  override def jobPropertyAdded(currentState: JobState, jobPropertyAdded: JobPropertyAdded): JobState = {
    val prs = currentState.jobProperties
      .filterNot(_.key == jobPropertyAdded.getJobProperty.key) :+ jobPropertyAdded.getJobProperty
    currentState.copy(jobProperties = prs, date = jobPropertyAdded.date)
  }

  override def jobPropertyRemoved(currentState: JobState, jpr: JobPropertyRemoved): JobState =
    currentState.copy(jobProperties = currentState.jobProperties.filterNot(_.key == jpr.key), date = jpr.date)

  override def jobMetaDataAdded(currentState: JobState, jobMetaDataAdded: JobMetaDataAdded): JobState = {
    val prs = currentState.jobMetaData
      .filterNot(_.key == jobMetaDataAdded.getJobMetaData.key) :+ jobMetaDataAdded.getJobMetaData
    currentState.copy(jobMetaData = prs, date = jobMetaDataAdded.date)
  }

  override def jobMetaDataRemoved(currentState: JobState, jmdr: JobMetaDataRemoved): JobState = {
    currentState.copy(jobMetaData = currentState.jobMetaData.filterNot(_.key == jmdr.key), date = jmdr.date)
  }

  override def jobDigestSet(currentState: JobState, jobDigestAdded: JobDigestSet): JobState =
    currentState.copy(jobDigest = jobDigestAdded.jobDigest, date = jobDigestAdded.date)

  override def jobSealed(currentState: JobState, jobSealed: JobSealed): JobState =
    currentState.copy(`sealed` = true, date = jobSealed.date)

  override def jobDeleted(currentState: JobState, jobDeleted: JobDeleted): JobState =
    currentState.copy(deleted = true, date = jobDeleted.date)

  override def jobRestored(currentState: JobState, jobRestored: JobRestored): JobState =
    currentState.copy(deleted = false, date = jobRestored.date)

  override def jobStatusChanged(currentState: JobState, jobStatusChanged: JobStatusChanged): JobState =
    currentState.copy(jobStatus = jobStatusChanged.status, date = jobStatusChanged.date)

  override def startRunDefined(currentState: JobState, startRunDefined: StartRunDefined): JobState =
    currentState.copy(runStart = startRunDefined.start, date = startRunDefined.date)

  override def endRunDefined(currentState: JobState, endRunDefined: EndRunDefined): JobState =
    currentState.copy(runEnd = endRunDefined.end, date = endRunDefined.date)
}