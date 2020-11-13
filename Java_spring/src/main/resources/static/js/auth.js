

async function auth() {

    let authObj = {
        username: document.getElementsByClassName("userName-input")[0].value,
        password: document.getElementsByClassName("passwordReg-input")[0].value
    }
    console.log("JKDGWHJK");
    if(authObj.username == "" || authObj.password == "") {
        alert("Input all fields");
    }
    const auth = await fetch("http://localhost:8080/auth", {
        method: 'POST',
        body: JSON.stringify(authObj),
        headers: {
            'Content-Type': 'application/json'
        }
    });
    const json = await auth.text();
    if(json === "OK") {
        location.assign('http://localhost:8080/mainPage');
    }
    else {
        alert("ЛАОДВЦЦ");
    }
}
