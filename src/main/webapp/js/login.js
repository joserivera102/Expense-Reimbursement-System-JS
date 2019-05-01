/**
 * login.js file used to control and configure the login
 * page for the application.
 * 
 * @author Jose Rivera
 */

// Constant variable for the alert id
let LOGIN_ALERT_ID = "login-alert-msg";

configureLogin();

/**
 * Function to set up the elements of the Login Page.
 */
function configureLogin() {

    // Hide the alert message
    alertMessage(LOGIN_ALERT_ID, '', '', true);

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

    if (checkForEmptyFields(credentials)) {

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
            alertMessage(LOGIN_ALERT_ID, SUCCESS_ALERT_CLASS, 'Login Successful!', false);

            // Save the JWT into local storage
            localStorage.setItem('jwt', request.headers.get('Authorization'));

            // Save the user id into local storage
            localStorage.setItem('userId', request.headers.get('UserId'));

            // Save the username into local storage
            localStorage.setItem('username', request.headers.get('Username'));

            // Check the role of the logged in user
            if (request.headers.get('Role') == 'EMPLOYEE') {
                localStorage.setItem('role', request.headers.get('Role'));
                loadDashboard();
            } else {
                localStorage.setItem('role', request.headers.get('Role'));
                loadManagerDashboard();
            }

        } else {

            // Display the alert message
            alertMessage(LOGIN_ALERT_ID, DANGER_ALERT_CLASS, 'Login Failed!', false);
        }
    } else {

        // Display the alert message
        alertMessage(LOGIN_ALERT_ID, DANGER_ALERT_CLASS, 'Invalid Fields!', false);
    }
}