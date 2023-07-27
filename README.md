### OVERVIEW
# This project combines an ultrasonic sensor, a java server, and a mysql database to log the number of people who pass through an area

# First an ESP32 microcontroller wired to an ultrasonic sensor will detect movement and send a signal to the Java socket server
# The Java server will then process this signal and record the data into a connected mysql database
# Finally the server will also automatically recorded the date and number of passes for that day and save the daily statistics into the database

## Connections
# The ESP32 microcontroller is connected to an ultrasonic sensor at pin 22 (trig pin) and pin 23 (echo pin)
# The MCU device does not need to be physically connected to the host pc since it uses wireless communication to reach the server
# 

## Docker (see docker-compose and Dockerfile file)
# Container 1: The Java server
# Container 2: MySQL database
# network: loggernet (user defined bridge network which allows communication between the two containers)
