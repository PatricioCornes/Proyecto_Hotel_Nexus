FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app
# Esto copia el .jar que generas con el comando clean install
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]