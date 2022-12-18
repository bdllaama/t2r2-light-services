package com.llaama.t2r2.view

import com.google.protobuf.any.{Any => ScalaPbAny}
import com.llaama.t2r2.api.JobForView
import com.llaama.t2r2.domain._
import com.llaama.t2r2.utils.JobConversionHelpers
import kalix.scalasdk.view.View.UpdateEffect
import kalix.scalasdk.view.ViewContext

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

class QueryJobsView(context: ViewContext) extends AbstractQueryJobsView {

  override def emptyState: JobForView = JobForView.defaultInstance

  override def processJobAdded(state: JobForView, jobAdded: JobAdded): UpdateEffect[JobForView] =
    effects.updateState(JobConversionHelpers.convertToApi(jobAdded))

  override def processStartRunDefined(state: JobForView, startRunDefined: StartRunDefined): UpdateEffect[JobForView] = {
    val negDur = startRunDefined.getStart.seconds * -1
    effects.updateState(state.copy(duration = negDur, date = startRunDefined.date))
  }

  override def processEndRunDefined(state: JobForView, endRunDefined: EndRunDefined): UpdateEffect[JobForView] = {
    val dur = endRunDefined.getEnd.seconds - (state.duration * -1)
    effects.updateState(state.copy(duration = dur, date = endRunDefined.date))
  }

  override def processJobStatusChanged(state: JobForView, jobStatusChanged: JobStatusChanged): UpdateEffect[JobForView] =
    effects.updateState(state.copy(jobStatus = JobConversionHelpers
      .statusConvert(jobStatusChanged.status), date = jobStatusChanged.date))

  override def processJobDeleted(state: JobForView, jobDeleted: JobDeleted): UpdateEffect[JobForView] =
    effects.updateState(state.copy(deleted = true, date = jobDeleted.date))

  override def processJobRestored(state: JobForView, jobRestored: JobRestored): UpdateEffect[JobForView] =
  effects.updateState(state.copy(deleted = false, date = jobRestored.date))

  override def ignoreOtherEvents(state: JobForView, any: ScalaPbAny): UpdateEffect[JobForView] =
    effects.ignore()
}
