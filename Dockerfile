FROM maven:3-adoptopenjdk-11 AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace

COPY /src /workspace/src
RUN mvn -f pom.xml clean package -Dmaven.test.skip

FROM maven:3-adoptopenjdk-11
ENV TZ=Europe/Tallinn
COPY --from=build /workspace/target/demo-user-project-0.0.1-SNAPSHOT.jar demo-project.jar
EXPOSE 8081
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","demo-project.jar","--host","0.0.0.0","--port","8081"]
