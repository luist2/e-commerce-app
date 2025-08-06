# E-Commerce Microservices Project

Proyecto de e-commerce desarrollado con arquitectura de microservicios utilizando Spring Boot, Docker, Kafka y más. Cada microservicio está desacoplado y especializado en un dominio particular, permitiendo escalar, mantener y desarrollar de forma independiente.

---

## 🛠️ Tecnologías utilizadas

| Servicio           | Tecnologías principales                                                                 |
|--------------------|------------------------------------------------------------------------------------------|
| **Config Server**   | Spring Cloud Config, Git                                                              |
| **Discovery**       | Eureka Server, Spring Cloud Config Client                                             |
| **Gateway**         | Spring Cloud Gateway, Config Client                                                   |
| **Customer**        | Spring Web, MongoDB, Validation, Eureka Client, Config Client                         |
| **Product**         | Spring Web, Spring Data JPA, PostgreSQL, Flyway, Validation, Eureka                    |
| **Order**           | Spring Web, JPA, PostgreSQL, OpenFeign, Kafka, Eureka, Validation                      |
| **Payment**         | Spring Web, JPA, Kafka, PostgreSQL, Eureka, Validation                                 |
| **Notification**    | Spring Web, Kafka, Mail API, Eureka, Plantillas de email (Thymeleaf), Validation       |
---


