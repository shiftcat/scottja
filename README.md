# Scott RESTful Web service

이 프로젝트는 오라클 데이터베이스에서 scott 계정으로 제공되는 샘플 데이터를 RESTful API로 제공하는 샘플 프로젝트이다.


## Modules
* scott-core
* scott-vo
* scott-api
* scott-flux

### 프로젝트 환경
* Java 1.8
* Spring Boot 2.0.4
* Lombok
* JPA
* Gradle 4.10
* MySql 8.0


### 기능 및 특징
* 부서(DEPT), 사원(EMP) 관리 API 
* API 한 호출당 고유 ID 부여
* Swagger를 이용하여 API문서 제공
* JPA ORM (querydsl)


### Environment variables
* DATABASE_NAME=
* DATABASE_SERVER=
* SPRING_PROFILES_ACTIVE=
* DATABASE_PORT=
* DATABASE_PASSWD=
* DATABASE_USER=
* LOG_DIR=


### docker run

```buildoutcfg
docker-compose up --build

docker stats

docker-compose down
```

### Swagger API 문서
* API Document: http://localhost:8080/swagger-ui.html



