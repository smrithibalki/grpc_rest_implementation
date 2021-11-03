

/**
 * Client program for invoking the `CalculatorFunctionRest` lambda function on AWS that uses JSON as the data
 * interchange format.
 *
 * @param url URL for the API Gateway that triggers the lambda function.
 */
import HelperUtils.CreateLogger
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http
import com.twitter.util.{Await, Future}
import org.slf4j.{Logger, LoggerFactory}

import java.time.LocalTime

//import system.dispatcher

class REST_Finagle(url : String)  {

  //val logger: Logger = LoggerFactory.getLogger(getClass)
  val logger = CreateLogger(classOf[REST_Finagle])
    def rest_evaluate(LB :LocalTime,UB:LocalTime) {

      val client: Service[http.Request, http.Response] = Http.client.withTls("f1bmo1rvph.execute-api.us-east-2.amazonaws.com").newService(s"f1bmo1rvph.execute-api.us-east-2.amazonaws.com:443")
      val request = http.Request(url+"?lower_interval="+LB+"&upper_interval="+UB)
      //request.host = "www.scala-lang.org"
      val response: Future[http.Response] = client(request)
      Await.result(response.onSuccess { rep: http.Response => logger.info("GET success -- Printing the Hash codes: " + rep.contentString)
      })

    }


}