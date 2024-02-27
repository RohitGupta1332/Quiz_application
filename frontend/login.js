async function loginUser() {
    const email = document.querySelector('input[name="email"]').value;
    const password = document.querySelector('input[name="password"]').value;
    try {
        const response = await fetch('http://localhost:8080/login?email=' + email + '&password=' + password, {
            method: 'GET',
        });
        if (response.ok) {
            window.location.href = "categories.html";
        }
        else {
            alert("Invalid username or password");
            window.location.href = "sign_in.html";
        }
    } catch (error) {
        console.log(error);
    }
}