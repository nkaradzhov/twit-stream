package com.example

import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.ActorLogging
import scala.util.{Success, Failure}
import scala.util.Failure

object ApplicationMain extends App {
  val system = ActorSystem("MyActorSystem")
  val testActor = system.actorOf(Props[TestActor])
  
  import TestActor._
  testActor ! Start("iphone")
  
}