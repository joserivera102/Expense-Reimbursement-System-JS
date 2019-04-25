console.log('login.js loaded');
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

            let response = await request.json();

            console.log(response);

            // TODO

        } else {
            // TODO
        }
    } else {
        // TODO
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