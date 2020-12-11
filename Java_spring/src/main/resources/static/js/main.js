window.onload = async () => {

    if(localStorage.getItem("login") === null){

        location.replace("http://localhost:8080/auth")
    }
    else{
        const getEvents = await fetch("http://localhost:8080/api/events", {
            method: 'GET',
            headers: {
                "Authorization": `Bearer ${localStorage.getItem("token")}`
            }
        });
        const events = await getEvents.json();
        generateEvents(events);

        document.getElementsByClassName("events")[0].onclick = function(event) {
            let div = event.target;
            if (div.className == 'event-title') generateEventsDescription(div.parentNode);
            else if (div.className == 'event') generateEventsDescription(div);
            else return;
        };
    }


}

function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("login");
    location.assign("http://localhost:8080/auth");
}


function generateEvents(events) {
    let eventsList = document.getElementsByClassName("events")[0];
    for(let i = 0; i < events.length; i++) {
        let event = document.createElement("div");
        event.classList.add("event");
        event.id = `${events[i].id}`;

        

        let titleEvent = document.createElement("div");
        titleEvent.classList.add("event-title");

        titleEvent.innerText = events[i].title;

        event.appendChild(titleEvent);

        eventsList.appendChild(event);
    }
}


var activeEvent;

async function generateEventsDescription(div) {
    if (activeEvent) {
        activeEvent.classList.remove('active');
    }
    activeEvent = div;
    activeEvent.classList.add('active');

    let eventId = activeEvent.id;
    const getEvent = await fetch(`http://localhost:8080/api/events/${eventId}`, {
        method: 'GET',
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    });
    const event = await getEvent.json();

    console.log(event);

    document.getElementsByClassName("left-column")[0].classList.remove("hidden");
    document.getElementsByClassName("title")[0].innerText = event.title;
    document.getElementsByClassName("description")[0].innerHTML = event.description;

    document.getElementsByClassName("requirement")[0].innerText = event.requirement;
    document.getElementsByClassName("citys")[0].innerText += "Города: ";
    for(let i=0; i<event.citys.length; i++){
        document.getElementsByClassName("citys")[0].innerText += ` ${event.citys[i].city} `;
    }

}

function ShowRequirementForm() {
    document.getElementsByClassName("column-application")[0].classList.remove("hidden");
}



async function addApplication() {

    let username = localStorage.getItem("login");
    let eventId = activeEvent.id;

    const user = await fetch(`http://localhost:8080/api/${username}`, {
        method: 'GET',
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        },
    });
    let userObj=  await user.json();

    console.log(userObj);

    const event = await fetch(`http://localhost:8080/api/events/${eventId}`, {
        method: 'GET',
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        },
    });

    let eventObj2 =  await event.json();

    let eventObj = {
        id:eventObj2.id,
        title: eventObj2.title,
        description: eventObj2.description,
        requirement: eventObj2.requirement,
        citys: eventObj2.citys
    }
    let applicationObj = {

        name: document.getElementsByClassName("user-name")[0].value,
        lastName: document.getElementsByClassName("user-lastName")[0].value,
        description: document.getElementsByClassName("user-description")[0].value,
        users: userObj,
        events: eventObj,
    }


    const getEvent = await fetch("http://localhost:8080/api/applications", {
        method: 'POST',
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        },
        body: JSON.stringify(applicationObj),
    });


    if(getEvent.ok){
        getAllEvents();
        alert("Event добавлен");

    }
    else {
        let getErrorEvent = await getEvent.json();
        alert(getErrorEvent.errorMessage);
    }
}


function closeEvent(){
    document.getElementsByClassName("column-application")[0].classList.add("hidden");
}