package com.example.utils

import spray.httpx.unmarshalling.Unmarshaller
import com.example.domain.Tweet
import spray.http.HttpEntity
import spray.httpx.unmarshalling.Deserialized
import com.example.domain.Tweet
import spray.json._
import spray.httpx.unmarshalling.MalformedContent
import scala.util.Try

trait TweetUnmarshaller {

  implicit object TweetUnmarshaller extends Unmarshaller[Tweet] {

    def apply(entity: HttpEntity): Deserialized[Tweet] = {
      try {
        val json = JsonParser(entity.asString).asJsObject
        (json.fields.get("id_str"), json.fields.get("text")) match {
          case (Some(JsString(id)), Some(JsString(text))) =>
            Right(Tweet(id, text))

          case _ => Left(MalformedContent("could not parse json"))
        }
      } catch {
        case t: Throwable => Left(MalformedContent(entity.asString, t))
      }

    }

  }

}