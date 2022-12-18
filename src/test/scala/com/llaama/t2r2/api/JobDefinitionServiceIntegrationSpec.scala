package com.llaama.t2r2.api

import com.llaama.t2r2.Main
import com.llaama.t2r2.utils.General

import kalix.scalasdk.testkit.KalixTestKit
import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.wordspec.AnyWordSpec

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

//TODO consolidate default test values

class JobDefinitionServiceIntegrationSpec
  extends AnyWordSpec
    with Matchers
    with BeforeAndAfterAll
    with ScalaFutures {

  implicit private val patience: PatienceConfig =
    PatienceConfig(Span(5, Seconds), Span(500, Millis))

  private val testKit = KalixTestKit(Main.createKalix()).start()

  import testKit.executionContext

  private val client = testKit.getGrpcClient(classOf[JobDefinitionService])

  import JobDefinitionServiceIntegrationSpec._

  "JobDefinitionService" must {

    "Create a new job definition " in {
      // use the gRPC client to send requests to the

      val addJ = new AddJobDefinition(uid, "test123", "test@test.com",
        Some(General.nowTimeStamp), "test", "test", "test", "new")

      val add = for {
        done <- client.newDefinition(addJ)
      } yield done

      add.futureValue

      // proxy and verify the results
      val getJDef = client.getDefinition(GetJobDefinition(uid))
      getJDef.futureValue.name shouldBe ("test123")
    }
  }

  val fp = FloatParameter("key1", 1, "test", "test val", -5.0d, 5.0d, 1.0d)
  val ip = IntParameter("key2", 2, "test", "test val", -15, 25, 10)
  val op = OptionParameter("key3", 3, "test", "test val", "opt1" :: "opt2" :: Nil, true, "opt1")
  val tp = TextParameter("key4", 4, "test", "test val", "hello world")
  val md = MetaData("domain1.key345", "first.last@comp.com", "meta info")
  val dg = JobDefinitionDigest("domain435.object523", "should_be_a_digest")
  val jp = JobProperty( "prop.key1", "test")
  val cl = ContainerLocation("dockerServer", "repo", "1.0.0", "image_digest", "calculus", "hello.mars@neptune.io")

  "Add property, parameter, meta, container" in {

    val add = for {
      _ <- client.addProperty(AddJobDefinitionProperty(uid,jp.key, jp.value))
      _ <- client.addFloatParameter(AddFloatParam(uid, Some(fp)))
      _ <- client.addIntParameter(AddIntParam(uid, Some(ip)))
      _ <- client.addOptionParameter(AddOptionParam(uid, Some(op)))
      _ <- client.addTextParameter(AddTextParam(uid, Some(tp)))
      _ <- client.addMeta(AddMetaData(uid, Some(md)))
      _ <- client.addContainerInfo(AddContainer(uid, Some(cl)))
      done <- client.addDigest(AddJobDefinitionDigest(uid, dg.key, dg.digest))
    } yield done

    add.futureValue

    val getJDef = client.getDefinition(GetJobDefinition(uid))

    val jd = getJDef.futureValue

    jd.definitionDigests.map(_.key) should contain(dg.key)
    jd.intParameters.map(_.key) should contain(ip.key)
    jd.floatParameters.map(_.key) should contain(fp.key)
    jd.textParameters.map(_.key) should contain(tp.key)
    jd.optionParameters.map(_.key) should contain(op.key)
    jd.definitionProperties.map(_.key) should contain(jp.key)
    jd.metaData.map(_.key) should contain(md.key)
    jd.containers.map(_.imageId) should contain(cl.imageId)
  }

  override def afterAll(): Unit = {
    testKit.stop()
    super.afterAll()
  }
}

object JobDefinitionServiceIntegrationSpec {
  val uid = s"com.test_company@calculus@1.2.3"

  val jd = new JobDefinition(uid, "test123", "test@test.com",
    "test", "test", "test", None, true, "test",
    "data", "data", "last", Seq.empty, Seq.empty)

}
