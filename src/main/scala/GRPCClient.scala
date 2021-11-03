import HelperUtils.CreateLogger

import java.util.Base64
import java.nio.charset.StandardCharsets
import com.smrithi.protobuf.hello.{Input, Response}
import com.typesafe.scalalogging.LazyLogging
import scalaj.http.{Http, HttpRequest}

import java.util.Base64

/**
 * Client program for invoking the `Grpc` lambda function on AWS that uses Protobuf as the data
 * interchange format.
 *
 * @param url URL for the API Gateway that triggers the lambda function.
 */
class GRPCClient(url: String) {

  val logger = CreateLogger(classOf[GRPCClient])

   def evaluate(expression: Input): Boolean = {
     /**
      * Input -> Protobuf with Request and Response objects
      * Returns -> Boolean (True --> if the TimeStamp is present else False)
      */
    //logger.trace(s"calculate(expression: $expression")
    // Make POST request to API Gateway
    val request = Http(url)
      .headers(Map(
        "Content-Type" -> "application/grpc+proto",
        "Accept" -> "application/grpc+proto"
      ))
      .timeout(connTimeoutMs = 2000, readTimeoutMs = 10000) // So that request doesn't time out for Lambda cold starts
      .postData(expression.toByteArray)
    // The input is converted to ByteArray and sent as a POST
    //logger.debug(s"Making HTTP request: $request")
    val response = request.asBytes
    /*The response is parsed from body of the response from lambda function and sent to
    protobuf response object to get the result
     */
    val responseMessage : Response = Response.parseFrom(response.body)
    //The result received in protobuf specified format
    responseMessage.result
  }
}