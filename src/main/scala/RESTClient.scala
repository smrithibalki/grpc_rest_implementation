import com.typesafe.config.{Config, ConfigFactory}
//import com.typesafe.scalalogging.Logger
import org.slf4j.{Logger, LoggerFactory}

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import akka.actor.ActorSystem
import akka.http.scaladsl.client.RequestBuilding.Get
import com.typesafe.config.{Config, ConfigFactory}

import scala.language.postfixOps

//import akka.actor.TypedActor.dispatcher
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpResponse, HttpRequest}
import akka.stream.ActorMaterializer

import scala.concurrent.duration._
import scala.concurrent.{Future,Await}
import java.time.LocalTime


/**
 * Client program for invoking the `CalculatorFunctionRest` lambda function on AWS that uses JSON as the data
 * interchange format.
 *
 * @param url URL for the API Gateway that triggers the lambda function.
 */
//import com.twitter.finagle.{Http, Service}
//import com.twitter.finagle.http
//import com.twitter.util.{Await, Future}
//import system.dispatcher

object RESTClient {
  val logger: Logger = LoggerFactory.getLogger(getClass)

  val conf: Config = ConfigFactory.load("application.conf")

  val timestamp_pattern = conf.getString("homework3.t_pattern")
  logger.info("--- Starting The Execution")
  val time = conf.getString("homework3.timestamp")
  logger.info("--- Taking the time stamp value from config file",time)
  val dt = conf.getInt("homework3.time_interval")
  logger.info("--- Taking the time interval in minutes value from config file",dt)


  val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern(timestamp_pattern)
  //val interval = LocalTime.parse("00:13:00", dtf)
  val t = LocalTime.parse(time, dtf)

  val UB = t.plusMinutes(dt+1)
  val LB = t.minusMinutes(dt)
//  def rest_evaluate(time:String,dt:String,pattern:String) {
//   // print(url+"?"+time+","+dt+","+pattern)
//    val client: Service[http.Request, http.Response] = Http.client.withTls("ttavy7jplj.execute-api.us-east-2.amazonaws.com").newService(s"ttavy7jplj.execute-api.us-east-2.amazonaws.com:443")
//    val request = http.Request(url+"?"+time+","+dt+","+pattern)
//    //request.host = "www.scala-lang.org"
//    val response: Future[http.Response] = client(request)
//    Await.result(response.onSuccess { rep: http.Response => println("GET success: " + rep.contentString) })
//
//
//  }
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
implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  import system.dispatcher

  def rest_evaluate(LB :LocalTime,UB:LocalTime): Future[String] = {
    //    val responseFuture: Future[HttpResponse] = Http().singleRequest(request)
    val responseFuture: Future[HttpResponse] = Http().singleRequest(Get("https://f1bmo1rvph.execute-api.us-east-2.amazonaws.com/default/rest_function?lower_interval="+LB+"&upper_interval="+UB))
    val entityFuture: Future[HttpEntity.Strict] = responseFuture.flatMap(response => response.entity.toStrict(2.seconds))
    entityFuture.map(entity => entity.data.utf8String)



  }

  def main(args: Array[String]): Unit ={
    val res = Await.result(rest_evaluate(LB :LocalTime,UB:LocalTime),2000 millis)
    println(res)
  }


}