FROM eclipse-temurin:25-jre-alpine
RUN addgroup -S nexus && adduser -S nexus -G nexus
WORKDIR /app
COPY target/*.jar app.jar
RUN chown -R nexus:nexus /app
USER nexus
ENTRYPOINT ["java", "-jar", "app.jar"]
