package com.llaama.t2r2.view

import com.google.protobuf.any.{ Any => ScalaPbAny }
import com.google.protobuf.empty.Empty
import com.llaama.t2r2.api.AllJobs
import com.llaama.t2r2.api.JobForView
import kalix.scalasdk.view.View.UpdateEffect
import kalix.scalasdk.view.ViewContext

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

class QueryJobsSortedView(context: ViewContext) extends AbstractQueryJobsSortedView {

  override def emptyState: JobForView =
    throw new UnsupportedOperationException("Not implemented yet, replace with your empty view state")

  override def ignoreOtherEvents(
    state: JobForView, any: ScalaPbAny): UpdateEffect[JobForView] =
    throw new UnsupportedOperationException("Update handler for 'IgnoreOtherEvents' not implemented yet")
}
