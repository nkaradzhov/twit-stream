package com.example

import akka.actor.Actor
import akka.actor.ActorLogging
import spray.http.HttpData
import com.example.utils.TweetUnmarshaller
import spray.httpx.unmarshalling.Deserialized
import com.example.domain.Tweet
import scala.collection.mutable.StringBuilder

class UnmarshallerActor extends Actor with ActorLogging with TweetUnmarshaller {

  var acumulated = new StringBuilder

  def receive = {
    case data: HttpData => {
      val str = data.asString
      acumulated.append(str)
      if (str.endsWith("\r\n")) {
        //end of tweet, so reset
        val tw: Deserialized[Tweet] = TweetUnmarshaller(acumulated.toString())
        tw.right.map { tweet => log.info(tweet.toString) }
        tw.left.map { err => log.info(err.toString) }
        acumulated = new StringBuilder
      }
    }
  }
}