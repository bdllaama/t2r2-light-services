package com.llaama.t2r2.domain

import com.llaama.t2r2.api
import kalix.scalasdk.eventsourcedentity.EventSourcedEntity
import kalix.scalasdk.testkit.EventSourcedResult
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

class JobSpec extends AnyWordSpec with Matchers {
  "The Job" should {
    "have example test that can be removed" in {
      val testKit = JobTestKit(new Job(_))
      pending
      // use the testkit to execute a command:
      // val result: EventSourcedResult[R] = testKit.someOperation(SomeRequest("id"));
      // verify the emitted events
      // val actualEvent: ExpectedEvent = result.nextEventOfType[ExpectedEvent]
      // actualEvent shouldBe expectedEvent
      // verify the final state after applying the events
      // testKit.state() shouldBe expectedState
      // verify the reply
      // result.reply shouldBe expectedReply
      // verify the final state after the command
    }

    "correctly process commands of type NewJob" in {
      val testKit = JobTestKit(new Job(_))
      pending
      // val result: EventSourcedResult[api.JobModifInfo] = testKit.newJob(api.AddJobWrap(...))
    }

    "correctly process commands of type AddProperty" in {
      val testKit = JobTestKit(new Job(_))
      pending
      // val result: EventSourcedResult[api.JobModifInfo] = testKit.addProperty(api.AddPropertyWrap(...))
    }

    "correctly process commands of type RemoveProperty" in {
      val testKit = JobTestKit(new Job(_))
      pending
      // val result: EventSourcedResult[api.JobModifInfo] = testKit.removeProperty(api.RemovePropertyWrap(...))
    }

    "correctly process commands of type AddParameter" in {
      val testKit = JobTestKit(new Job(_))
      pending
      // val result: EventSourcedResult[api.JobModifInfo] = testKit.addParameter(api.AddParameterWrap(...))
    }

    "correctly process commands of type RemoveParameter" in {
      val testKit = JobTestKit(new Job(_))
      pending
      // val result: EventSourcedResult[api.JobModifInfo] = testKit.removeParameter(api.RemoveParameterWrap(...))
    }

    "correctly process commands of type AddMetaData" in {
      val testKit = JobTestKit(new Job(_))
      pending
      // val result: EventSourcedResult[api.JobModifInfo] = testKit.addMetaData(api.AddMetaDataWrap(...))
    }

    "correctly process commands of type RemoveMetaData" in {
      val testKit = JobTestKit(new Job(_))
      pending
      // val result: EventSourcedResult[api.JobModifInfo] = testKit.removeMetaData(api.RemoveMetaDataWrap(...))
    }

    "correctly process commands of type AddDigest" in {
      val testKit = JobTestKit(new Job(_))
      pending
      // val result: EventSourcedResult[api.JobModifInfo] = testKit.addDigest(api.SetDigestWrap(...))
    }

    "correctly process commands of type RemoveDigest" in {
      val testKit = JobTestKit(new Job(_))
      pending
      // val result: EventSourcedResult[api.JobModifInfo] = testKit.removeDigest(api.RemoveDigestWrap(...))
    }

    "correctly process commands of type AddRelatedDigest" in {
      val testKit = JobTestKit(new Job(_))
      pending
      // val result: EventSourcedResult[api.JobModifInfo] = testKit.addRelatedDigest(api.AddRelatedDigestWrap(...))
    }

    "correctly process commands of type RemoveRelatedDigest" in {
      val testKit = JobTestKit(new Job(_))
      pending
      // val result: EventSourcedResult[api.JobModifInfo] = testKit.removeRelatedDigest(api.RemoveRelatedDigestWrap(...))
    }

    "correctly process commands of type Seal" in {
      val testKit = JobTestKit(new Job(_))
      pending
      // val result: EventSourcedResult[api.JobModifInfo] = testKit.seal(api.SealJobWrap(...))
    }

    "correctly process commands of type SetStartDate" in {
      val testKit = JobTestKit(new Job(_))
      pending
      // val result: EventSourcedResult[api.JobModifInfo] = testKit.setStartDate(api.SetStartDateWrap(...))
    }

    "correctly process commands of type SetEndDate" in {
      val testKit = JobTestKit(new Job(_))
      pending
      // val result: EventSourcedResult[api.JobModifInfo] = testKit.setEndDate(api.SetEndDateWrap(...))
    }

    "correctly process commands of type SetStatus" in {
      val testKit = JobTestKit(new Job(_))
      pending
      // val result: EventSourcedResult[api.JobModifInfo] = testKit.setStatus(api.SetStatusWrap(...))
    }

    "correctly process commands of type RetrieveJob" in {
      val testKit = JobTestKit(new Job(_))
      pending
      // val result: EventSourcedResult[api.Job] = testKit.retrieveJob(api.GetJobWrap(...))
    }
  }
}
