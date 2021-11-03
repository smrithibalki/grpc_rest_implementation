import HelperUtils.CreateLogger
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
 * Client program for invoking the `Rest` lambda function on AWS that uses JSON as the data
 * interchange format.
 * Framework used is AKKA HTTP Framework
 * @param url URL for the API Gateway that triggers the lambda function.
 */


object RESTClient_AKKA {

  val logger = CreateLogger(classOf[RESTClient_AKKA.type])
  val conf: Config = ConfigFactory.load("application.conf")

  val url = conf.getString("homework3.rest")
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

implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  import system.dispatcher

  def rest_evaluate(LB :LocalTime,UB:LocalTime): Future[String] = {
    /**
     * Input -> Lower and Upper Interval of given Timestamp is given
     * Returns -> MD5 generated hash codes if the log messages are present else 404 statuscode
     */
    //    val responseFuture: Future[HttpResponse] = Http().singleRequest(request)
    val responseFuture: Future[HttpResponse] = Http().singleRequest(Get(url+"?lower_interval="+LB+"&upper_interval="+UB))
    val entityFuture: Future[HttpEntity.Strict] = responseFuture.flatMap(response => response.entity.toStrict(2.seconds))
    entityFuture.map(entity => entity.data.utf8String)
  }

  def main(args: Array[String]): Unit ={
    val res = Await.result(rest_evaluate(LB :LocalTime,UB:LocalTime),2000 millis)
    logger.info("Printing the Result (Hash codes / 404)")
    logger.info(res)
  }


}