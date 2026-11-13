# 🌤️ Spring Boot Weather API & Dashboard

A full-stack weather application built with **Spring Boot** and **Vanilla JavaScript**. It fetches real-time weather data from the OpenWeatherMap API, utilizes server-side caching to reduce external API calls, and features a clean, responsive frontend.

## ✨ Features

*   **3-Layer Architecture:** Clean separation of concerns (Controller, Service, Client).
*   **Real-time Data:** Fetches live weather data for any city worldwide.
*   **Caching:** Utilizes Spring's `@Cacheable` to store recent searches, drastically improving response times and preventing API rate-limiting.
*   **Global Error Handling:** Uses `@RestControllerAdvice` to gracefully handle invalid cities and API errors, returning clean HTTP status codes and JSON messages.
*   **CORS Configured:** Securely allows frontend communication.
*   **Responsive UI:** A simple, elegant frontend using plain HTML/CSS/JS.

## 🛠️ Tech Stack

*   **Backend:** Java 21, Spring Boot 3 (Web), Gradle
*   **Frontend:** HTML5, CSS3, Vanilla JavaScript
*   **External API:** [OpenWeatherMap](https://openweathermap.org/)
*   **Testing:** JUnit 5, Mockito *(Coming Soon)*

## 🏗️ Architecture Overview

1.  **Client (Frontend):** User enters a city name.
2.  **Controller (`WeatherController`):** Receives the HTTP GET request and delegates to the service.
3.  **Service (`WeatherService`):** Checks the cache. If the data isn't cached, it requests it from the client.
4.  **Client (`WeatherClient`):** Makes the HTTP call to OpenWeatherMap using Spring's `RestClient`.
5.  **DTO (`WeatherResponse`):** Maps the raw JSON into a clean, structured Java object for the frontend.

## 🚀 How to Run Locally

### Prerequisites
*   Java 21 or higher
*   An API key from OpenWeatherMap

### Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/MHadiHU/Weather.git
   cd weather-app