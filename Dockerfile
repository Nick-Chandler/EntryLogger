FROM ibmjava:11-jdk
RUN apt-get update && apt-get install iputils-ping -y && mkdir /app
COPY /EntryLoggerMaven.jar /app
WORKDIR /app
CMD java -jar EntryLoggerMaven.jar