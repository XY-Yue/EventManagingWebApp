let available = [];
const SELECTED = "forestgreen";
const NOT_SELECTED = "white";

for (let i = 0; i < 24; i++){
    document.getElementById("roomAvailable" + i).addEventListener("click", onChangeAvailableTime);
}

// Respond user's click event on the buttons in the select hour field, changes its color
function onChangeAvailableTime(){
    if (this.style.backgroundColor === SELECTED){
        this.style.backgroundColor = NOT_SELECTED;
        removeHourFromAvailable(parseInt(this.innerHTML.toString()));
    }else {
        this.style.backgroundColor = SELECTED;
        addHourToAvailable(parseInt(this.innerHTML.toString()));
    }
}

function removeHourFromAvailable(hour){
    for (let i = 0; i < available.length; i++){
        if (available[i] === hour) {
            available.splice(i, 1);
        }
    }
}

function addHourToAvailable(hour){
    for (let i = 0; i < available.length; i++){
        if (available[i] > hour) {
            available.splice(i, 0, hour);
            break;
        }
    }
}
