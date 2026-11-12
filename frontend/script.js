const searchForm = document.getElementById('searchForm');
const cityInput = document.getElementById('cityInput');

searchForm.addEventListener('submit', (event) => {
    event.preventDefault();
    getWeather();
});

async function getWeather() {
    const city = cityInput.value.trim();
    const weatherCard = document.getElementById('weatherCard');
    const errorMessage = document.getElementById('errorMessage');
    const emptyState = document.getElementById('emptyState');
    const searchBtn = document.getElementById('searchBtn');

    errorMessage.classList.add('hidden');

    if (!city) {
        emptyState.classList.add('hidden');
        weatherCard.classList.add('hidden');
        errorMessage.innerText = "Type a city name in the search bar above before checking the weather.";
        errorMessage.classList.remove('hidden');
        cityInput.focus();
        return;
    }

    searchBtn.disabled = true;
    searchBtn.querySelector('span').innerText = "Checking";

    try {
        const response = await fetch(`http://localhost:8080/api/weather?city=${encodeURIComponent(city)}`);
        const data = await response.json();

        emptyState.classList.add('hidden');

        if (response.ok) {
            const tempValue = Math.round(Number(data.temperature));
            
            document.getElementById('cityName').innerText = data.city;
            document.getElementById('description').innerText = data.description;
            document.getElementById('comfortNote').innerText = getComfortGuidance(tempValue);
            document.getElementById('observedTime').innerText = formatLocalTime();

            applyClimateTheme(tempValue);
            weatherCard.classList.remove('hidden');
            animateTemperature(document.getElementById('temperature'), tempValue);
        } else {
            weatherCard.classList.add('hidden');
            errorMessage.innerText = data.message || `We couldn't find weather data for "${city}". Check the spelling and try again.`;
            errorMessage.classList.remove('hidden');
        }
    } catch (error) {
        emptyState.classList.add('hidden');
        weatherCard.classList.add('hidden');
        errorMessage.innerText = "Unable to reach your weather service. Make sure your Spring Boot server is running on port 8080.";
        errorMessage.classList.remove('hidden');
    } finally {
        searchBtn.disabled = false;
        searchBtn.querySelector('span').innerText = "Check";
    }
}

function animateTemperature(element, targetTemp) {
    const duration = 450;
    const startTemp = 0;
    const startTime = performance.now();

    function step(currentTime) {
        const elapsed = currentTime - startTime;
        const progress = Math.min(elapsed / duration, 1);
        const easeOut = 1 - Math.pow(1 - progress, 3);
        const current = Math.round(startTemp + (targetTemp - startTemp) * easeOut);
        
        element.innerText = current;
        if (progress < 1) {
            requestAnimationFrame(step);
        }
    }
    requestAnimationFrame(step);
}

function applyClimateTheme(temp) {
    if (temp <= 10) {
        document.body.setAttribute('data-climate', 'cold');
    } else if (temp <= 24) {
        document.body.setAttribute('data-climate', 'mild');
    } else {
        document.body.setAttribute('data-climate', 'warm');
    }
}

function getComfortGuidance(temp) {
    if (temp <= 5) return "Crisp and cold — bundle up outside.";
    if (temp <= 15) return "Cool air — a light jacket is ideal.";
    if (temp <= 25) return "Comfortable and mild conditions.";
    return "Warm weather — stay hydrated.";
}

function formatLocalTime() {
    return `Checked at ${new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}`;
}
