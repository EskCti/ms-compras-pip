
FROM maven:3.9.5-amazoncorretto-17 as build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip=true

FROM amazoncorretto:17
COPY --from=build /home/app/target/mscompra.war /user/local/lib/mscompra.war
COPY target/mscompra.war /user/local/lib/mscompra.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/user/local/lib/mscompra.war"]
