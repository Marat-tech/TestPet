import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PetTesting {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";

    private static final int PET_ID = 18;
    // Уникальный ID для теста

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;



    }
    @Test
    public void testCreateUpdateAndDeletePet() {
        // Шаг 1: Создать питомца
        String createPetBody = """
              {
                  "id": 18,
                  "category": {
                    "id": 18,
                    "name": "dog"
                  },
                  "name": "Sam",
                  "photoUrls": [
                    "https://ornella.club/uploads/posts/2023-02/5288/1675872634_ornella-club-p-smeshnie-mordi-sobak-zhivotnie-vkontakte-2.jpg"
                  ],
                  "tags": [
                    {
                      "id": 18,
                      "name": "balbes"
                    }
                  ],
                  "status": "durnoy"
                } 
            """.formatted(PET_ID);


        Response response = given()
                .contentType(ContentType.JSON)
                .body(createPetBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .body("id", equalTo(PET_ID))
                .body("name", equalTo("Sam"))
                .extract()
                .response();

        System.out.println("Response: " + response.asString());

        try {
            System.out.println("Ожидание три секунды перед отправкой запроса");
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Задержка прервана: " + e.getMessage());
            return;
        }

        given()
                .pathParam("petId", 18)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Sam"));
        try {
            System.out.println("Ожидание пять секунд перед отправкой запроса");
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Задержка прервана: " + e.getMessage());
            return;
        }

        String updatePetBody = """
            {
               "id": 18,
                  "category": {
                    "id": 18,
                    "name": "dog"
                  },
                  "name": "Vasya",
                  "photoUrls": [
                    "https://ornella.club/uploads/posts/2023-02/5288/1675872634_ornella-club-p-smeshnie-mordi-sobak-zhivotnie-vkontakte-2.jpg"
                  ],
                  "tags": [
                    {
                      "id": 18,
                      "name": "balbes"
                    }
                  ],
                  "status": "ne durnoy"
            }
            """.formatted(PET_ID);

        Response response1 = given()
                .contentType(ContentType.JSON)
                .body(updatePetBody)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .body("name", equalTo("Vasya"))
                .body("status", equalTo("ne durnoy"))
                .extract()
                .response();

        System.out.println("Response: " + response1.asString());
        try {
            System.out.println("Ожидание три секунды перед отправкой запроса");
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Задержка прервана: " + e.getMessage());
            return;
        }


        given()
                .pathParam("petId", PET_ID)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Vasya"))
                .body("status", equalTo("ne durnoy"));
        try {
            System.out.println("Ожидание четыре секунды перед отправкой запроса");
            Thread.sleep(4000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Задержка прервана: " + e.getMessage());
            return;
        }


        // Шаг 3: Удалить питомца
        given()
                .pathParam("petId", PET_ID)
                .when()
                .delete("/pet/{petId}")
                .then()
                .statusCode(200)
                .body("message", equalTo(String.valueOf(PET_ID)))
                .log().ifError();
        try {
            System.out.println("Ожидание три секунды перед отправкой запроса");
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Задержка прервана: " + e.getMessage());
            return;
        }

        // Шаг 4: Проверить, что питомец больше не существует
        given()
                .pathParam("petId", PET_ID)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(404)
                .body("message", equalTo("Pet not found"));
        try {
            System.out.println("Ожидание три секунды перед отправкой запроса");
            Thread.sleep(4000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Задержка прервана: " + e.getMessage());
            return;
        }




    }


}
