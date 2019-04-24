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

    console.log('in registerUser()');

    // Gather all the fields into an array
    let fieldsArr = new Array();
    fieldsArr[0] = document.getElementById('register-fn').value;
    fieldsArr[1] = document.getElementById('register-ln').value;
    fieldsArr[2] = document.getElementById('register-email').value;
    fieldsArr[3] = document.getElementById('register-username').value;
    fieldsArr[4] = document.getElementById('register-password').value;

    // Validate the fields
    if (fieldsValid(fieldsArr)) {

        // Create a user using the fields
        let user = {
            id: 0,
            firstName: fieldsArr[0],
            lastName: fieldsArr[1],
            email: fieldsArr[2],
            username: fieldsArr[3],
            password: fieldsArr[4],
            roleId: 1
        };

        console.log(user);

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

            let response = await request.json();

            console.log(response);
        }
    } else {
        // TODO
    }
}

/**
 * Helper function to validate all the fields in the 
 * register form. Will check if they are not empty, and they
 * follow proper form.
 */
function fieldsValid(fieldsArr) {

    // Loop through and validate for empty values
    for (let i = 0; i < fieldsArr.length; i++) {
        if (fieldsArr[i] == '')
            return false;
    }

    /*
        Taken from StackOverflow user 'Jaymon' ( 04/24/2019 ), regular expression 
        used for simple email validation. 
    */
    let emailRegex = /\S+@\S+\.\S+/;
    if (emailRegex.test(fieldsArr[2]) == false)
        return false;

    /*
        Taken from StackOverflow user 'Jason McCreary' ( 04/24/2019 ), regular expression 
        used for simple username validation.
        - the input username should only contains alphanumeric characters
        - Cannot be longer than 12 characters
    */
    let usernameRegex = /^[a-zA-Z0-9]+$/;
    let usernameLength = 12;
    if (usernameRegex.test(fieldsArr[3]) == false || fieldsArr[3].length > usernameLength)
        return false;

    // TODO add check for unique username

    /*
        Taken from StackOverflow user 'Minko Gechev' ( 04/24/2019 ), regular expression 
        used for simple password validation.
        - Contain at least 8 characters
        - Contain at least 1 number
        - Contain at least 1 lowercase character (a-z)
        - Contain at least 1 uppercase character (A-Z)
        - Contains only 0-9a-zA-Z
    */
    let passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/;
    if (passwordRegex.test(fieldsArr[4]) == false)
        return false;

    return true;
}