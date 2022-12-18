package com.llaama.t2r2

import com.llaama.t2r2.api.NewJobActionImpl
import com.llaama.t2r2.domain.{Job, JobDefinition}
import com.llaama.t2r2.utils.NameGenerator
import com.llaama.t2r2.view.{QueryDefinitionsView, QueryJobsSortedView, QueryJobsView}
import kalix.scalasdk.Kalix
import org.slf4j.LoggerFactory

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

object Main {

  private val log = LoggerFactory.getLogger("com.llaama.t2r2.Main")

  def createKalix(): Kalix = {
    // The kalixFactory automatically registers any generated Actions, Views or Entities,
    // and is kept up-to-date with any changes in your protobuf definitions.
    // If you prefer, you may remove this and manually register these components in a
    // `kalix()` instance.
    KalixFactory.withComponents(
      new Job(_),
      new JobDefinition(_),
      new NewJobActionImpl(_),
      new QueryDefinitionsView(_),
      new QueryJobsView(_),
      new QueryJobsSortedView(_))
  }

  def main(args: Array[String]): Unit = {
    log.info("starting the Akka Serverless service")
    log.info(s"name generator works: ${NameGenerator.getName}")

    createKalix().start()
  }
}
