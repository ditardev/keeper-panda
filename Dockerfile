FROM eclipse-temurin:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} PandaApplication.jar
ENTRYPOINT ["java","-jar","PandaApplication.jar"]
