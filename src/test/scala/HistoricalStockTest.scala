import java.net.URL

import org.scalatest.FlatSpec

class HistoricalStockTest extends FlatSpec {

  behavior of "HistoricalStock"

  it should "price url should be well formed without malformed exception" in {
    val pricesURL: String = HistoricalStock.pricesURL("GOOG")
    val url = new URL(pricesURL)
    assert(url != null)
  }

  it should "price url should open connection to server host" in {
    val pricesURL: String = HistoricalStock.pricesURL("GOOG")
    val connection = new URL(pricesURL).openConnection();
    assert(connection != null)
  }

  it should "should retieve daily stock prices of given ticker for a year" in {
    val dailyPrices = HistoricalStock.dailyPrices("GOOG")
    assert(dailyPrices.size > 200)
  }

  it should "should retieve returns stock prices of given ticker for a year" in {
    val returns = HistoricalStock.returns("GOOG")
    assert(returns.size  > 200)
  }

  it should "should return mean value return of a given ticker for a year" in {
    val meanReturn = HistoricalStock.meanReturn("GOOG")
    assert(meanReturn == 1)
  }
}
