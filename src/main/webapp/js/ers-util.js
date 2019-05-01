/**
 * Expense Reimbursement System util file. Contains functions to
 * help with validation and error checking.
 * 
 * @author Jose Rivera
 */

// Constant Variables for bootstrap alert classes
const SUCCESS_ALERT_CLASS = "alert alert-success text-center";
const INFO_ALERT_CLASS = "alert alert-info";
const DANGER_ALERT_CLASS = "alert alert-danger text-center";

/**
 * Function that takes in an array of values to check
 * for empty string values.
 * 
 * @param {String[]} arr Array of string values to check if valid.
 * 
 * @returns True if the fields contain values, false if any
 *          of the fields are empty strings.
 */
function checkForEmptyFields(arr) {

    // Loop through and check for empty values
    for (let i = 0; i < arr.length; i++)
        if (arr[i] == '')
            return false;

    return true;
}

/**
 * Function that takes in an email and validates it
 * against a regex to make sure the email follows
 * the proper form. Regex taken from StackOverflow
 * user 'Jaymon' ( 04/24/2019 ).
 * 
 * @param {String} email The email to validate.
 * 
 * @returns True if the email is valid, false if not valid.
 */
function validateEmail(email) {

    let emailRegex = /\S+@\S+\.\S+/;

    return (emailRegex.test(email));
}

/**
 * Function that takes in a username and validates it
 * against a regex to make sure the username follows
 * the proper form. Regex taken from StackOverflow
 * user 'Jason McCreary' ( 04/24/2019 ). Usernames must follow:
 *  - The input username should only contains alphanumeric characters.
 * 
 * @param {String} username The username to validate.
 * 
 * @returns True if the username is valid, false if not valid.
 */
function validateUsername(username) {

    let usernameRegex = /^[a-zA-Z0-9]+$/;

    return (usernameRegex.test(username))
}

/**
 * Function that takes in a password and validates it
 * against a regex to make sure the password follows
 * the proper form. Regex taken from StackOverflow
 * user 'Minko Gechev' ( 04/24/2019 ). Passwords must follow:
 *  - Contain at least 8 characters.
 *  - Contain at least 1 number.
 *  - Contain at least 1 lowercase character (a-z).
 *  - Contain at least 1 uppercase character (A-Z).
 *  - Contains only (0-9) (a-z) (A-Z).
 * 
 * @param {String} password The password to validate.
 * 
 * @returns True if the password is valid, false if not valid.
 */
function validatePassword(password) {

    let passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/;

    return (passwordRegex.test(password))
}

/**
 * Function to compare the two password fields to make sure they match before the user can
 * submit to update their password.
 * 
 * @param {String} updatedPasswordField The password taken from the update profile form:
 *                                      New Password.
 * @param {String} confirmPasswordField The password field taken from the update profile form:
 *                                      Confirm Password.
 * 
 * @returns True if the passwords match, false if they do not.
 */
function checkPasswordsMatch(updatedPasswordField, confirmPasswordField) {

    return (updatedPasswordField == confirmPasswordField);
}

/**
 * Function that displays the alert and configures it appropriately.
 * 
 * @param {String} id Id of the element to target.
 * @param {String} type Class attribute from bootstrap, either alert-danger or alert-success.
 * @param {String} message The message the alert displays.
 * @param {boolean} hidden boolean whether the message is hidden.
 */
function alertMessage(id, type, message, hidden) {

    document.getElementById(id).hidden = hidden;
    document.getElementById(id).setAttribute('class', type);
    document.getElementById(id).innerHTML = message;
}

/**
 * Helper function to convert unix time to local time.
 * 
 * @param {String} timestamp 
 */
function timeConverter(timestamp) {

    // Updates time from a unix timestamp to local time ( en-US )
    let date = new Date(timestamp).toLocaleDateString("en-US");
    let time = new Date(timestamp).toLocaleTimeString("en-US")
    let dateSubmitted = new String(date + ' @ ' + time);

    return dateSubmitted;
}