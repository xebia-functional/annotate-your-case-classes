package com.fortysevendeg.examples

import com.fortysevendeg.macros.Obfuscate

@Obfuscate("password", "pinCode")
case class TestObfuscatePassword(name: String, username : String, password : String, pinCode: String)

@Obfuscate("cardNumber")
case class TestObfuscateCreditCard(cardNumber: String, cvv : Int, endDate: String)

@Obfuscate("password")
case class TestWithObfuscation(username : String, password : String)

case class TestWithoutObfuscation(username : String, password : String)