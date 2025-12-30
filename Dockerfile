FROM openjdk:17.0.2
COPY . .
RUN ./mvnw clean install -DskipTests
CMD ./mvnw spring-boot:run