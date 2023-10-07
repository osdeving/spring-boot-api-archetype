FROM maven:3.8.3 as build
WORKDIR /workspace/app

COPY pom.xml ./
RUN mvn dependency:go-offline # Baixa as dependências primeiro para aproveitar o caching do Docker
COPY src ./src
RUN mvn clean package

COPY target/java-spring-boot-api.jar ./
RUN java -Djarmode=layertools -jar java-spring-boot-api.jar extract --destination target/

FROM openjdk:17-alpine3.14
VOLUME /tmp
ARG DEPENDENCY=/workspace/app
COPY --from=build ${DEPENDENCY}/target/dependencies/ ./
COPY --from=build ${DEPENDENCY}/target/snapshot-dependencies/ ./
COPY --from=build ${DEPENDENCY}/target/spring-boot-loader/ ./
COPY --from=build ${DEPENDENCY}/target/application/ ./

RUN apk add --no-cache curl tzdata
RUN cp /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime && echo "America/Sao_Paulo" > /etc/timezone

ENTRYPOINT exec java $JAVA_OPTS org.springframework.boot.loader.JarLauncher

