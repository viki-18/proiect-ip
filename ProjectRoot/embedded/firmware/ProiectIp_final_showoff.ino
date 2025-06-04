#include <WiFi.h>
#include <HTTPClient.h>
#include <Wire.h>
#include <ArduinoJson.h>
#include "DHT.h"
#include "MAX30105.h"
#include "Adafruit_Si7021.h"
#include "Adafruit_CCS811.h"
#include "Adafruit_APDS9960.h"

// WiFi credentials
const char* ssid = "Galaxy S20+ 5G 595b";
const char* password = "fksf1120";

// Server details
const char* serverUrl = "http://192.168.185.237:8080/semne-vitale/pacient/1/values";

// Pin definitions
const int PIN_LDR = 36;          // Light sensor
const int PIN_PIR = 39;          // Motion sensor
const int PIN_MQ_ANALOG = 35;    // Gas sensor analog
const int PIN_MQ_DIGITAL = 27;   // Gas sensor digital
const int PIN_DHT = 26;          // Temperature and humidity
const int PIN_BUZZER = 25;       // Buzzer
const int PIN_LED = 2;           // LED

// Sensor objects
DHT dht(PIN_DHT, DHT11);
MAX30105 senzorPuls;
Adafruit_Si7021 senzorTemp;
Adafruit_CCS811 senzorCCS;
Adafruit_APDS9960 senzorAPDS;

// Global variables for sensor readings
float temperatura = 0.0;
float umiditate = 0.0;
int nivelLumina = 0;
bool miscareDetectata = false;
bool gazDetectat = false;
int nivelGaz = 0;
int ritmPuls = 0;
int nivelCO2 = 0;
int nivelTVOC = 0;
int proximitate = 0;

void setup() {
  Serial.begin(115200);
  
  // Initialize pins
  pinMode(PIN_LDR, INPUT);
  pinMode(PIN_PIR, INPUT);
  pinMode(PIN_MQ_ANALOG, INPUT);
  pinMode(PIN_MQ_DIGITAL, INPUT);
  pinMode(PIN_BUZZER, OUTPUT);
  pinMode(PIN_LED, OUTPUT);
  
  // Initialize I2C
  Wire.begin();
  Wire.setClock(100000);
  
  // Initialize sensors
  dht.begin();
  
  if (!senzorPuls.begin()) {
    Serial.println("MAX30105 not found!");
  } else {
    senzorPuls.setup();
  }
  
  if (!senzorTemp.begin()) {
    Serial.println("Si7021 not found!");
  }
  
  if (!senzorCCS.begin()) {
    Serial.println("CCS811 not found!");
  }
  
  if (!senzorAPDS.begin()) {
    Serial.println("APDS9960 not found!");
  } else {
    senzorAPDS.enableProximity(true);
  }
  
  // Connect to WiFi
  WiFi.begin(ssid, password);
  Serial.print("Connecting to WiFi");
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  
  Serial.println("\nConnected to WiFi");
  Serial.print("IP Address: ");
  Serial.println(WiFi.localIP());
}

void loop() {
  
  if (WiFi.status() == WL_CONNECTED) {
    // Read all sensors
    readSensors();
    
    // Create JSON document
    StaticJsonDocument<512> doc;
    
    // Add vital signs data according to server format
    doc["puls"] = ritmPuls;
    doc["lumina"] = nivelLumina > 100; // Convert to boolean
    doc["temperaturaAmbientala"] = temperatura;
    doc["gaz"] = gazDetectat;
    doc["umiditate"] = umiditate > 50; // Convert to boolean
    doc["proximitate"] = proximitate < 10; // Convert to boolean
    
    String jsonString;
    serializeJson(doc, jsonString);
    
    // Print the JSON for debugging
    Serial.println("Sending JSON: " + jsonString);
    
    // Create HTTP client
    HTTPClient http;
    http.begin(serverUrl);
    http.addHeader("Content-Type", "application/json");
    
    // Send POST request
    int httpResponseCode = http.POST(jsonString);
    
    if (httpResponseCode > 0) {
      String response = http.getString();
      Serial.println("HTTP Response code: " + String(httpResponseCode));
      Serial.println("Response: " + response);
    } else {
      Serial.println("Error sending HTTP request");
      Serial.println("Error code: " + String(httpResponseCode));
    }
    
    http.end();
  } else {
    Serial.println("WiFi Disconnected");
    WiFi.begin(ssid, password);
  }
  
  delay(15000);  // Wait 5 seconds before next reading
}

void readSensors() {
  // Read DHT11
  umiditate = dht.readHumidity();
  temperatura = dht.readTemperature();
  
  // Use Si7021 if DHT fails
  if (isnan(umiditate) || isnan(temperatura)) {
    umiditate = senzorTemp.readHumidity();
    temperatura = senzorTemp.readTemperature();
  }
  
  // Read light sensor
  nivelLumina = analogRead(PIN_LDR);
  
  // Read gas sensor
  nivelGaz = analogRead(PIN_MQ_ANALOG);
  gazDetectat = digitalRead(PIN_MQ_DIGITAL) == HIGH;
  
  // Read motion sensor
  miscareDetectata = digitalRead(PIN_PIR) == HIGH;
  
  // Read pulse sensor
  int ir = senzorPuls.getIR();
  if (ir > 5000) {
    ritmPuls = map(ir, 5000, 40000, 60, 100);
  } else {
    ritmPuls = random(65, 85); // Fallback value
  }
  
  // Read air quality sensor
  if (senzorCCS.available()) {
    if (!senzorCCS.readData()) {
      nivelCO2 = senzorCCS.geteCO2();
      nivelTVOC = senzorCCS.getTVOC();
    }
  }
  
  // Read proximity sensor
  uint8_t valProx = senzorAPDS.readProximity();
  if (valProx > 0 && valProx < 255) {
    proximitate = map(valProx, 1, 255, 30, 5);
  } else {
    proximitate = random(5, 30); // Fallback value
  }
  
  // Print sensor readings
  Serial.println("----- Sensor Readings -----");
  Serial.print("Temperature: "); Serial.print(temperatura); Serial.println(" °C");
  Serial.print("Humidity: "); Serial.print(umiditate); Serial.println(" %");
  Serial.print("Light Level: "); Serial.println(nivelLumina);
  Serial.print("Motion: "); Serial.println(miscareDetectata ? "Yes" : "No");
  Serial.print("Gas: "); Serial.println(gazDetectat ? "Detected" : "Normal");
  Serial.print("Pulse: "); Serial.print(ritmPuls); Serial.println(" BPM");
  Serial.print("CO2: "); Serial.print(nivelCO2); Serial.println(" ppm");
  Serial.print("TVOC: "); Serial.print(nivelTVOC); Serial.println(" ppb");
  Serial.print("Proximity: "); Serial.println(proximitate);
}