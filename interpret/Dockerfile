FROM python:3

ENV JAVA_HOME=/opt/java/openjdk
COPY --from=eclipse-temurin:21-jre $JAVA_HOME $JAVA_HOME
ENV PATH="${JAVA_HOME}/bin:${PATH}"

RUN pip install bcrypt
RUN pip install pyOpenSSL
RUN pip install pycryptodome
RUN pip install cryptography


WORKDIR /interpret
COPY ./target/interpret-0.0.1-SNAPSHOT.jar /interpret
CMD ["java", "-jar", "interpret-0.0.1-SNAPSHOT.jar"]