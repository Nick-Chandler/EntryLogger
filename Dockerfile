FROM ibmjava:11-jdk
RUN apt-get update && apt-get install iputils-ping -y && mkdir /app
COPY /EntryLogger.jar /app
WORKDIR /app
CMD java -jar EntryLogger.jar