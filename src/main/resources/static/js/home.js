//change date

function currentDate() {
    let now = new Date();

    let days = [
        "Sunday",
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday"
    ];
    let day = days[now.getDay()];
    let hours = now.getHours();
    let minutes = now.getMinutes();
    let currentDay = document.querySelector("#day");
    let currentTime = document.querySelector("#time");
    currentDay.innerHTML = `${day}`;
    currentTime.innerHTML = `${hours} : ${minutes}`;
}

currentDate();

//search fake data
function searchCity(event) {
    event.preventDefault();
    let input = document.querySelector("#search-input");
    let currentCity = document.querySelector("span.city");
    if (input.value) {
        currentCity.innerHTML = input.value;
    } else {
        currentCity.innerHTML = "Kyiv";
    }
}

let searchButton = document.querySelector("#search-button");
searchButton.addEventListener("click", searchCity);

//C to F
function toFahrenheit(event) {
    event.preventDefault();
    let temp = document.querySelector("span.temp");
    temp.innerHTML = 53;
}

function toCelcius(event) {
    event.preventDefault();
    let temp = document.querySelector("span.temp");
    temp.innerHTML = 12;
}

let fahrenheitLink = document.querySelector("#fahrenheit");
fahrenheitLink.addEventListener("click", toFahrenheit);

let celciusLink = document.querySelector("#celsius");
celciusLink.addEventListener("click", toCelcius);
