package com.dinstest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.RestAssured;
import org.json.simple.JSONObject;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


@Listeners({ ExtentITestListenerClassAdapter.class })
public class TestingClass {

    ExtentSparkReporter spark;
    ExtentReports extent;
    String testName;
    String testDescription;

@BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080";

        extent = new ExtentReports();
        //create a report (html file) in the following directory
        spark = new ExtentSparkReporter("target/TestReport.html");
        extent.attachReporter(spark);
    }


    @Test
    @SuppressWarnings("unchecked")
    public void should_CreateUser_WithoutSpecialCharacters_And_NullValues(){
        testName = "should_CreateUser_WithoutSpecialCharacters_And_NullValues()";
        testDescription = "This test should create a new user without special characters and null values in the fields: \"firstName\" and \"lastName\" (Positive)." +
                "<br> <i>User credentials: </i>" +
                "<br> - \"firstName\": \"Jesse\"," +
                "<br> - \"lastName\": \"Pinkman\"." +
                "<br> <i>Expected results if passed: </i> " +
                "<br> - HTTP response status code \"201\"." +
                "<br> - A new user with the aforementioned credentials is created in the database.";

        JSONObject user = new JSONObject();

        user.put("firstName", "Jesse");
        user.put("lastName", "Pinkman");

        //create a new user
        given()
                .header("Content-Type", "application/json")
                .body(user)
        .when()
                .post("/users")
        .then()
                .assertThat().statusCode(201);

        //check if the user exists
        when()
                .get("/users")
        .then()
                .assertThat()
                .body("firstName", hasItem("Jesse"))
                .body("lastName", hasItem("Pinkman"));

    }

    @Test(dependsOnMethods={"should_CreateUser_WithoutSpecialCharacters_And_NullValues"})
    @SuppressWarnings("unchecked")
    public void should_CreateContact_WithoutSpecialCharacters_And_NullValues(){

        testName = "should_CreateContact_WithoutSpecialCharacters_And_NullValues()";
        testDescription = "The following test should create a new contact" +
                " without special characters and null values in the fields: \"firstName\", \"lastName\", \"phone\" (Positive)." +
        "<br> <i>Contact data: </i>" +
                "<br> - \"firstName\": \"Walter\"," +
                "<br> - \"lastName\": \"White\"." +
                "<br> - \"phone\": \"1111111111\"." +
                "<br> - \"email\": \"heisenberg@meth.com\"." +
                "<br> <i>Expected results if passed: </i> " +
                "<br> - A new contact is created in the database by the user with ID - 3." +
                "<br> - HTTP response status code \"201\"." +
                "<br> - Contact with the aforementioned data exists in the database." +
                "<br> - HTTP response status code \"200\".";

        JSONObject contact = new JSONObject();

        contact.put("firstName", "Walter");
        contact.put("lastName", "White");
        contact.put("phone", "1111111111");
        contact.put("email", "heisenberg@meth.com");

        //create a new contact
        given()
                .header("Content-Type", "application/json")
                .body(contact)
        .when()
                .post("/users/3/contacts")
        .then()
                .assertThat().statusCode(201);

        //check if the contact exists
        when()
                .get("/users/3/contacts")
        .then()
                .assertThat().statusCode(200)
        .and()
                .body("firstName", hasItem("Walter"))
                .body("lastName", hasItem("White"))
                .body("phone", hasItem("1111111111"))
                .body("email", hasItem("heisenberg@meth.com"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_ThrowException_When_CreateUser_WithSpecialCharacters_AndNullValues(){

        testName = "should_ThrowException_When_CreateUser_WithSpecialCharacters_AndNullValues()";
        testDescription = "This test should attempt to create a new user with special characters and null values in the fields: \"firstName\" and \"lastName\" (Negative)" +
                "<br> <i>User credentials: </i>" +
                "<br> - \"firstName\": \"Jesse* \"," +
                "<br> - \"lastName\": null." +
                "<br> <i>Expected results if passed: </i> " +
                "<br> - HTTP response status code \"400\"." +
                "<br> - An exception is thrown on the server, " +
                        "not letting us save the user with special characters and null values in the user credentials.";

        JSONObject user = new JSONObject();

        user.put("firstName", "Jesse1* ");
        user.put("lastName", null);

        //create a new user with a null value
        given()
                .header("Content-Type", "application/json")
                .body(user)
        .when()
                .post("/users")
        .then()
                .assertThat().statusCode(400);
    }

    @Test(dependsOnMethods={"should_CreateUser_WithoutSpecialCharacters_And_NullValues"})
    @SuppressWarnings("unchecked")
    public void should_ThrowException_When_CreateContact_WithWrongNumberFormat(){

        testName = "should_ThrowException_When_CreateContact_WithWrongNumberFormat()";
        testDescription = "The following test should attempt to create a new contact with the field \"phone\" in the wrong format (Negative). There are three cases: 1st case (number is too short (less than 10 digits))," +
                " 2nd case(number is too long(more than 10 digits)), 3d case(number contains letters and special characters)." +
                "<br> <i>Contact data: </i>" +
                "<br> - \"firstName\": \"Walter\"," +
                "<br> - \"lastName\": \"White\"." +
                "<br> - \"phone\": \"1111\" (1st case) and \"111111111111111111111111\" (2nd case) and \"abCd123*56\" (3d case)" +
                "<br> - \"email\": \"heisenberg@meth.com\"." +
                "<br> <i>Expected results if passed: </i> " +
                "<br> - An exception is thrown on the server." +
                "<br> - HTTP response status code \"400\". Body: \"phone\": \"должно соответствовать шаблону \\\"\\\\d{10}\\\"\"";

        //number too short(less than 10 digits
        JSONObject contactWithNumberTooShort = new JSONObject();
        contactWithNumberTooShort.put("firstName", "Walter");
        contactWithNumberTooShort.put("lastName", "White");
        contactWithNumberTooShort.put("phone", "1111");
        contactWithNumberTooShort.put("email", "heisenberg@meth.com");

        //number too long(more than 10 digits
        JSONObject contactWithNumberTooLong = new JSONObject();
        contactWithNumberTooLong.put("firstName", "Walter");
        contactWithNumberTooLong.put("lastName", "White");
        contactWithNumberTooLong.put("phone", "111111111111111111111111");
        contactWithNumberTooLong.put("email", "heisenberg@meth.com");

        //number contains letters and special characters
        JSONObject contactWithNumberContainingLettersAndCharacters = new JSONObject();
        contactWithNumberContainingLettersAndCharacters.put("firstName", "Walter");
        contactWithNumberContainingLettersAndCharacters.put("lastName", "White");
        contactWithNumberContainingLettersAndCharacters.put("phone", "abCd123*56");
        contactWithNumberContainingLettersAndCharacters.put("email", "heisenberg@meth.com");

        //create a new contact. Number too short
        given()
                .header("Content-Type", "application/json")
                .body(contactWithNumberTooShort)
        .when()
                .post("/users/3/contacts")
        .then()
                .assertThat().statusCode(400);

        //create a new contact. Number too long
        given()
                .header("Content-Type", "application/json")
                .body(contactWithNumberTooLong)
        .when()
                .post("/users/3/contacts")
        .then()
                .assertThat().statusCode(400);

        //create a new contact. Number contains letters and characters
        given()
                .header("Content-Type", "application/json")
                .body(contactWithNumberContainingLettersAndCharacters)
        .when()
                .post("/users/3/contacts")
        .then()
                .assertThat().statusCode(400);
    }

    //catch test results
    @AfterMethod
    public synchronized void afterMethod(ITestResult result) {
        switch (result.getStatus()) {
            case ITestResult.FAILURE:
                extent.createTest(testName, testDescription)
                        .log(Status.FAIL, "Test failed! " + "<br><b><i>Reason: </i></b>"
                                + result.getThrowable().getMessage() );
                break;
            case ITestResult.SKIP:
                extent.createTest(testName, testDescription)
                        .log(Status.SKIP, "Test has been skipped!");
                break;
            default:
                extent.createTest(testName, testDescription)
                        .log(Status.PASS, "Test is successful!");
                break;
        }
    }

    //save logs
    @AfterClass
    public void tearDown(){
        extent.flush();
    }
}
