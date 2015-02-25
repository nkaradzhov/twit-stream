package com.example.utils

import spray.http.HttpRequest
import scala.io.Source

/**
 * @author User
 */
trait TwitterAuthorization {
  def authorize: HttpRequest => HttpRequest
}

trait OAuthTwitterAuthorization extends TwitterAuthorization {
  import OAuth._
//  val home = System.getProperty("user.home")
//  val lines = Source.fromFile(s"$home/.twitter/activator").getLines().toList

  val consumer = Consumer("3URh7wJkMXOJD6BzFuoBwrcy9", "YFS0Ba6aMnzGdCgeaNHZBPWgh28zeQkB9rUcXJgb5A34gXe38e")
  val token = Token("2301821670-JSPSWSDZJ0rj5LyfU33PMVx1SjZFfQfq09nZb4q", "ZPmmS6WyyoDXHgSMfyCTg7kXd9td35eGLozTHJQRU4pbb")

  val authorize: (HttpRequest) => HttpRequest = oAuthAuthorizer(consumer, token)
} 