package com.fortysevendeg.examples

import com.fortysevendeg.macros.ToStringObfuscate

object Obfuscated {

  @ToStringObfuscate("password", "pinCode")
  case class TestPassword(name: String, username: String, password: String, pinCode: String)

  @ToStringObfuscate("cardNumber")
  case class TestCreditCard(cardNumber: String, cvv: Int, endDate: String)

  @ToStringObfuscate("password")
  case class UserPassword(username: String, password: String)

}

object NonObfuscated {

  case class TestPassword(name: String, username: String, password: String, pinCode: String)

  case class TestCreditCard(cardNumber: String, cvv: Int, endDate: String)

  case class UserPassword(username: String, password: String)

}
