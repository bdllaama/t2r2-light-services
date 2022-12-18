package com.llaama.t2r2.domain

import com.google.protobuf.empty.Empty
import com.llaama.t2r2.api
import com.llaama.t2r2.api.{AddContainer, AddJobDefinitionProperty, AddMetaData, RemoveContainer, RemoveIntParam, RemoveMetaData, SealJobDefinition}
import com.llaama.t2r2.utils.DefConversionHelpers.{convertContainerADT2Api, convertContainerAPI2Domain}
import com.llaama.t2r2.utils.General
import kalix.scalasdk.eventsourcedentity.{EventSourcedEntity, EventSourcedEntityContext}

import scala.annotation.nowarn
import scala.util.matching.Regex

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

//TODO replace Effect[Empty] with something useful


/** An event sourced entity. */
class JobDefinition(context: EventSourcedEntityContext) extends AbstractJobDefinition {
  val errorOnUID =
    """job definition unique id should be formed like 'organization@short_name@version'
      |where organization is something like 'com.llaama', shortname is a name without
      |spaces and special characters except _ and - and version something like 4.2.5 or b5890, etc.""".stripMargin

  val reg: Regex = "[a-z0-9-_\\.]+".r

  @nowarn("msg=unused")
  private val entityId = context.entityId

  override def emptyState: JobDefinitionState = JobDefinitionState.defaultInstance

  override def newDefinition(currentState: JobDefinitionState, addJobDef: api.AddJobDefinition): EventSourcedEntity.Effect[Empty] =
    if (currentState.`sealed`) effects.error("Job definition is sealed and cannot be changed anymore. ")
    else if (addJobDef.jobDefinitionId.isEmpty) {
      effects.error("job definition unique id cannot be empty ")
    } else {
      val jobIdSplit = addJobDef.jobDefinitionId.toLowerCase.split("@")
      if (jobIdSplit.length != 3 || !reg.matches(jobIdSplit(0)) || !reg.matches(jobIdSplit(1)) || !reg.matches(jobIdSplit(2)))
        effects.error(errorOnUID)
      else {
        val event = JobDefinitionAdded(
          jobDefinitionId = addJobDef.jobDefinitionId,
          shortName = jobIdSplit(1),
          organization = jobIdSplit(0),
          buildVersion = jobIdSplit(2),
          name = if (addJobDef.name.nonEmpty) addJobDef.name else currentState.name,
          ownerEmail = if (addJobDef.ownerEmail.nonEmpty) addJobDef.ownerEmail else currentState.ownerEmail,
          date = Some(General.nowTimeStamp),
          description = if (addJobDef.description.nonEmpty) addJobDef.description else currentState.description,
          consumes = if (addJobDef.consumes.nonEmpty) addJobDef.consumes else currentState.consumes,
          produces = if (addJobDef.produces.nonEmpty) addJobDef.produces else currentState.produces,
          releaseNotes = if (addJobDef.releaseNotes.nonEmpty) addJobDef.releaseNotes else currentState.releaseNotes,
        )
        effects.emitEvent(event).thenReply(_ => Empty.defaultInstance)
      }
    }

  override def addProperty(currentState: JobDefinitionState, addJobDefinitionProperty: AddJobDefinitionProperty): EventSourcedEntity.Effect[Empty] = {
    if (currentState.`sealed`) effects.error("Job definition is sealed and cannot be changed anymore. ")
    else if (currentState.jobDefinitionId == addJobDefinitionProperty.jobDefinitionId) {
      val event = JobDefinitionPropertyAdded(Some(JobDefinitionProperty(addJobDefinitionProperty.key, addJobDefinitionProperty.value)))
      effects.emitEvent(event).thenReply(_ => Empty.defaultInstance)
    } else {
      effects.error("Job definition does not exist. ")
    }
  }

  override def removeProperty(currentState: JobDefinitionState, rmJobDefinitionProperty: api.RemoveJobDefinitionProperty): EventSourcedEntity.Effect[Empty] = {
    if (currentState.`sealed`) effects.error("Job definition is sealed and cannot be changed anymore. ")
    else {
      val event = JobDefinitionPropertyRemoved(rmJobDefinitionProperty.key)
      effects.emitEvent(event).thenReply(_ => Empty.defaultInstance)
    }
  }

  override def addDigest(currentState: JobDefinitionState, addJobDefinitionDigest: api.AddJobDefinitionDigest): EventSourcedEntity.Effect[Empty] = {
    if (currentState.`sealed`) effects.error("Job definition is sealed and cannot be changed anymore. ")
    else if (currentState.jobDefinitionId == addJobDefinitionDigest.jobDefinitionId) {
      val event = JobDefinitionDigestAdded(Some(JobDefinitionDigest(addJobDefinitionDigest.key, addJobDefinitionDigest.digest)))
      effects.emitEvent(event).thenReply(_ => Empty.defaultInstance)
    } else {
      effects.error("Job definition does not exist. ")
    }
  }

  override def removeDigest(currentState: JobDefinitionState, rmJobDefinitionDigest: api.RemoveJobDefinitionDigest): EventSourcedEntity.Effect[Empty] = {
    if (currentState.`sealed`) effects.error("Job definition is sealed and cannot be changed anymore. ")
    else {
      val event = JobDefinitionDigestRemoved(rmJobDefinitionDigest.key)
      effects.emitEvent(event).thenReply(_ => Empty.defaultInstance)
    }
  }

  override def addMeta(currentState: JobDefinitionState, addMetaData: AddMetaData): EventSourcedEntity.Effect[Empty] = {
    if (currentState.jobDefinitionId == addMetaData.jobDefinitionId) {
      val md = addMetaData.metaData.get
      val event = MetaDataAdded(Some(MetaData(md.key, md.metaData, md.user)))
      effects.emitEvent(event).thenReply(_ => Empty.defaultInstance)
    } else {
      effects.error("Job definition does not exist. ")
    }
  }

  override def removeMeta(currentState: JobDefinitionState, removeMetaData: RemoveMetaData): EventSourcedEntity.Effect[Empty] = {
    if (currentState.`sealed`) effects.error("Job definition is sealed and cannot be changed anymore. ")
    else {
      val event = MetaDataRemoved(removeMetaData.key)
      effects.emitEvent(event).thenReply(_ => Empty.defaultInstance)
    }
  }

  override def addContainerInfo(currentState: JobDefinitionState, addContainer: AddContainer): EventSourcedEntity.Effect[Empty] = {
    if (currentState.jobDefinitionId == addContainer.jobDefinitionId) {
      val event = ContainerAdded(Some(convertContainerAPI2Domain(addContainer.container.get)))
      effects.emitEvent(event).thenReply(_ => Empty.defaultInstance)
    } else {
      effects.error("Job definition does not exist. ")
    }
  }

  override def removeContainerInfo(currentState: JobDefinitionState, removeContainer: RemoveContainer): EventSourcedEntity.Effect[Empty] = {
    if (currentState.`sealed`) effects.error("Job definition is sealed and cannot be changed anymore. ")
    else {
      val event = ContainerRemoved(removeContainer.key)
      effects.emitEvent(event).thenReply(_ => Empty.defaultInstance)
    }
  }

  override def seal(currentState: JobDefinitionState, sealJobDefinition: SealJobDefinition): EventSourcedEntity.Effect[Empty] = {
    if (currentState.shortName.isEmpty || currentState.organization.isEmpty ||
    currentState.buildVersion.isEmpty) effects.error("Job Definition not yet defined, too early to seal.")
    else if (currentState.`sealed`) effects.error("Job definition is already sealed and cannot be changed anymore. ")
    else {
      val event = JobDefinitionSealed(
        date = Some(General.nowTimeStamp),
      )
      effects.emitEvent(event).thenReply(_ => Empty.defaultInstance)
    }
  }

  override def getDefinition(currentState: JobDefinitionState, getJobDefinition: api.GetJobDefinition): EventSourcedEntity.Effect[api.JobDefinition] = {
    val props = currentState.definitionProperties.map(kv => api.JobDefinitionProperty(kv.key, kv.value))
    val intPar = currentState.intParameters.map(p => api.IntParameter(p.key, p.order, p.name, p.hint, p.minValue, p.minValue, p.defaultValue))
    val floatPar = currentState.floatParameters.map(p => api.FloatParameter(p.key, p.order, p.name, p.hint, p.minValue, p.minValue, p.defaultValue))
    val textPar = currentState.textParameters.map(p => api.TextParameter(p.key, p.order, p.name, p.defaultValue))
    val optionPar = currentState.optionParameters.map(p => api.OptionParameter(p.key, p.order, p.name, p.hint, p.options, allowsNew = p.allowsNew, p.defaultValue))
    val digs = currentState.definitionDigests.map(d => api.JobDefinitionDigest(d.key, d.digest))
    val meta = currentState.metaData.map(md => api.MetaData(md.key, md.metaData, md.user))
    val cont = currentState.containers.map(convertContainerADT2Api)

    effects.reply(api.JobDefinition(
      jobDefinitionId = currentState.jobDefinitionId,
      shortName = currentState.shortName,
      name = currentState.name,
      organization = currentState.organization,
      buildVersion = currentState.buildVersion,
      ownerEmail = currentState.ownerEmail,
      date = currentState.date,
      `sealed` = currentState.`sealed`,
      description = currentState.description,
      consumes = currentState.consumes,
      produces = currentState.produces,
      releaseNotes = currentState.releaseNotes,
      definitionProperties = props,
      intParameters = intPar,
      floatParameters = floatPar,
      textParameters = textPar,
      optionParameters = optionPar,
      definitionDigests = digs,
      metaData = meta,
      containers = cont))
  }

  override def jobDefinitionAdded(currentState: JobDefinitionState, jobDefinitionAdded: JobDefinitionAdded): JobDefinitionState = {
    JobDefinitionState(
      jobDefinitionId = jobDefinitionAdded.jobDefinitionId,
      shortName = if (jobDefinitionAdded.shortName.nonEmpty) jobDefinitionAdded.shortName else currentState.shortName,
      name = if (jobDefinitionAdded.name.nonEmpty) jobDefinitionAdded.name else currentState.name,
      organization = if (jobDefinitionAdded.organization.nonEmpty) jobDefinitionAdded.organization else currentState.organization,
      buildVersion = if (jobDefinitionAdded.buildVersion.nonEmpty) jobDefinitionAdded.buildVersion else currentState.buildVersion,
      ownerEmail = if (jobDefinitionAdded.ownerEmail.nonEmpty) jobDefinitionAdded.ownerEmail else currentState.ownerEmail,
      description = if (jobDefinitionAdded.description.nonEmpty) jobDefinitionAdded.description else currentState.description,
      consumes = if (jobDefinitionAdded.consumes.nonEmpty) jobDefinitionAdded.consumes else currentState.consumes,
      produces = if (jobDefinitionAdded.produces.nonEmpty) jobDefinitionAdded.produces else currentState.produces,
      releaseNotes = if (jobDefinitionAdded.releaseNotes.nonEmpty) jobDefinitionAdded.releaseNotes else currentState.releaseNotes,

      date = Some(General.nowTimeStamp))
  }

  override def jobDefinitionSealed(currentState: JobDefinitionState, jobDefinitionSealed: JobDefinitionSealed): JobDefinitionState = {
    if (currentState.`sealed`) currentState
    else currentState.copy(`sealed` = true)
  }

  override def addOptionParameter(currentState: JobDefinitionState, addOptionParam: api.AddOptionParam): EventSourcedEntity.Effect[Empty] = {
    if (currentState.`sealed`) effects.error("Job definition is sealed and cannot be changed anymore. ")
    else if (currentState.jobDefinitionId == addOptionParam.jobDefinitionId) {
      val event = OptionParameterAdded(addOptionParam.optionParameter.fold[Option[OptionParameter]](None)(op =>
        Some(OptionParameter(op.key, op.order, op.name, op.hint, op.options, op.allowsNew, op.defaultValue))))
      effects.emitEvent(event).thenReply(_ => Empty.defaultInstance)
    } else {
      effects.error("Job definition does not exist. ")
    }
  }

  override def removeOptionParameter(currentState: JobDefinitionState, removeOptionParam: api.RemoveOptionParam): EventSourcedEntity.Effect[Empty] = {
    if (currentState.`sealed`) effects.error("Job definition is sealed and cannot be changed anymore. ")
    else {
      val event = OptionParameterRemoved(removeOptionParam.key)
      effects.emitEvent(event).thenReply(_ => Empty.defaultInstance)
    }
  }

  override def addIntParameter(currentState: JobDefinitionState, addIntParam: api.AddIntParam): EventSourcedEntity.Effect[Empty] = {
    if (currentState.`sealed`) effects.error("Job definition is sealed and cannot be changed anymore. ")
    else if (currentState.jobDefinitionId == addIntParam.jobDefinitionId) {
      val event = IntParameterAdded(addIntParam.intParameter.fold[Option[IntParameter]](None)(iv =>
        Some(IntParameter( iv.key, iv.order, iv.name, iv.hint, iv.minValue, iv.maxValue, iv.defaultValue))))
      effects.emitEvent(event).thenReply(_ => Empty.defaultInstance)
    } else {
      effects.error("Job definition does not exist. ")
    }
  }

  override def removeIntParameter(currentState: JobDefinitionState, removeIntParam: RemoveIntParam): EventSourcedEntity.Effect[Empty] = {
    if (currentState.`sealed`) effects.error("Job definition is sealed and cannot be changed anymore. ")
    else {
      val event = IntParameterRemoved(removeIntParam.key)
      effects.emitEvent(event).thenReply(_ => Empty.defaultInstance)
    }
  }

  override def addFloatParameter(currentState: JobDefinitionState, addFloatParam: api.AddFloatParam): EventSourcedEntity.Effect[Empty] = {
    if (currentState.`sealed`) effects.error("Job definition is sealed and cannot be changed anymore. ")
    else if (currentState.jobDefinitionId == addFloatParam.jobDefinitionId) {
      val event = FloatParameterAdded(addFloatParam.floatParameter.fold[Option[FloatParameter]](None)(f =>
        Some(FloatParameter(f.key, f.order, f.name, f.hint, f.minValue, f.maxValue, f.defaultValue))))
      effects.emitEvent(event).thenReply(_ => Empty.defaultInstance)
    } else {
      effects.error("Job definition does not exist. ")
    }
  }

  override def removeFloatParameter(currentState: JobDefinitionState, removeFloatParam: api.RemoveFloatParam): EventSourcedEntity.Effect[Empty] = {
    if (currentState.`sealed`) effects.error("Job definition is sealed and cannot be changed anymore. ")
    else {
      val event = FloatParameterRemoved(removeFloatParam.key)
      effects.emitEvent(event).thenReply(_ => Empty.defaultInstance)
    }
  }

  override def addTextParameter(currentState: JobDefinitionState, addTextParam: api.AddTextParam): EventSourcedEntity.Effect[Empty] = {
    if (currentState.`sealed`) effects.error("Job definition is sealed and cannot be changed anymore. ")
    else if (currentState.jobDefinitionId == addTextParam.jobDefinitionId) {
      val event = TextParameterAdded(addTextParam.textParameter.fold[Option[TextParameter]](None)(t =>
        Some(TextParameter(t.key, t.order, t.name, t.hint, t.defaultValue))))
      effects.emitEvent(event).thenReply(_ => Empty.defaultInstance)
    } else {
      effects.error("Job definition does not exist. ")
    }
  }

  override def removeTextParameter(currentState: JobDefinitionState, removeTextParam: api.RemoveTextParam): EventSourcedEntity.Effect[Empty] = {
    if (currentState.`sealed`) effects.error("Job definition is sealed and cannot be changed anymore. ")
    else {
      val event = TextParameterRemoved(removeTextParam.key)
      effects.emitEvent(event).thenReply(_ => Empty.defaultInstance)
    }
  }

  override def jobDefinitionPropertyAdded(currentState: JobDefinitionState, jobDefinitionPropertyAdded: JobDefinitionPropertyAdded): JobDefinitionState = {
    val prs = currentState.definitionProperties
      .filterNot(_.key == jobDefinitionPropertyAdded.getJobDefinitionProp.key) :+ jobDefinitionPropertyAdded.getJobDefinitionProp
    currentState.copy(definitionProperties = prs)
  }

  override def jobDefinitionPropertyRemoved(currentState: JobDefinitionState, jobDefinitionPropertyRemoved: JobDefinitionPropertyRemoved): JobDefinitionState = {
    currentState.copy(definitionProperties = currentState.definitionProperties
      .filterNot(_.key == jobDefinitionPropertyRemoved.key))
  }

  override def jobDefinitionDigestAdded(currentState: JobDefinitionState, jobDefinitionDigestAdded: JobDefinitionDigestAdded): JobDefinitionState = {
    val prs = currentState.definitionDigests
      .filterNot(_.key == jobDefinitionDigestAdded.getJobDefinitionDigest.key) :+ jobDefinitionDigestAdded.getJobDefinitionDigest
    currentState.copy(definitionDigests = prs)
  }

  override def jobDefinitionDigestRemoved(currentState: JobDefinitionState, jobDefinitionDigestRemoved: JobDefinitionDigestRemoved): JobDefinitionState = {
    currentState.copy(definitionDigests = currentState.definitionDigests
      .filterNot(_.key == jobDefinitionDigestRemoved.key))
  }

  override def intParameterAdded(currentState: JobDefinitionState, intParameterAdded: IntParameterAdded): JobDefinitionState = {
    val intParams = currentState.intParameters
      .filterNot(_.key == intParameterAdded.getIntParameter.key) :+ intParameterAdded.getIntParameter
    currentState.copy(intParameters = intParams)
  }

  override def intParameterRemoved(currentState: JobDefinitionState, intParameterRemoved: IntParameterRemoved): JobDefinitionState = {
    currentState.copy(intParameters = currentState.intParameters
      .filterNot(_.key == intParameterRemoved.key))
  }

  override def optionParameterAdded(currentState: JobDefinitionState, optionParameterAdded: OptionParameterAdded): JobDefinitionState = {
    val optionParams = currentState.optionParameters
      .filterNot(_.key == optionParameterAdded.getOptionParameter.key) :+ optionParameterAdded.getOptionParameter
    currentState.copy(optionParameters = optionParams)
  }

  override def optionParameterRemoved(currentState: JobDefinitionState, optionParameterRemoved: OptionParameterRemoved): JobDefinitionState = {
    currentState.copy(optionParameters = currentState.optionParameters
      .filterNot(_.key == optionParameterRemoved.key))
  }

  override def textParameterAdded(currentState: JobDefinitionState, textParameterAdded: TextParameterAdded): JobDefinitionState = {
    val textParams = currentState.textParameters
      .filterNot(_.key == textParameterAdded.getTextParameter.key) :+ textParameterAdded.getTextParameter
    currentState.copy(textParameters = textParams)
  }

  override def textParameterRemoved(currentState: JobDefinitionState, textParameterRemoved: TextParameterRemoved): JobDefinitionState = {
    currentState.copy(textParameters = currentState.textParameters
      .filterNot(_.key == textParameterRemoved.key))
  }

  override def floatParameterAdded(currentState: JobDefinitionState, floatParameterAdded: FloatParameterAdded): JobDefinitionState = {
    val floatParams = currentState.floatParameters
      .filterNot(_.key == floatParameterAdded.getFloatParameter.key) :+ floatParameterAdded.getFloatParameter
    currentState.copy(floatParameters = floatParams)
  }

  override def floatParameterRemoved(currentState: JobDefinitionState, floatParameterRemoved: FloatParameterRemoved): JobDefinitionState = {
    currentState.copy(floatParameters = currentState.floatParameters
      .filterNot(_.key == floatParameterRemoved.key))
  }

  override def metaDataAdded(currentState: JobDefinitionState, metaDataAdded: MetaDataAdded): JobDefinitionState = {
    val md = currentState.metaData.filterNot(_.key == metaDataAdded.getMetaData.key) :+ metaDataAdded.getMetaData
    currentState.copy(metaData = md)
  }

  override def metaDataRemoved(currentState: JobDefinitionState, metaDataRemoved: MetaDataRemoved): JobDefinitionState = {
    currentState.copy(metaData = currentState.metaData.filterNot(_.key == metaDataRemoved.key))
  }

  override def containerAdded(currentState: JobDefinitionState, containerAdded: ContainerAdded): JobDefinitionState = {
    val cont = currentState.containers :+ containerAdded.getContainer
    currentState.copy(containers = cont)
  }

  override def containerRemoved(currentState: JobDefinitionState, containerRemoved: ContainerRemoved): JobDefinitionState = {
    currentState.copy(containers = currentState.containers.filterNot(_.id == containerRemoved.id))
  }
}
