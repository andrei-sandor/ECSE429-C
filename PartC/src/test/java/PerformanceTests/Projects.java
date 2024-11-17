package PerformanceTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Projects {
    public String url = "http://localhost:4567";
    public int id = 1;

    public Projects() {
        RestAssured.baseURI = "http://localhost:4567";
    }

    public void createProject() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("title", "project_title");
        body.put("description", "project_description");
        body.put("completed", false);
        body.put("active", true);

        Response response = RestAssured.given()
                .body(body.toString())
                .post("/projects");

        int statusCode = response.getStatusCode();
        String actualTitle = response.getBody().jsonPath().getString("title");
        String description = response.getBody().jsonPath().getString("description");
        String completed = response.getBody().jsonPath().getString("completed");
        String active = response.getBody().jsonPath().getString("active");
        String bodyResponse = response.getBody().asString();
        org.json.JSONObject jsonResponse = new org.json.JSONObject(bodyResponse);
        id = jsonResponse.getInt("id");
        assertEquals(201, statusCode);
        assertEquals("project_title", actualTitle);
        assertEquals("project_description", description);
        assertEquals("false", completed);
        assertEquals("true", active);
    }

    public void updateProject() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("title", "updated_project_title");
        body.put("description", "updated_project_description");
        body.put("completed", true);
        body.put("active", false);

        Response response = RestAssured.given()
                .body(body.toString())
                .post("/projects/" + id);

        int statusCode = response.getStatusCode();
        String actualTitle = response.getBody().jsonPath().getString("title");
        String description = response.getBody().jsonPath().getString("description");
        String completed = response.getBody().jsonPath().getString("completed");
        String active = response.getBody().jsonPath().getString("active");
        assertEquals(200, statusCode);
        assertEquals("updated_project_title", actualTitle);
        assertEquals("updated_project_description", description);
        assertEquals("true", completed);
        assertEquals("false", active);
    }

    public void deleteProject() {
        Response response = RestAssured.given()
                .delete("/projects/" + id);

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
        id++;
    }
}