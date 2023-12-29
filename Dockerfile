FROM amd64/amazoncorretto:17

WORKDIR /app

COPY ./build/libs/lequuServer-0.0.1-SNAPSHOT.jar /app/lequuServer.jar

CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "lequuServer.jar"]