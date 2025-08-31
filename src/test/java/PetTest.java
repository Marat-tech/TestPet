import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PetTest {
    // Базовый URL Pet Store API
    private static final String BASE_URL = "https://petstore.swagger.io/v2";

    // Уникальный ID для теста (можно генерировать случайно)
    private static final int PET_ID = 15; // Используем уникальный ID

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void createPet() {
        String petJson = """
                {
                  "id": 15,
                  "category": {
                    "id": 15,
                    "name": "dog"
                  },
                  "name": "Sam",
                  "photoUrls": [
                    "https://ornella.club/uploads/posts/2023-02/5288/1675872634_ornella-club-p-smeshnie-mordi-sobak-zhivotnie-vkontakte-2.jpg"
                  ],
                  "tags": [
                    {
                      "id": 15,
                      "name": "balbes"
                    }
                  ],
                  "status": "durnoy"
                } 
                """.formatted(PET_ID);



        given()
                .contentType(ContentType.JSON)
                .body(petJson)
                .when()
                .post("/pet")
                .then()
                .log().ifError() // Логируем тело ответа, если ошибка
                .statusCode(200) // Petstore возвращает 200 при успехе
                .body("id", equalTo(PET_ID))
                .body("name", equalTo("Sam"))
                .body("category.name", equalTo("dog"))
                .body("status", equalTo("durnoy"));

        // Шаг 2: Проверяем, что питомец действительно создан — делаем GET

        given()
                .pathParam("petId", PET_ID)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200)
                .body("id", equalTo(PET_ID))
                .body("name", equalTo("Sam"))
                .body("status", equalTo("durnoy"));

    }

    @Test
    public void testUpdatePetName() {
        // Новое имя питомца
        String newName = "Vasya";


        // Тело запроса для обновления (можно передать только нужные поля)
        String updateBody = """
            {
                "id": %d,
                "name": "%s",
                "status": "durnoy"
            }
            """.formatted(PET_ID, newName);

        // Отправляем PUT-запрос для обновления питомца
        given()
                .contentType(ContentType.JSON)
                .body(updateBody)
                .when()
                .put("/pet")
                .then()
                .statusCode(200) // или 201 — зависит от API
                .body("name", equalTo(newName))
                .body("id", equalTo(PET_ID))
                .log().ifError();


        given()
                .pathParam("petId", PET_ID)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200)
                .body("name", equalTo(newName))
                .body("id", equalTo(PET_ID));


        given()
                .pathParam("petId", PET_ID)
                .when()
                .delete("/pet/{petId}")
                .then()
                .statusCode(200)
                .body("message", equalTo(String.valueOf(PET_ID)))
                .log().ifError();

        // Шаг 4: Проверить, что питомец больше не существует


        given()
                .pathParam("petId", PET_ID)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(404)
                .body("message", equalTo("Pet not found"));


    }



}
