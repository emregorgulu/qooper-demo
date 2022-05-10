Feature: Qooper Demo

  @Login
  @QooperWrongEmailLogin
  Scenario: Qooper Wrong Email Login
    * Check 'Homepage Logo' object visible.
    * Navigate to "https://platform.qooper.io/signIn" url.
    * The value "emre@testqooper.com" is written in the EMAIL field.
    * The value "123456" is written in the PASSWORD field.
    * Click 'SIGN-IN' object.
    * Check SIGN-IN-MESSAGE object contains "The email or password entered is invalid." value.


  @MentorTab
  @CheckMentoringTabWhenMenteePeerMatchingEnabled
  Scenario: Check Mentoring Tab When Mentee Peer Matching Enabled

    * Navigate to "https://staging.mentoring.qooper.io/" url.
    * The value "menteeprofilepeermatching@testqooper.com" is written in the EMAIL field.
    * The value "123456" is written in the PASSWORD field.
    * Check 'Mentor Platform Home Icon' object visible.
    * Click 'Mentoring' object.
    * Check 'Mentors' object visible.
    * Check 'Mentees' object is not visible.
    * Check 'Peer' object visible.
    * Check 'Find More Mentors' object is not visible.
    
    

