services:
  postgres:
    image: postgres:17
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: mydb
    ports:
      - "5432:5432"

  app:
    build:
	  context: .
    depends_on:
   	  - postgres
    environment:
      DB_URL: jdbc:postgresql://postgres:5432/mydb
      DB_USER: postgres
      DB_PASSWORD: postgres
    ports:
      - "8080:8080"


