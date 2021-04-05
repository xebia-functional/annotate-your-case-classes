package com.fortysevendeg.examples

import java.util.UUID
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import scala.util.Random
import com.fortysevendeg.examples.Obfuscated._

class ObfuscateSpec extends AnyWordSpec with Matchers {

  def generateUUID = UUID.randomUUID().toString

  def obfuscateAlgorithmUsedInMacro(value: String) = "*" * value.length

  "`toString` method on TestObfuscatePassword case class obfuscates password and pinCode fields" in {

    val name = generateUUID
    val username = generateUUID
    val password = generateUUID
    val pinCode = generateUUID

    val obfuscatedInstance = TestPassword(
      name = name,
      username = username,
      password = password,
      pinCode = pinCode
    )

    val nonObfuscatedInstance = NonObfuscated.TestPassword(
      name = name,
      username = username,
      password = obfuscateAlgorithmUsedInMacro(password),
      pinCode = obfuscateAlgorithmUsedInMacro(pinCode)
    )

    val obfuscatedToString = obfuscatedInstance.toString
    val nonObfuscatedToString = nonObfuscatedInstance.toString

    obfuscatedToString shouldEqual nonObfuscatedToString
  }

  "`toString` method on TestObfuscateCreditCard case class obfuscates the cardNumber field" in {

    val cardNumber = generateUUID
    val cvv = Random.nextInt(1000)
    val endDate = generateUUID

    val obfuscatedInstance = TestCreditCard(
      cardNumber = cardNumber,
      cvv = cvv,
      endDate = endDate
    )

    val nonObfuscatedInstance = NonObfuscated.TestCreditCard(
      cardNumber = obfuscateAlgorithmUsedInMacro(cardNumber),
      cvv = cvv,
      endDate = endDate
    )

    val obfuscatedToString = obfuscatedInstance.toString
    val nonObfuscatedToString = nonObfuscatedInstance.toString

    obfuscatedToString shouldEqual nonObfuscatedToString
  }

  "`toString` method in identical case classes are different when one of them obfuscate one of his fields" in {

    val username = generateUUID
    val password = generateUUID

    val obfuscatedInstance = UserPassword(
      username = username,
      password = password
    )

    val nonObfuscatedInstance = NonObfuscated.UserPassword(
      username = username,
      password = password
    )

    obfuscatedInstance.toString should not equal nonObfuscatedInstance.toString
  }
}
