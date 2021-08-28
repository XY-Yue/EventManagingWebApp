let available = [];
const SELECTED = "forestgreen";
const NOT_SELECTED = "white";

for (let i = 0; i < 24; i++){
    let element = document.getElementById("roomAvailable" + i.toString());
    element.addEventListener("click", onChangeAvailableTime);
    element.style.backgroundColor = NOT_SELECTED;
}
document.getElementById("continue").addEventListener("click", validateInput);

// Respond user's click event on the buttons in the select hour field, changes its color
function onChangeAvailableTime(){
    if (this.style.backgroundColor === SELECTED){
        this.style.backgroundColor = NOT_SELECTED;
        removeHourFromAvailable(parseInt(this.innerHTML.toString()));
    }else {
        this.style.backgroundColor = SELECTED;
        addHourToAvailable(parseInt(this.innerHTML.toString()));
    }
    document.getElementById("available").value = JSON.stringify(available);
}

function removeHourFromAvailable(hour){
    for (let i = 0; i < available.length; i++){
        if (available[i] === hour) {
            available.splice(i, 1);
        }
    }
}

function addHourToAvailable(hour){
    if (available.length === 0){
        available.push(hour);
        return;
    }
    for (let i = 0; i < available.length; i++){
        if (available[i] > hour) {
            available.splice(i, 0, hour);
            break;
        }
    }
}

function validateInput(){
    document.getElementById("availableWarning").innerHTML = "";
    document.getElementById("invalidNameWarning").innerHTML = "";
    let roomName = document.getElementById("roomName").value.toString();
    if (available.length === 0){
        document.getElementById("availableWarning").innerHTML =
            "Choose when room is available here";
    }else if (roomName === ""){
        document.getElementById("invalidNameWarning").innerHTML =
            "Room name cannot be empty.";
    }else {
        $.ajax({
            type: 'post',
            url: 'validateName?roomName=' + roomName,
            success: function (response) {
                if (response['status']){
                    // The input are all valid, submit the form.
                    document.getElementById("roomForm").submit();
                }else {
                    document.getElementById("invalidNameWarning").innerHTML =
                        "Room name already exist.";
                }
            },
            error: function (e) {
                console.log("Room name validation failed.")
            }
        })
    }
}
