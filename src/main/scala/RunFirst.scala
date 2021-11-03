
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
 * Main client program that takes inputs from the configuration file and evaluates the result by invoking AWS Lambda
 * functions.
 *
 * The program invokes 2 Lambda functions in a sequential order:
 * (0) GRPC_Lambda  : Creates a GRPCClient that invokes lambda function,an API is used for making gRPC calls.
 * (1) REST_Finagle : Create a REST Client on Twitter Framework,uses URL of the API Gateway that invokes the lambda function .
 *
 * These arguments are by default picked up from the typesafe config files.Can be modified in the config file
 */
object RunFirst extends App {

  // Load Configuration settings

  val logger = CreateLogger(classOf[RunFirst.type])
  val conf: Config = ConfigFactory.load("application.conf")
  /*
    grpc_url --> API gateway endpoint to pass the protobuf object via GRPC, to invoke the lambda function
    rest_url --> API gateway endpoint to invoke lambda function via REST API calls
  */
  val grpc_url :String = conf.getString("homework3.grpc")
  val rest_url :String = conf.getString("homework3.rest")
  val length = conf.getString("homework3.length")

  /* To check the pattern of input given by the user for timestamp*/
  val timestamp_pattern = conf.getString("homework3.t_pattern")
  logger.info("--- Starting The Execution")

  /* time -> TimeStamp the client wants to check if present in the log file
     dt --> Time Interval given in minutes by the user for the timeinterval
     These two values can be modified in the configuration file
   */
  val time = conf.getString("homework3.timestamp")
  logger.info("--- Taking the time stamp value from config file",time)
  val dt = conf.getInt("homework3.time_interval")
  logger.info("--- Taking the time interval in minutes value from config file",dt)

  /* Parse the input time string in DateTime format*/
  val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern(timestamp_pattern)
  val t = LocalTime.parse(time, dtf)

  /* upper_interval and lower_interval --> Computed Timestamps by the time_interval specified
  Example : time_interval = 00:02:00, timestamp = 09:56:55
            upper_interval = 09:58:55 , lower_interval = 09:55:55
   */
  val upper_interval = t.plusMinutes(dt+1) // to stop the search when the minute is exceeded
  val lower_interval = t.minusMinutes(dt)

  // Make an Input protobuf and evaluate the result
  val client = new GRPCClient(grpc_url)
  // GRPCClient is created which takes the api gateway endpoint to parse the protobuf
  val result = client.evaluate(Input(time = time,dt = length))
  logger.info(s"\nResult = $result")
  if (result == true) {
    /* Only when the GRPCClient returns a True value(when TimeStamp is present)
       RESTCLient invokes the second lambda function to return the log messages present on Finagle Framework
     */
    logger.info("Executing RESTAPI with Finagle HTTP Framework")
    //REST_Finagle is created with api gateway to invoke the lambda function
    val client_finagle = new REST_Finagle(rest_url)
    val rest_finagle = client_finagle.rest_evaluate(lower_interval, upper_interval)
    logger.info("Completed Execution with Finagle Framework")
  }
  else {
        logger.info("The given Timestamp is not present")
      }

}
