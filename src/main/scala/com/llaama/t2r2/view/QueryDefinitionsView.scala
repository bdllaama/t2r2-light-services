package com.llaama.t2r2.view

import com.google.protobuf.any.{Any => ScalaPbAny}
import com.llaama.t2r2.api.JobDefinition
import com.llaama.t2r2.domain.JobDefinitionAdded
import com.llaama.t2r2.utils.DefConversionHelpers
import kalix.scalasdk.view.View.UpdateEffect
import kalix.scalasdk.view.ViewContext

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

class QueryDefinitionsView(context: ViewContext) extends AbstractQueryDefinitionsView {


  override def emptyState: JobDefinition = JobDefinition.defaultInstance


  override def processJobDefinitionAdded(state: JobDefinition,
                                         jobDefinitionAdded: JobDefinitionAdded): UpdateEffect[JobDefinition] =
    effects.updateState(DefConversionHelpers.convertToApi(jobDefinitionAdded))


  override def ignoreOtherEvents(state: JobDefinition,
                                 any: ScalaPbAny): UpdateEffect[JobDefinition] = effects.ignore()
}
