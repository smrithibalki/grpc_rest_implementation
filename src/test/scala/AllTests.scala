import com.typesafe.config.ConfigFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AllTests extends AnyFlatSpec with Matchers {
  val config = ConfigFactory.load("application.conf")
  it should "Check for the timestamp pattern" in {
    val formatter_test = "HH:mm:ss"
    val pattern_test =config.getString("homework3.t_pattern")
    assert(formatter_test == pattern_test)

  }
  it should "Check for the timestamp " in {

    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val timestamp = LocalTime.parse(config.getString("homework3.timestamp"), formatter)
    val timestamp_test = LocalTime.parse("13:14:53", formatter)
    val timeinterval_test = 1

    val timeinterval = config.getInt("homework3.time_interval")
    assert(timestamp == timestamp_test)
    assert(timeinterval_test == timeinterval)

  }
  it should "Check for the timeinterval in minutes " in {

    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    val timeinterval_test = 1

    val timeinterval = config.getInt("homework3.time_interval")

    assert(timeinterval_test == timeinterval)

  }
  it should "Check for GRPC Endpoint" in {


    val grpc_url = config.getString("homework3.grpc")
    val grpc_url_test = "https://r4uuq1pr7d.execute-api.us-east-2.amazonaws.com/default/hw3"

    assert(grpc_url_test == grpc_url)

  }
}
class AllTestCases extends AnyFunSuite {
  test ( " Check for plusMinutes and minusMinutes to calculate time intervals"){
    val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val t = LocalTime.parse("00:13:00", dtf)

    val UB = t.plusMinutes(1)
    val UB_test = LocalTime.parse("00:14:00", dtf)
    val LB = t.minusMinutes(1)
    val LB_test = LocalTime.parse("00:12:00", dtf)

    assert(UB == UB_test)
    assert(LB == LB_test)

  }
  test ("Check for REST Endpoint" ) {
    val config = ConfigFactory.load("application.conf")

    val rest_url = config.getString("homework3.rest")
    val rest_url_test = "https://f1bmo1rvph.execute-api.us-east-2.amazonaws.com/default/rest_function"


    assert(rest_url_test == rest_url)


  }
}