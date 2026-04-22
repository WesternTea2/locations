#FROM eclipse-temurin:25-jre-ubi10-minimal
#RUN mkdir /opt/app
#COPY target/locations-0.0.1-SNAPSHOT.jar /opt/app/locations.jar
#CMD ["java", "-jar", "/opt/app/locations.jar"]

FROM eclipse-temurin:25 AS builder
WORKDIR /application
COPY target/locations-0.0.1-SNAPSHOT.jar locations.jar
RUN java -Djarmode=tools -jar locations.jar extract --layers --destination extracted

FROM eclipse-temurin:25
WORKDIR /application
COPY --from=builder /application/extracted/dependencies/ ./
COPY --from=builder /application/extracted/spring-boot-loader/ ./
COPY --from=builder /application/extracted/snapshot-dependencies/ ./
COPY --from=builder /application/extracted/application/ ./
ENTRYPOINT ["java", "-jar", "locations.jar"]