package com.example

import akka.actor.Actor
import akka.actor.ActorLogging
import spray.can.Http
import spray.http.Uri
import akka.io.IO
import akka.pattern.ask
import spray.http.HttpEntity
import spray.http.ContentType
import spray.http.MediaTypes
import spray.http.HttpRequest
import spray.http.HttpMethods
import spray.http.HttpResponse
import spray.http.ChunkedResponseStart
import spray.http.MessageChunk

class TestActor extends Actor with ActorLogging {
  import TestActor._

  val twitterUri = Uri("https://stream.twitter.com/1.1/statuses/filter.json")
  val googleUri = Uri("http://104.131.75.136:5000/sentiment/iphone6/last/3")

  val io = IO(Http)(context.system)

  def receive = {
    case Start(query: String) => {
      log.info("start message accepted")
      val body = HttpEntity(ContentType(MediaTypes.`application/x-www-form-urlencoded`), s"track=$query")
      val rq = HttpRequest(HttpMethods.GET, uri = googleUri)
      io ! rq
    }
    case Stop => log.info("stop message accepted")
    case ChunkedResponseStart(_) =>
    case MessageChunk(entity, _) => log.info(entity toString)
    case r: HttpResponse => handleResponse(r)
    case _ => log.info("unknown message")
  }

  def handleResponse(r: HttpResponse): Unit = {
    val entity = r.entity
    log.info(entity.data toString)
  }
}

object TestActor {
  case class Start(val query: String)
  case object Stop
}