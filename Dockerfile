FROM openjdk:17.0.2
COPY . .
RUN ./mvnw clean install -DskipTests
CMD ./mvnw spring-boot:run


#FROM openjdk:17.0.2
#COPY target/employee-service-*.jar service.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "/service.jar"]