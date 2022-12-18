package com.llaama.t2r2.domain

import com.google.protobuf.empty.Empty
import com.llaama.t2r2.api
import kalix.scalasdk.eventsourcedentity.EventSourcedEntity
import kalix.scalasdk.testkit.EventSourcedResult
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

class JobDefinitionSpec extends AnyWordSpec with Matchers {
  "The JobDefinition" should {
    "have example test that can be removed" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
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

    "correctly process commands of type NewDefinition" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.newDefinition(api.AddJobDefinition(...))
    }

    "correctly process commands of type AddProperty" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.addProperty(api.AddJobDefinitionProperty(...))
    }

    "correctly process commands of type RemoveProperty" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.removeProperty(api.RemoveJobDefinitionProperty(...))
    }

    "correctly process commands of type AddDigest" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.addDigest(api.AddJobDefinitionDigest(...))
    }

    "correctly process commands of type RemoveDigest" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.removeDigest(api.RemoveJobDefinitionDigest(...))
    }

    "correctly process commands of type AddOptionParameter" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.addOptionParameter(api.AddOptionParam(...))
    }

    "correctly process commands of type RemoveOptionParameter" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.removeOptionParameter(api.RemoveOptionParam(...))
    }

    "correctly process commands of type AddIntParameter" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.addIntParameter(api.AddIntParam(...))
    }

    "correctly process commands of type RemoveIntParameter" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.removeIntParameter(api.RemoveIntParam(...))
    }

    "correctly process commands of type AddFloatParameter" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.addFloatParameter(api.AddFloatParam(...))
    }

    "correctly process commands of type RemoveFloatParameter" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.removeFloatParameter(api.RemoveFloatParam(...))
    }

    "correctly process commands of type AddTextParameter" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.addTextParameter(api.AddTextParam(...))
    }

    "correctly process commands of type RemoveTextParameter" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.removeTextParameter(api.RemoveTextParam(...))
    }

    "correctly process commands of type AddMeta" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.addMeta(api.AddMetaData(...))
    }

    "correctly process commands of type RemoveMeta" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.removeMeta(api.RemoveMetaData(...))
    }

    "correctly process commands of type AddContainerInfo" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.addContainerInfo(api.AddContainer(...))
    }

    "correctly process commands of type RemoveContainerInfo" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.removeContainerInfo(api.RemoveContainer(...))
    }

    "correctly process commands of type Seal" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
      pending
      // val result: EventSourcedResult[Empty] = testKit.seal(api.SealJobDefinition(...))
    }

    "correctly process commands of type GetDefinition" in {
      val testKit = JobDefinitionTestKit(new JobDefinition(_))
      pending
      // val result: EventSourcedResult[api.JobDefinition] = testKit.getDefinition(api.GetJobDefinition(...))
    }
  }
}
