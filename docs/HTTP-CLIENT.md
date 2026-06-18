


# Cliente HTTP

Guía para explicar cómo lanzar peticiones HTTP desde Java Spring a servicios externos como por ejemplo una API REST.


```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-restclient</artifactId>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-restclient-test</artifactId>
      <scope>test</scope>
    </dependency>
```

Consumir API REST:

https://fakestoreapi.com/products

Primero necesitamos un DTO: ProductDTO

Necesitamos usar el RestClient

