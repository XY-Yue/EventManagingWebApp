window.onload = startSearch;
document.getElementById("continue").addEventListener("click", startSearch);

function startSearch(){
    let roomKeyword = document.getElementById("roomName").value.toString();
    let capacity = parseInt(document.getElementById("capacity").value.toString());
    let startHour = document.getElementById("startHour").value.toString();
    let endHour = document.getElementById("endHour").value.toString();

    $.ajax({
        type: "post",
        url: "startSearch",
        data: JSON.stringify({
            nameKey: roomKeyword,
            capacity: capacity,
            availableStart: startHour,
            availableEnd: endHour
        }),
        dataType: 'json',
        contentType: 'application/json',
        success: function (response) {
            document.getElementById("roomListPlaceHolder").innerHTML = response;
        },
        error: function (e) {
            console.log("Error!  " + e.toString());
        }
    });
}