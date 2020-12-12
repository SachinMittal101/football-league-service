FROM openjdk:8
EXPOSE 8085
ADD target/football-league-service.jar football-league-service.jar
ENTRYPOINT ["java", "-jar", "football-league-service.jar"]