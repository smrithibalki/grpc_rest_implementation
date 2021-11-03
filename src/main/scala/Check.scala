
import HelperUtils.CreateLogger
import com.smrithi.protobuf.hello.Input
import com.typesafe.config.{Config, ConfigFactory}
//import com.typesafe.scalalogging.Logger
import org.slf4j.{Logger, LoggerFactory}
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import scala.io.StdIn
import scala.math.Ordered.orderingToOrdered

/**
 * Main Calculator client program that takes inputs from the user and evaluates the result by invoking AWS Lambda
 * functions.
 *
 * The program accepts the below two arguments in that order:
 * (0) api-type:         Either "grpc" or "rest" to specify which type of API to use for making gRPC calls.
 * (1) api-gateway-url:  The URL of the API Gateway that invokes the lambda function for the specified `api-type`.
 *
 * If these arguments are not specified, default values will be picked up from the typesafe config files.
 */
object Check extends App {

  // Load application settings
  //val logger: Logger = LoggerFactory.getLogger(getClass)
  val logger = CreateLogger(classOf[Check.type ])
  val conf: Config = ConfigFactory.load("application.conf")
  val grpc_url :String = conf.getString("homework3.grpc")
  val rest_url :String = conf.getString("homework3.rest")
  val length = conf.getString("homework3.length")
  //val pattern = conf.getString("homework3.pattern")
  val timestamp_pattern = conf.getString("homework3.t_pattern")
  logger.info("--- Starting The Execution")
  val time = conf.getString("homework3.timestamp")
  logger.info("--- Taking the time stamp value from config file",time)
  val dt = conf.getInt("homework3.time_interval")
  logger.info("--- Taking the time interval in minutes value from config file",dt)


  val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern(timestamp_pattern)
  val t = LocalTime.parse(time, dtf)

  val upper_interval = t.plusMinutes(dt+1)
  val lower_interval = t.minusMinutes(dt)
      //val length = "100"
      // Make an Expression protobuf and evaluate the result
  val client = new GRPCClient(grpc_url)
  val result = client.evaluate(Input(time = time,dt = length))
  logger.info(s"\nResult = $result")
  if (result == true) {

    logger.info("Executing RESTAPI with Finagle HTTP Framework")
    val client_finagle = new REST_Finagle(rest_url)
    val rest_finagle = client_finagle.rest_evaluate(lower_interval, upper_interval)
    logger.info("Completed Execution with Finagle Framework")
  }
  else {
        logger.info("The given Timestamp is not present")
      }

}
