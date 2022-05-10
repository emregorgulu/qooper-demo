 ![enter image description here](https://www.qooper.io/hs-fs/hubfs/Qooper%20main%20logo.png?width=230&height=80&name=Qooper%20main%20logo.png)

A demo test automation scenario was written on qooper.io. The project is compiled on maven in Java version 1.8.

**Technology Used**

-   Selenium
-   Maven
-   Chrome Driver
-   Java
-   Cucumber
----------

**Required IDE Plugins**

- Cucumber for Java
- Cucumber Scenario Indexer
- Gherkin

>  **Note:** Added Mac M1 Chrome 101 and Windows Chrome 101 drivers. It will be enough to run it on one of the 2 devices. If it will be run on Intel or another Chrome version for Mac, it is sufficient to add the appropriate driver with the name "chromedriver_mac" to the "/qooper-demo-main/properties/driver" path.
----------
**Demo Scenario**

Feature: Qooper Demo
  
>@Login  
> @QooperWrongEmailLogin  
Scenario: Qooper Wrong Email Login  
>  * Check 'Homepage Logo' object visible.
>  * Navigate to "https://platform.qooper.io/signIn" url.
>  * The value "emre@testqooper.com" is written in the EMAIL field.
>  * The value "123456" is written in the PASSWORD field.
>  * Click 'SIGN-IN' object.
>  * Check SIGN-IN-MESSAGE object contains "The email or password entered is invalid." value.
>  * Favori listesinde 'URUNADI' parametresi ile saklanan ürün adının var olmadığı kontrol edilir.

>  **Note:**The scenario below was written without adding selectors(id,css,xpath etc) because the pages cannot be accessed. If missing elements are added to "/mapJSON/element.json" it should work.

>@MentorTab  
> @CheckMentoringTabWhenMenteePeerMatchingEnabled  
Scenario: Check Mentoring Tab When Mentee Peer Matching Enabled
> * Navigate to "https://staging.mentoring.qooper.io/" url.
> * The value "menteeprofilepeermatching@testqooper.com" is written in the EMAIL field.
> * The value "123456" is written in the PASSWORD field.
> * Check 'Mentor Platform Home Icon' object visible.
> * Click 'Mentoring' object.
> * Check 'Mentors' object visible.
> * Check 'Mentees' object is not visible.
> * Check 'Peer' object visible.
> * Check 'Find More Mentors' object is not visible.
----------
