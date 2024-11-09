package PerformanceTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Todos {
    public String url = "http://localhost:4567";
    public static Process jar;
    public int id = 9;

//    @BeforeEach
//    public void setUp() throws Exception {
//        try {
//            jar = Runtime.getRuntime().exec("java -jar runTodoManagerRestAPI-1.5.5.jar");
//            sleep(300);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @AfterEach
//    public void setDown() throws InterruptedException {
//        jar.destroy();
//        sleep(300);
//    }
public Todos() {
    RestAssured.baseURI = "http://localhost:4567";
}

    @Test
   public void createTodo() throws JSONException, IOException, InterruptedException {

       JSONObject body = new JSONObject();
       body.put("title", "title1");
       body.put("description", "description1");
       body.put("doneStatus", false);

       Response response = RestAssured.given()
               .body(body.toString())
               .post("/todos");


       int statusCode = response.getStatusCode();
       String actualTitle = response.getBody().jsonPath().getString("title");
       String description = response.getBody().jsonPath().getString("description");
       String doneStatus = response.getBody().jsonPath().getString("doneStatus");
       String body2 = response.getBody().asString();
       org.json.JSONObject jsonResponse = new org.json.JSONObject(body2);
       id = jsonResponse.getInt("id");
       System.out.println(id);
       assertEquals(201, statusCode);
       assertEquals("title1", actualTitle);
       assertEquals("description1", description);
       assertEquals("false", doneStatus);
   }

   @Test
   public void updateTodo() throws JSONException, InterruptedException, IOException {

       JSONObject body = new JSONObject();
       body.put("title", "title2");
       body.put("description", "description2");
       body.put("doneStatus", false);

       Response response = RestAssured.given()
               .body(body.toString())
               .post("/todos/" + id);


       int statusCode = response.getStatusCode();
       String actualTitle = response.getBody().jsonPath().getString("title");
       String description = response.getBody().jsonPath().getString("description");
       String doneStatus = response.getBody().jsonPath().getString("doneStatus");
       assertEquals(200, statusCode);
       assertEquals("title2", actualTitle);
       assertEquals("description2", description);
       assertEquals("false", doneStatus);
   }

   @Test
   public void deleteTodo() throws JSONException, IOException, InterruptedException {
       Response response = RestAssured.given()
               .delete("/todos/" +id);

       int statusCode = response.getStatusCode();
       assertEquals(200,statusCode);
       id++;
   }

}
