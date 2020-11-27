window.onload = async () => {

    if(localStorage.getItem("login") !== "admin"){
        alert("У вас нет прав!!");
        location.replace("http://localhost:8080/mainPage")
    }
  
}


function closeEvent(){
    document.getElementsByClassName("showEvent")[0].classList.add("hidden");
}

function closeEventForm(){
    document.getElementsByClassName("addEvent-div")[0].classList.add("hidden");
}

function closeAll(){
    document.getElementsByClassName("allApplications")[0].classList.add("hidden");
    document.getElementsByClassName("allEvents")[0].classList.add("hidden");
    document.getElementsByClassName("addEvent-div")[0].classList.add("hidden");
    document.getElementsByClassName("showEvent")[0].classList.add("hidden");
}

async function AddEvent() {

    let citysNames = Array.from(document.getElementsByClassName("citys")[0].options)
        .filter(option => option.selected)
        .map(option => option.value);

    if(citysNames.length === 0){
        alert("Выберите город")
    }else {
        const getCity = await fetch(`http://localhost:8080/api/citys`, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${localStorage.getItem("token")}`
            },
        });

        let citysObj = await getCity.json();
        let citysList = [];

        for(let i = 0; i<citysNames.length; i++){
            for(let j = 0; j<citysObj.length; j++){
                if(citysNames[i] === citysObj[j].city){
                    citysList.push(citysObj[j]);
                    break;
                }
            }
        }

        let eventObj = {
            title: document.getElementsByClassName("eventTitle")[0].value,
            description: document.getElementsByClassName("eventDescription")[0].value,
            requirement: document.getElementsByClassName("eventRequirement")[0].value,
            citys:citysList,
        }

        const getEvent = await fetch("http://localhost:8080/api/admin/events/add", {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify(eventObj),
        });

        if(getEvent.ok){
            getAllEvents();
            alert("Event добавлен");

        }
        else {
            alert("проверьте все поля. Что-то пошло не так")
        }
    }



}

async function getAddEventForm(){
    closeAll()
    document.getElementsByClassName("addEvent-div")[0].classList.remove("hidden");


    const getCitys = await fetch("http://localhost:8080/api/citys", {
        method: 'GET',
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    });
    const citys = await getCitys.json();

    console.log(citys.length);

    let citysList = document.getElementsByClassName("citys")[0];

    citysList.innerHTML = ' ';
    for(let i = 0; i < citys.length; i++) {

        let option = document.createElement("option");
        option.innerText = citys[i].city;
        citysList.appendChild(option);
    }
}


async function getAllApplications(){
    closeAll()
    document.getElementsByClassName("allApplications")[0].classList.remove("hidden");


    const getApplications = await fetch("http://localhost:8080/api/admin/applications", {
        method: 'GET',
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    });

    const applications = await getApplications.json();
    generateApplications(applications);
}

function showEventFromId(){

}

function generateApplications(applications) {
  let applicationsList = document.getElementsByClassName("allApplications")[0];
  applicationsList.innerHTML = ' ';

  for(let i = 0; i < applications.length; i++) {
      let application = document.createElement("div");
      application.classList.add("application");
      application.id = `${applications[i].id}`;

      let left = document.createElement("div");
      left.classList.add("left");

      let right = document.createElement("div");
      right.classList.add("right");


      let email = document.createElement("div");
      email.classList.add("email");
      email.innerText = `${applications[i].users.username}`

      let name = document.createElement("div");
      name.classList.add("name");
      name.innerText = `${applications[i].name}`

      let lastName = document.createElement("div");
      lastName.classList.add("lastName");
      lastName.innerText = `${applications[i].lastName}`

      let description = document.createElement("div");
      description.classList.add("description");
      description.innerText = `${applications[i].description}`

      let accept = document.createElement("div");
      accept.innerHTML += `<button class = "accept" onclick = "AcceptApp('${application.id}')">Принять</button>`;


      let cancel = document.createElement("div");
      cancel.innerHTML += `<button class = "cancel" onclick = "CancelApp('${application.id}')">Отклонить</button>`;

      let event = document.createElement("div");
      event.innerHTML += `<button class = "${applications[i].events.id}" onclick = "showEventFromId('${applications[i].events.id}')">Event</button>`;



      application.appendChild(left);
      application.appendChild(right);

      left.appendChild(email);
      left.appendChild(name);
      left.appendChild(lastName);
      left.appendChild(description);
      left.appendChild(event);

      right.appendChild(accept);
      right.appendChild(cancel);

      applicationsList.appendChild(application);
  }
}

async function CancelApp(id){
    const delApp = await fetch(`http://localhost:8080/api/admin/applications/${id}`, {
        method: 'DELETE',
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    });
    if(delApp.ok){
        alert("Заявка удалена");
    }
    else {
        alert("Что-то пошло не так");
    }
    getAllApplications();
}

async function showEventFromId(id) {

    let eventId = id;
    const getEvent = await fetch(`http://localhost:8080/api/events/${eventId}`, {
        method: 'GET',
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    });
    const event = await getEvent.json();

    document.getElementsByClassName("showEvent")[0].classList.remove("hidden");
    document.getElementsByClassName("title")[0].innerText = event.title;
    document.getElementsByClassName("descriptions")[0].innerText = event.description;

    document.getElementsByClassName("requirement")[0].innerText = event.requirement;
    for(let i=0; i<event.citys.length; i++){
        document.getElementsByClassName("citys")[0].innerText += ` ${event.citys[i].city} `;
    }
}

async  function getAllEvents(){
    closeAll()
    document.getElementsByClassName("allEvents")[0].classList.remove("hidden");

    let eventsList = document.getElementsByClassName("allEvents")[0];
    eventsList.innerHTML = ' ';

    const getEvents = await fetch(`http://localhost:8080/api/events`, {
        method: 'GET',
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    });

    let events = await getEvents.json();

    for(let i = 0; i < events.length; i++) {

        let eventId = events[i].id ;

        let event = document.createElement("div")
        event.classList.add("event")

        let titleEvent = document.createElement("div")
        titleEvent.classList.add("titleEvent")
        titleEvent.innerText = events[i].title;

        let descriptionEvent = document.createElement("div")
        descriptionEvent.classList.add("descriptionEvent")
        descriptionEvent.innerText = events[i].description;

        let requirementEvent = document.createElement("div")
        requirementEvent.classList.add("requirementEvent")
        requirementEvent.innerText = events[i].requirement;


        let cancel = document.createElement("div");

        cancel.innerHTML += `<button class = "cancel" onclick = "deleteEventFromId('${eventId}')">Удалить</button>`;


        event.appendChild(titleEvent);
        event.appendChild(descriptionEvent);
        event.appendChild(requirementEvent);
        event.appendChild(cancel);

        eventsList.appendChild(event);
    }
}

async function deleteEventFromId(id){


    const delEvent = await fetch(`http://localhost:8080/api/admin/events/${id}`, {
        method: 'DELETE',
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    });

    if(delEvent.ok){
        alert("Event удален");
    }
    else{
        alert("Нужно обработать все заявки по данному Event")
    }

    getAllEvents();

}


async function AcceptApp(appId){

    let userEmail = document.getElementById(appId).firstChild.firstChild;

    const accept = await fetch(`http://localhost:8080/api/admin/applications/${appId}/accept`, {
        method: 'POST',
        body: userEmail.innerText,
        headers: {
            "Content-Type": "text/plain",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        },
    });



    if(accept.ok){
        alert("Сообщение отправлено на почту");
    }
    else{
        alert("Ошибка при отправке сообщения");
    }

    getAllApplications();
}

async function CancelApp(appId){

    let userEmail = document.getElementById(appId).firstChild.firstChild;

    const accept = await fetch(`http://localhost:8080/api/admin/applications/${appId}/cancel`, {
        method: 'POST',
        body: userEmail.innerText,
        headers: {
            "Content-Type": "text/plain",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        },
    });

    if(accept.ok){
        alert("Сообщение отправлено на почту");
    }
    else{
        alert("Ошибка при отправке сообщения");
    }

    getAllApplications();
}

