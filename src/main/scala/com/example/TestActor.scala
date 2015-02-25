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
import com.example.utils.TweetUnmarshaller
import com.example.utils.OAuthTwitterAuthorization
import spray.http.HttpData
import spray.httpx.unmarshalling.Deserialized
import com.example.domain.Tweet
import spray.http.ChunkedMessageEnd
import spray.httpx.unmarshalling.MalformedContent

class TestActor extends Actor with ActorLogging with TweetUnmarshaller with OAuthTwitterAuthorization {
  import TestActor._

  val twitterUri = Uri("https://stream.twitter.com/1.1/statuses/filter.json")

  val io = IO(Http)(context.system)

  def receive = {
    case Start(query: String)    => sendRequest(query)
    case Stop                    => log.info("stop message accepted")
    case ChunkedResponseStart(_) => log.info("chunk response start")
    case MessageChunk(entity, _) => handleChunk1(entity)
    case ChunkedMessageEnd       => log.info("chunk message end")
    case r: HttpResponse         => handleResponse(r)
    case _                       => log.info("unknown message")
  }
  
  private def handleChunk1(data: HttpData) {
    log.info(data.asString)
    log.info(data.asString.endsWith("\r\n").toString)
  }

  private def handleChunk(data: HttpData) = {
    val tweet: Deserialized[Tweet] = TweetUnmarshaller(data)
    tweet match {
      case Right(t: Tweet) => log.info(t.text)
      case Left(MalformedContent(_,t))         => {
        t.map { x => x.printStackTrace() }
      }
    }
    log.info(data toString)
  }

  private def sendRequest(query: String): Unit = {
    log.info("start message accepted") 
    val body = HttpEntity(ContentType(MediaTypes.`application/x-www-form-urlencoded`), s"track=${query}&language=en")
    val rq = authorize(HttpRequest(HttpMethods.POST, uri = twitterUri, entity = body))
    io ! rq
  }

  private def handleResponse(r: HttpResponse): Unit = {
    log.info("handling response")
  }
}

object TestActor {
  case class Start(val query: String)
  case object Stop
}