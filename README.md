Vertx starter

### Building

To launch your tests:
```
./gradlew clean build
```

Request templates

```shell
curl --location 'http://localhost:8080/api/v1/UserService/Create' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Test"
}'

curl --location 'http://localhost:8080/api/v1/UserService/Update' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1,
    "name": "Test 1"
}'

curl --location 'http://localhost:8080/api/v1/UserService/Get' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1
}'

curl --location 'http://localhost:8080/api/v1/UserService/Delete' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1
}'

```


### Help

* https://vertx.io/docs/[Vert.x Documentation]
* https://stackoverflow.com/questions/tagged/vert.x?sort=newest&pageSize=15[Vert.x Stack Overflow]
* https://groups.google.com/forum/?fromgroups#!forum/vertx[Vert.x User Group]
* https://discord.gg/6ry7aqPWXy[Vert.x Discord]


