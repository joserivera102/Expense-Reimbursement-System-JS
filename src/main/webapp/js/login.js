console.log('login.js loaded');

// Constant Variables
const SUCCESS_ALERT_CLASS = "alert alert-success text-center";
const DANGER_ALERT_CLASS = "alert alert-danger text-center";

configureLogin();

/**
 * Function to set up the elements of the Login Page.
 */
function configureLogin() {

    console.log('in configureLogin()');

    // Hide the alert message
    document.getElementById('login-alert-msg').hidden = true;

    // Add event listener to button
    document.getElementById('submit-creds').addEventListener('click', loginUser);
}

async function loginUser() {

    console.log('in loginUser()');

    // Gather the fields, username and password
    let credentials = [
        document.getElementById('username-cred').value,
        document.getElementById('password-cred').value
    ];

    if (fieldsValid(credentials)) {

        // Perform our POST request
        let request = await fetch('auth', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(credentials)
        });

        if (request.status == 200) {

            // Display login success message
            document.getElementById('login-alert-msg').setAttribute('class', SUCCESS_ALERT_CLASS);
            document.getElementById('login-alert-msg').innerHTML = 'Login Successful!';
            document.getElementById('login-alert-msg').hidden = false;

            let response = await request.json();

            // TODO save this response to local storage (JWT)

            // Navigate to dashboard, calling loadDashboard() from app.js
            loadDashboard();

        } else {

            // Display login failure message
            document.getElementById('login-alert-msg').setAttribute('class', DANGER_ALERT_CLASS);
            document.getElementById('login-alert-msg').innerHTML = 'Login Failed!';
            document.getElementById('login-alert-msg').hidden = false;
        }
    } else {

        // Display login failure message
        document.getElementById('login-alert-msg').setAttribute('class', DANGER_ALERT_CLASS);
        document.getElementById('login-alert-msg').innerHTML = 'Please enter proper field values!';
        document.getElementById('login-alert-msg').hidden = false;
    }
}

function fieldsValid(fieldsArr) {

    // Loop through and validate for empty values
    for (let i = 0; i < fieldsArr.length; i++) {
        if (fieldsArr[i] == '')
            return false;
    }

    return true;
}