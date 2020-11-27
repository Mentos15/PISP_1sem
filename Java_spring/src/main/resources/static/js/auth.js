

async function auth() {

    let authObj = {
        username: document.getElementsByClassName("userName-input")[0].value,
        password: document.getElementsByClassName("passwordReg-input")[0].value
    }

    if(authObj.username == "" || authObj.password == "") {
        alert("Input all fields");
    }
    const token = await fetch("http://localhost:8080/auth", {
        method: 'POST',
        body: JSON.stringify(authObj),
        headers: {
            'Content-Type': 'application/json'
        }

    });



    if(token.ok) {
        const json = await token.json();
        localStorage.setItem("token", json.token);
        localStorage.setItem("login", authObj.username);
        location.assign('http://localhost:8080/mainPage');
    }
    else {
        alert("Error");
    }
}
