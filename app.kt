import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

const val WEBDRIVER_PROPERTY_KEY = "webdriver.gecko.driver"
const val WEBDRIVER_PROPERTY_VALUE = "/opt/bin/geckodriver"

const val PAGE_URL = "https://www.amazon.com/gp/product/<add_ASIN>"
const val ELEMENT_ID = "priceblock_ourprice"

const val PERIOD:Long = 30000 // 30s

fun main() {
    System.setProperty(WEBDRIVER_PROPERTY_KEY, WEBDRIVER_PROPERTY_VALUE)

    val driver = FirefoxDriver()
    driver.get(PAGE_URL)

    val timer = Timer()
    timer.scheduleAtFixedRate(object : TimerTask() {
        override fun run() {
            val dateTime = LocalDateTime.now()
            var formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("M/d/y H:m:ss"))

            if (driver.findElements(By.id(ELEMENT_ID)).size == 0) {
                println("Sold out - $formattedDateTime")
            } else {
                val price = driver.findElement(By.id(ELEMENT_ID)).text
                println("Price: $price - $formattedDateTime")

                // Send an Email or SMS, e.g. using Twilio
            }

            driver.navigate().refresh()
        }
    }, 1000, PERIOD)
}
