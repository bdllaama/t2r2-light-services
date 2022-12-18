package com.llaama.t2r2.api


import com.llaama.t2r2.utils.General
import kalix.scalasdk.testkit.MockRegistry
import org.scalamock.clazz.MockImpl._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.Future

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

class NewJobActionImplSpec
  extends AnyWordSpec
    with Matchers {

  import NewJobActionImplSpec._
  import JobDefinitionServiceIntegrationSpec._

  val addJ = AddNewJob(job1.jobDefinitionId, job1.name, job1.description, job1.organization,
    job1.ownerEmail, job1.timeout, job1.clonedFrom)

  //TODO fix the stub issue (see: https://docs.kalix.io/java/actions-as-controller.html)

  "NewJobActionImpl" must {

    "handle command CreateNewJob" in {
      val mockDef = stub[JobDefinitionService]
      (mockDef.getDefinition _).when(*).returns(Future.successful(jd))

      val mockRegistry = MockRegistry.withMock(mockDef)
      val service = NewJobActionImplTestKit(new NewJobActionImpl(_), mockRegistry)
//      pending
      // use the testkit to execute a command
      // and verify final updated state:
      // val result = service.someOperation(SomeRequest)
      // verify the reply
      // result.reply shouldBe expectedReply
      val result = service.createNewJob(addJ)

      result.reply shouldBe a[NewJobCreated]
    }
  }
}

object NewJobActionImplSpec {

  import com.llaama.t2r2.api.JobDefinitionServiceIntegrationSpec._

  val job1 = Job(jobId = "GENERATED_UID_8888", jobDefinitionId = uid,
    name = "test job", description = "does a lot of good things!",
    organization = "com.mars", ownerEmail = "tim.red@great.com",
    date = Some(General.nowTimeStamp), runStart = Some(General.nowTimeStamp),
    runEnd = Some(General.nowTimeStamp), jobStatus = JobStatus.SUCCESS, timeout = 100, `sealed` = true,
    jobDigest = "final_digest", clonedFrom = "", jobParameters = Seq.empty, jobProperties = Seq.empty,
    parentJobs = Seq.empty, relatedDigests = RelatedDigest("key111", "digest9999") ::
      RelatedDigest("key222", "digest888") :: Nil,
    jobMetaData = JobMetaData("meta.key1", "meta_data1") :: JobMetaData("meta.key2", "meta_data2") :: Nil)

}













