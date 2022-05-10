Feature: N11 Demo

  @Login
  @QooperWrongEmailLogin
  Scenario: Qooper Wrong Email Login
    * Check 'Homepage Logo' object visible.
    * Navigate to "https://platform.qooper.io/signIn" url.
    * The value "emre@testqooper.com" is written in the EMAIL field.
    * The value "123456" is written in the PASSWORD field.
    * Click 'SIGN-IN' object.
    * Check SIGN-IN-MESSAGE object contains "The email or password entered is invalid." value.

