FROM gradle:6.8.2-jdk11 AS builder
WORKDIR /home/gradle/test

COPY --chown=gradle:gradle build.gradle settings.gradle gradle ./
RUN gradle build --no-daemon > /dev/null 2>&1 || true
COPY --chown=gradle:gradle . ./
RUN gradle build --no-daemon -x test


FROM openjdk:16-ea-23-jdk-alpine3.12 AS build
WORKDIR /myapp
COPY --from=builder /home/gradle/test/build/libs/*.jar /myapp/sba1.jar
RUN java -Djarmode=layertools -jar /myapp/sba1.jar extract



FROM openjdk:11.0.8-jre-slim
WORKDIR /myapp
COPY --from=build /myapp/dependencies/ ./
COPY --from=build /myapp/spring-boot-loader/ ./
COPY --from=build /myapp/snapshot-dependencies/ ./
COPY --from=build /myapp/application/ ./
ENTRYPOINT [ "java", "org.springframework.boot.loader.JarLauncher"]