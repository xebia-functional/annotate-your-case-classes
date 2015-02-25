package com.fortysevendeg.examples

import com.fortysevendeg.macros.ToStringObfuscate

object Obfuscated {

  @ToStringObfuscate("password", "pinCode")
  case class TestObfuscatePassword(name: String, username: String, password: String, pinCode: String)

  @ToStringObfuscate("cardNumber")
  case class TestObfuscateCreditCard(cardNumber: String, cvv: Int, endDate: String)

  @ToStringObfuscate("password")
  case class UserPassword(username: String, password: String)

}

object NonObfuscated {

  case class UserPassword(username: String, password: String)

}
