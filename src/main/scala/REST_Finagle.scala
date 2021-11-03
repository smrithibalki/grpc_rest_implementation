//import akka.actor.ActorSystem
//import akka.actor.ActorSystem
//import akka.http.scaladsl.Http
//import akka.http.scaladsl.model._
//import akka.stream.scaladsl._
//import akka.http.scaladsl.Http
//import akka.http.scaladsl.model._

//import scala.concurrent.Future
//import scala.concurrent.Future
//import scala.util.{ Failure, Success }
//import akka.actor.TypedActor.dispatcher
//import akka.http.javadsl.server.Directives.entity
//import akka.http.scaladsl.Http
//import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpRequest}
//import akka.http.scaladsl.server.Directives.entity
//import akka.stream.ActorMaterializer
//import com.google.protobuf.duration.Duration.defaultInstance.seconds
//import com.typesafe.scalalogging.LazyLogging

//import java.net.URLEncoder
//import scala.concurrent.Future
//import scala.concurrent.duration.DurationInt
//import scala.language.postfixOps
////import scalaj.http.Http
//import com.google.gson.Gson
//import scalapb.json4s.JsonFormat
//import java.util
//import akka.actor.ActorSystem
//import akka.http.scaladsl.client.RequestBuilding.Get
//
//import scala.language.postfixOps
//
////import akka.actor.TypedActor.dispatcher
//import akka.http.scaladsl.Http
//import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpResponse, HttpRequest}
//import akka.stream.ActorMaterializer
//
//import scala.concurrent.duration._
//import scala.concurrent.{Future,Await}
import java.time.LocalTime


/**
 * Client program for invoking the `CalculatorFunctionRest` lambda function on AWS that uses JSON as the data
 * interchange format.
 *
 * @param url URL for the API Gateway that triggers the lambda function.
 */
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http
import com.twitter.util.{Await, Future}
//import system.dispatcher

class REST_Finagle(url : String)  {
    def rest_evaluate(LB :LocalTime,UB:LocalTime) {
     // print(url+"?"+time+","+dt+","+pattern)
      val client: Service[http.Request, http.Response] = Http.client.withTls("f1bmo1rvph.execute-api.us-east-2.amazonaws.com").newService(s"f1bmo1rvph.execute-api.us-east-2.amazonaws.com:443")
      val request = http.Request(url+"?lower_interval="+LB+"&upper_interval="+UB)
      //request.host = "www.scala-lang.org"
      val response: Future[http.Response] = client(request)
      Await.result(response.onSuccess { rep: http.Response => println("GET success: " + rep.contentString) })

    }
  //def rest_evaluate(myCode: String): Future[String] = {
  //
  //  implicit val system = ActorSystem()
  //  implicit val materializer = ActorMaterializer()
  //  val responseFuture = Http().singleRequest(
  //      HttpRequest(
  //        method = HttpMethods.POST,
  //        //uri = "http://markup.su/api/highlighter",
  //        uri = url,
  //        entity = HttpEntity(
  //          ContentTypes.`application/x-www-form-urlencoded`,
  //          s"source=${URLEncoder.encode(myCode.trim, "UTF-8")}&language=Scala&theme=Sunburst"
  //        )
  //      )
  //  )
  ////  print(responseFuture)
  //  responseFuture
  //    .flatMap(_.entity.toStrict(2 seconds))
  //    .map(_.data.utf8String).foreach(println)
  //  responseFuture
  //    .flatMap(_.entity.toStrict(2 seconds))
  //    .map(_.data.utf8String)
  //}
//  implicit val system: ActorSystem = ActorSystem()
//  implicit val materializer: ActorMaterializer = ActorMaterializer()
//  import system.dispatcher
//
//  def sendRequest(LB :LocalTime,UB:LocalTime,pattern:String): Future[String] = {
//    //    val responseFuture: Future[HttpResponse] = Http().singleRequest(request)
//    val responseFuture: Future[HttpResponse] = Http().singleRequest(Get("https://f1bmo1rvph.execute-api.us-east-2.amazonaws.com/default/rest_function?lower_interval="+LB+"&upper_interval="+UB+"&pattern="+pattern))
//    val entityFuture: Future[HttpEntity.Strict] = responseFuture.flatMap(response => response.entity.toStrict(2.seconds))
//    entityFuture.map(entity => entity.data.utf8String)
//
//
//  }
//
//  def rest_evaluate(LB :LocalTime,UB:LocalTime,pattern:String): String ={
//    val res = Await.result(sendRequest(LB :LocalTime,UB:LocalTime,pattern:String),5000 millis)
//    res
//  }


}