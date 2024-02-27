async function send() {
    let email = document.querySelector('input[name="email"]').value;
    let name = document.querySelector('input[name="name"]').value;
    let password = document.querySelector('input[name="password"]').value;
    const user = {
        "email": email,
        "name": name,
        "password": password
    };
    try {
        let response = await fetch('http://localhost:8080/save', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(user ),
        });
        if(response.status === 200){
            window.location.href='categories.html';
        }
        else{
            alert("This email already exists or something went wrong!");
            window.location.href='sign_up.html';

        }
       
    }
    catch (error) {
        console.log(error);
    }
}
