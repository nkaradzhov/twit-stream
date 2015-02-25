package com.example

import akka.actor.Actor
import akka.actor.ActorLogging
import spray.http.HttpData

class UnmarshallerActor extends Actor with ActorLogging {

  var acumulated = new StringBuilder

  def receive = {
    case data: HttpData => {
      val str = data.asString
      acumulated.append(str)
      if (str.endsWith("\r\n")) {
        //TODO send full json
      } else {
      }
    }
  }
}