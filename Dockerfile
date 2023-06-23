FROM adoptopenjdk/openjdk11:alpine-jre

EXPOSE 5500

COPY . CloudServerDiplom.jar

CMD ["java","-jar","CloudServerDiplom.jar"]