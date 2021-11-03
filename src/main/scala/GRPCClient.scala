import java.util.Base64
import java.nio.charset.StandardCharsets
import com.smrithi.protobuf.hello.{Input, Response}
import com.typesafe.scalalogging.LazyLogging
import scalaj.http.{Http, HttpRequest}

import java.util.Base64

/**
 * Client program for invoking the `CalculatorFunctionGrpc` lambda function on AWS that uses Protobuf as the data
 * interchange format.
 *
 * @param url URL for the API Gateway that triggers the lambda function.
 */
class GRPCClient(url: String) {
//  implicit val system = ActorSystem()
//  implicit val materializer = ActorMaterializer()
  print(url)
   def evaluate(expression: Input): Boolean = {
    //logger.trace(s"calculate(expression: $expression")
    // Make POST request to calculator API Gateway
    val request = Http(url)
      .headers(Map(
        "Content-Type" -> "application/grpc+proto",
        "Accept" -> "application/grpc+proto"
      ))
      .timeout(connTimeoutMs = 2000, readTimeoutMs = 10000) // So that request doesn't time out for Lambda cold starts
      .postData(expression.toByteArray)
     println(expression.toByteArray)
    //logger.debug(s"Making HTTP request: $request")
    val response = request.asBytes

    val responseMessage : Response = Response.parseFrom(response.body)

    responseMessage.result
  }
}