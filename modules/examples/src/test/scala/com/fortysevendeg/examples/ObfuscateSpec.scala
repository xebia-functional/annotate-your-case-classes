package com.fortysevendeg.examples

import java.util.UUID

import org.scalatest.{Matchers, WordSpec}

import scala.util.Random

class ObfuscateSpec extends WordSpec with Matchers {

  def generateUUID = UUID.randomUUID().toString

  "`toString` method on TestObfuscatePassword case class obfuscates password and pinCode fields" in {

    val name = generateUUID
    val username = generateUUID
    val password = generateUUID
    val pinCode = generateUUID

    val testObfuscatePassword = TestObfuscatePassword(
      name = name,
      username = username,
      password = password,
      pinCode = pinCode
    )

    val obfuscatedToString = testObfuscatePassword.toString

    obfuscatedToString should contain(s"***${password.takeRight(4)}")
    obfuscatedToString should contain(s"***${pinCode.takeRight(4)}")
  }

  "`toString` method on TestObfuscateCreditCard case class obfuscates password and pinCode fields" in {

    val cardNumber = generateUUID
    val cvv = Random.nextInt(1000)
    val endDate = generateUUID

    val testObfuscatedCreditCard = TestObfuscateCreditCard(
      cardNumber = cardNumber,
      cvv = cvv,
      endDate = endDate
    )

    val obfuscatedToString = testObfuscatedCreditCard.toString

    obfuscatedToString should contain(s"***${cardNumber.takeRight(4)}")
  }

  "`toString` method in identical case classes are different when one of them obfuscate one of his fields" in {

    val username = generateUUID
    val password = generateUUID

    val obfuscatedInstance = TestWithObfuscation(
      username = username,
      password = password
    )

    val nonObfuscatedInstance = TestWithoutObfuscation(
      username = username,
      password = password
    )

    obfuscatedInstance.toString should not equal nonObfuscatedInstance.toString
  }
}