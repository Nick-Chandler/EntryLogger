#include "WiFi.h"
#define WIFI_NETWORK "SSID" // replace with your network SSID
#define WIFI_PASSWORD "PASSWORD" // replace with your wifi password

#define WIFI_TIMEOUT_MS 20000

#define PASS_LOCKOUT_TIME 750 // defines time waited before counting another person
#define ENTRY_WIDTH 70 // entry-way width in cm

// using pin 22 for trig and 23 for echo
#define echoPin 23
#define trigPin 22

long duration, distance;
WiFiClient client;

// defining host ip and port
const uint16_t port = 9999;
const char * host = "0.0.0.0"; // replace with host pc ip-address

// connect esp32 client to wifi
void connectToWiFi() {
  Serial.print("Connecting to WiFi\n");
  WiFi.mode(WIFI_STA);
  WiFi.begin(WIFI_NETWORK,WIFI_PASSWORD);

  unsigned long startAttemptTime = millis();

  // attempts to connect to wifi until WIFI_TIMEOUT_MS is reached
  while(WiFi.status() != WL_CONNECTED && millis() - startAttemptTime < WIFI_TIMEOUT_MS) {
    Serial.print(".\n");
    delay(100);
  }

  if(WiFi.status() != WL_CONNECTED) {
    Serial.print("CONNECTION FAILED!");
  }
  else{
    Serial.print("CONNECTED!\n");
    Serial.println(WiFi.localIP());
  }
}

// connects esp32 client to server running in container on host
// container port 9999 is mapped to host port 9999
void connectToServer() {
  if (!client.connect(host, port)) {

      Serial.println("Connection to host failed");

      delay(1000);
      return;
    }
    else {
      Serial.println("Connected to server");
    }
}

// sends a single character to server when entry is detected
void handleEntry() {
  Serial.println("Enrty Detected");
  client.println("E");
  Serial.println("Sent data to server");
  delay(PASS_LOCKOUT_TIME);
}


void setup() {
  Serial.begin(9600);
  Serial.println("\nRAN");
  connectToWiFi();
  connectToServer();
  pinMode(trigPin,OUTPUT);
  pinMode(echoPin,INPUT);
  delay(3000);
}


void loop() {
  
  digitalWrite(trigPin,LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin,HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin,LOW);

  // calculates distance from object echo time of sensor
  duration = pulseIn(echoPin, HIGH);
  distance = duration/58.2;
  String disp = String(distance);

  if(distance <= ENTRY_WIDTH) { // if object is detected within entryway
    handleEntry();
  }

  if(!client.connected()) {
    connectToServer();
  }
  
  delay(200);
}
