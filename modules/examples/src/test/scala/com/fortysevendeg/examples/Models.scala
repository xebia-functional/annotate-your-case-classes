package com.fortysevendeg.examples

import com.fortysevendeg.macros.ToStringObfuscate

@ToStringObfuscate("password", "pinCode")
case class TestObfuscatePassword(name: String, username : String, password : String, pinCode: String)

@ToStringObfuscate("cardNumber")
case class TestObfuscateCreditCard(cardNumber: String, cvv : Int, endDate: String)

@ToStringObfuscate("password")
case class TestWithObfuscation(username : String, password : String)

case class TestWithoutObfuscation(username : String, password : String)