package com.llaama.t2r2.api

import com.llaama.t2r2.utils.NameGenerator
import kalix.scalasdk.action.{Action, ActionCreationContext}

import java.util.UUID
import scala.concurrent.Future


// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

/** An action. */
class NewJobActionImpl(creationContext: ActionCreationContext) extends AbstractNewJobAction {

  // tag::createPrePopulated[]

  /** Handler for "CreateNewJob". */
  override def createNewJob(addNewJob: AddNewJob): Action.Effect[NewJobCreated] = {

    val jobId = UUID.randomUUID().toString

    val jobName = if (addNewJob.name.isEmpty || addNewJob.name.isBlank) {
      NameGenerator.getName
    } else addNewJob.name

    val getJobDefinitionId =
      components.jobDefinition.getDefinition(GetJobDefinition(addNewJob.jobDefinitionId)).execute()
    //todo check sealed

    import scala.concurrent.duration._
//    Await.result(getJobDefinitionId, 10.seconds)

    val createNowJ = getJobDefinitionId.flatMap(jdef => {
      val addJob = AddJobWrap(jobId = jobId, jobDefinitionId = jdef.jobDefinitionId,
        name = jobName, description = addNewJob.description,
        organization = addNewJob.organization, ownerEmail = addNewJob.ownerEmail,
        timeout = addNewJob.timeout, clonedFrom = addNewJob.clonedFrom)
//      println(s"jobdef id: ${jdef.jobDefinitionId}")
      components.job.newJob(addJob).execute()
    })

    val result: Future[Action.Effect[NewJobCreated]] =
      createNowJ.map(_ => effects.reply(NewJobCreated(jobId, jobName)))
        .recover(_ => effects.error("failed creating new job. "))

    effects.asyncEffect(result)
  } // <3>
}
// end::createPrePopulated[]

