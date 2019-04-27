console.log('login.js loaded');

configureLogin();

/**
 * Function to set up the elements of the Login Page.
 */
function configureLogin() {

    console.log('in configureLogin()');

    // Hide the alert message
    alertMessage('', '', true);

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

            // Display the alert message
            alertMessage(SUCCESS_ALERT_CLASS, 'Login Successful!', false);

            let response = await request.json();

            // Save the JWT into local storage
            localStorage.setItem('jwt', request.headers.get('Authorization'));

            // Navigate to dashboard, calling loadDashboard() from app.js
            loadDashboard();

        } else {

            // Display the alert message
            alertMessage(DANGER_ALERT_CLASS, 'Login Failed!', false);
        }
    } else {

        // Display the alert message
        alertMessage(DANGER_ALERT_CLASS, 'Invalid Fields!', false);
    }
}

/**
 * Helper function to check if the fields are not empty.
 * 
 * @param {String Array} fieldsArr The array of strings to check.
 * 
 * @return True if the fields are valid, false if not.
 */
function fieldsValid(fieldsArr) {

    // Loop through and validate for empty values
    for (let i = 0; i < fieldsArr.length; i++) {
        if (fieldsArr[i] == '')
            return false;
    }

    return true;
}

/**
 * Function that displays the alert and configures it appropriately.
 * 
 * @param {String} type Class attribute from bootstrap, either alert-danger or alert-success.
 * @param {String} message The message the alert displays.
 * @param {boolean} hidden boolean whether the message is hidden.
 */
function alertMessage(type, message, hidden) {

    document.getElementById('login-alert-msg').hidden = hidden;
    document.getElementById('login-alert-msg').setAttribute('class', type);
    document.getElementById('login-alert-msg').innerHTML = message;
}