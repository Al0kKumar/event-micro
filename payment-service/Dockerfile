FROM java:17

WORKDIR /app

COPY build.gradle ./

RUN ./gradlew

COPY . .

EXPOSE 8080

CMD [ "./gradlew bootRun" ]
