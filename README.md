## Технологии

* **Java 17**
* **Maven**
* **Spring Boot**
* **Liquibase**
* **Docker**
* **Docker Compose**

# k6 run script.js

---

##  Инструкция по запуску

Для запуска приложения следуйте этим шагам:

1.  **Клонируйте репозиторий:**
    ```
    git clone https://github.com/007vrstud-droid/wallet.git
    ```

2.  **Перейдите в директорию проекта:**
    ```
    cd wallet
    ```
    
3.  **Запустите контейнеры с помощью Docker Compose:**
    ```
    docker-compose up -d
    ```

4.  **Откройте документацию API:**
    После запуска контейнеров, вы сможете получить доступ к документации Swagger по следующему адресу:
    [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

---

##  Остановка и очистка

Чтобы остановить контейнеры и удалить тома, используйте следующую команду:
```
docker-compose down -v