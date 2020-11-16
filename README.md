Here are the automation tests required by DINS company to apply for the position of an intern (QA Automation) at DINS company.

*How to run the tests.*

## Installation guide
 * Supposed you already have Adopt Open JDK 11 or above.
 * [Download](https://www.jetbrains.com/ru-ru/idea/download/#section=windows) Intellij IDEA from the official website.
 * Clone this repository to your local machine. You can use [this](https://www.youtube.com/watch?v=aBVOAnygcZw&ab_channel=IntelliJIDEAbyJetBrains) guide.
 * Or you can create a new Spring Boot Maven project on your local machine and copy the dependencies from [this](https://github.com/InalDJ/DINS-automation-test/blob/master/pom.xml) pom.xml into your pom.xml. Then, create a class "TestingClass.java" in the following directory: src\test\java\com\dinstest\TestingClass.java. Copy the code from [this](https://github.com/InalDJ/DINS-automation-test/blob/master/src/test/java/com/dinstest/TestingClass.java) page into your newly created test class.
 
 
 ## How to run tests
 * After the repository has been cloned or the code copied, in Intellij IDEA go to: Run> Edit Configurations...  Click "TestNg" and "+" button
 ![Image](https://user-images.githubusercontent.com/65347205/99298164-57321600-285a-11eb-9a5e-c29b6f99dd11.png)
 * Then a drop-down list will appear. Click "TestNg" again.
 ![image](https://user-images.githubusercontent.com/65347205/99299143-dc69fa80-285b-11eb-8e1e-43bdef026e8a.png)
 * Then another window will appear. Set it up according to the following example. Choose a class according to your directory:
 ![image](https://user-images.githubusercontent.com/65347205/99299035-af1d4c80-285b-11eb-8072-96ed615ad1ab.png)
 * Click "Apply" and then "OK".
 * Now you can start tests. Activate your newly created configuration in the drop-down list:
 ![image](https://user-images.githubusercontent.com/65347205/99299466-4da9ad80-285c-11eb-9463-43b54f0ba872.png)
 * Click Run> Run 'TestingClass'...
 * After the tests have been executed, you can open a newly generated test report. It will be saved to this directory: target\TestReport.html
 ![image](https://user-images.githubusercontent.com/65347205/99299872-eb04e180-285c-11eb-8296-521191e72f68.png)
