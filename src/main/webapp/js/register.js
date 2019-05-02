/**
 * register.js file to configure and set up the register
 * page of the application.
 * 
 * @author Jose Rivera
 */
configureRegister();

/**
 * Function to set up and configure elements of the register page.
 */
function configureRegister() {

    // Hide the alert message
    alertMessage(REGISTER_ALERT_ID, '', '', true);

    // Configure the button
    document.getElementById('register-account-btn').addEventListener('click', registerUser);
}

/**
 * Function to send the user details to the server in order to register
 * them in the database. Will use the helper function 'fieldsValid' to 
 * make sure all fields are valid.
 */
async function registerUser() {

    // Gather all the fields into an array
    let fieldsArr = new Array();
    fieldsArr[0] = document.getElementById('register-fn').value;
    fieldsArr[1] = document.getElementById('register-ln').value;
    fieldsArr[2] = document.getElementById('register-email').value;
    fieldsArr[3] = document.getElementById('register-username').value;
    fieldsArr[4] = document.getElementById('register-password').value;

    if (checkForEmptyFields(fieldsArr)) {

        // Validate the email
        if (!validateEmail(fieldsArr[2])) {

            // Display an alert
            alertMessage(REGISTER_ALERT_ID, DANGER_ALERT_CLASS, 'Email is invalid', false);
            return;
        }

        // Validate the username
        if (!validateUsername(fieldsArr[3])) {

            // Display an alert
            alertMessage(REGISTER_ALERT_ID, DANGER_ALERT_CLASS, 'Username is invalid', false);
            return;
        }

        // Validate the password
        if (!validatePassword(fieldsArr[4])) {

            // Display an alert
            alertMessage(REGISTER_ALERT_ID, DANGER_ALERT_CLASS, 'Password is invalid', false);
            return;
        }

        // Create a role for the user (all users registering will be employees)
        let role = {
            id: 1,
            role: 'EMPLOYEE'
        };

        // Create a user using the fields
        let user = {
            id: 0,
            firstName: fieldsArr[0],
            lastName: fieldsArr[1],
            email: fieldsArr[2],
            username: fieldsArr[3],
            password: fieldsArr[4],
            role: role
        };

        // Perform our POST request
        let request = await fetch('add', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        });

        // Check our status
        if (request.status == 200) {

            // Display the alert message
            alertMessage(REGISTER_ALERT_ID, SUCCESS_ALERT_CLASS, 'Registration Successful!', false);

            // Save the JWT into local storage
            localStorage.setItem('jwt', request.headers.get('Authorization'));

            // Save the user id into local storage
            localStorage.setItem('userId', request.headers.get('UserId'));

            // Save the username into local storage
            localStorage.setItem('username', request.headers.get('Username'));

            // Save the role into local storage
            localStorage.setItem('role', request.headers.get('Role'));

            // Navigate to dashboard
            loadDashboard();

        } else {

            // Display the alert message
            alertMessage(REGISTER_ALERT_ID, DANGER_ALERT_CLASS, 'Registration Failure!', false);
        }
    } else {

        // Display the alert message
        alertMessage(REGISTER_ALERT_ID, DANGER_ALERT_CLASS, 'Invalid Fields!', false);
    }
}