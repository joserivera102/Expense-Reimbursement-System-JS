console.log('register.js loaded');
configureRegister();

/**
 * Function to set up and configure elements of the register page.
 */
function configureRegister() {

    console.log('in configureRegister()');

    // Hide the alert message on startup
    document.getElementById('register-alert-msg').hidden = true;

    // Configure the button
    document.getElementById('register-account-btn').addEventListener('click', registerUser);
}

/**
 * Function to send the user details to the server in order to register
 * them in the database. Will use the helper function 'fieldsValid' to 
 * make sure all fields are valid.
 */
async function registerUser() {

    // Validate the fields
    if (fieldsValid()) {
        // TODO
    } else {
        // TODO
    }
}

/**
 * Helper function to validate all the fields in the 
 * register form. Will check if they are not empty, and they
 * follow proper form.
 */
function fieldsValid() {

    // Empty value checks
    if (document.getElementById('register-fn').value == '') {
        // TODO
    }

    if (document.getElementById('register-ln').value == '') {
        // TODO
    }

    if (document.getElementById('register-email').value == '') {
        // TODO
    }

    if (document.getElementById('register-username').value == '') {
        // TODO
    }

    if (document.getElementById('register-password').value == '') {
        // TODO
    }

    return true;
}