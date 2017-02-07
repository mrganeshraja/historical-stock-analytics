import java.time.LocalDate

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.Source

object HistoricalStock {

  val closeCol = 4

  def pricesURL(ticker: String)(implicit pastdate: LocalDate = LocalDate.now.minusYears(1)) = {
    val today = LocalDate.now()
    f"http://real-chart.finance.yahoo.com/table.csv?s=$ticker" +
         f"&a=${pastdate.getMonthValue}&b=${pastdate.getDayOfMonth}&c=${pastdate.getYear}" +
         f"&d=${today.getMonthValue}&e=${today.getDayOfMonth}&f=${today.getYear}" +
         "&g=d&ignore=.csv".stripMargin
  }

  def dailyPrices(ticker: String): List[Double] = {
    var list: ListBuffer[Double] = mutable.ListBuffer()
    for (line <- Source.fromURL(pricesURL(ticker)).getLines().drop(1)) {
      list += line.mkString.split(",")(closeCol).toDouble
    }
    list.toList
  }

  def returns(ticker: String): Seq[Double] = {
    val dailyReturn: (Double, Double) => Double =
      (today: Double, yesterday: Double) => { today - yesterday / yesterday }
    dailyPrices(ticker).sliding(2).collect { case (b) => dailyReturn.apply(b.head, b(1)) }.toSeq
  }

  def meanReturn(ticker: String): Double = {
    val yearReturnsx: Seq[Double] = returns(ticker)
    yearReturnsx.map(_.toDouble).sum / yearReturnsx.length
  }

  def main(args: Array[String]) {

   var googleDailyPrices = dailyPrices("GOOG")
   var googleDailyReturns = dailyPrices("GOOG")
   var googleAverageRetuns = dailyPrices("GOOG")

  }

}
