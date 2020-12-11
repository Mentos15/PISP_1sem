

async function register() {

    let registerObj = {
        username: document.getElementsByClassName("userName-input")[0].value,
        password: document.getElementsByClassName("passwordReg-input")[0].value,
    }

    if(registerObj.login == "" || registerObj.password == "") {
        alert("Input all fields");
    }
    const register = await fetch("http://localhost:8080/register", {
        method: 'POST',
        body: JSON.stringify(registerObj),
        headers: {
            'Content-Type': 'application/json'
        }
    });
    if(register.ok) {
        location.assign('http://localhost:8080/auth');
    }
    else {
        let getErrorEvent = await register.json();
        alert(getErrorEvent.errorMessage);
    }
}
