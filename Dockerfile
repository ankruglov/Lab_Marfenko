FROM java:8-jdk-alpine
ADD target/lab-1.0-SNAPSHOT-jar-with-dependencies.jar lab-1.0-SNAPSHOT-jar-with-dependencies.jar
ENTRYPOINT ["java", "-jar", "lab-1.0-SNAPSHOT-jar-with-dependencies.jar"]