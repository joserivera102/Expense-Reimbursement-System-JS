/**
 * dashboard.js file used to configure the Dashboard for an employee.
 * 
 * @author Jose Rivera
 */
configureDashboard();

/**
 * Configuration of the Dashboard page. Sets up the forms and buttons.
 */
function configureDashboard() {

    // Display the user
    document.getElementById('current-user').innerHTML = 'Welcome, ' + localStorage.getItem('username');

    // Hide the forms
    document.getElementById('request-form').hidden = true;
    document.getElementById('submissions-form').hidden = true;
    document.getElementById('update-form').hidden = true;

    // Hide the alert message
    alertMessage(DASHBOARD_ALERT_ID, '', '', true);

    // Add event listeners to buttons
    document.getElementById('create-new-request-btn').addEventListener('click', function() {
        showForm('request-form');
    });

    document.getElementById('view-my-submissions-btn').addEventListener('click', function() {
        showForm('submissions-form');
        getAllReimbursements();
    });

    document.getElementById('update-profile-btn').addEventListener('click', function() {
        showForm('update-form');
    });

    // Request form event listeners
    document.getElementById('submit-request-btn').addEventListener('click', submitRequest);
    document.getElementById('clear-form-btn').addEventListener('click', clearRequestForm);

    // Update profile event listener
    document.getElementById('submit-update-btn').addEventListener('click', updateProfile);
}

async function submitRequest() {

    // Variable for the type id of the reimbursement
    let typeId;

    // Variable for the type name
    let typeName = document.getElementById('reimbursement-type').value;

    // Get the select input value
    switch (typeName) {

        case 'Lodging':
            typeId = 1;
            break;

        case 'Travel':
            typeId = 2;
            break;

        case 'Food':
            typeId = 3;
            break;

        case 'Other':
            typeId = 4;
            break;
    }

    // Gather the input fields
    let fieldsArr = [
        document.getElementById('reimbursement-amount').value,
        document.getElementById('reimbursement-description').value,
    ];

    if (checkForEmptyFields(fieldsArr)) {

        // Create the reimbursement status
        let reimbursementStatus = {
            id: 1,
            status: 'PENDING'
        }

        // Create the reimbursement type
        let reimbursementType = {
            id: typeId,
            type: typeName
        }

        // Create a reimbursement
        let reimbursement = {
            id: 0,
            amount: fieldsArr[0],
            dateSubmitted: Date.now(),
            dateResolved: null, // Date Resolved will be set when the reimbursement is resolved
            description: fieldsArr[1],
            authorId: localStorage.getItem('userId'),
            resolverId: 1, // All reimbursements need a default value for resolver ( Due to database constraints )
            reimbursementStatus: reimbursementStatus,
            reimbursementType: reimbursementType
        }

        // Perform our POST request
        let request = await fetch('submit', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('jwt')
            },
            body: JSON.stringify(reimbursement)
        });

        if (request.status == 200) {

            // Display the alert message
            alertMessage(DASHBOARD_ALERT_ID, SUCCESS_ALERT_CLASS, 'Submission Successful!', false);

            // After 2 seconds, turn off the message
            setTimeout(() => {
                alertMessage(DASHBOARD_ALERT_ID, '', '', true);
            }, 2000);

            // Clear our request form
            clearRequestForm();

        } else {

            // Display the alert message
            alertMessage(DASHBOARD_ALERT_ID, DANGER_ALERT_CLASS, 'Unable to process request!', false);
        }
    } else {

        // Display the alert message
        alertMessage(DASHBOARD_ALERT_ID, DANGER_ALERT_CLASS, 'Invalid Fields!', false);
    }
}

/**
 * Function that will perform a POST request to retrieve
 * all reimbursements for the current user.
 */
async function getAllReimbursements() {

    // Perform our POST request
    let request = await fetch('getall', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('jwt'),
            'UserId': localStorage.getItem('userId')
        }
    });

    if (request.status == 200) {

        let response = await request.json();

        // Check to make sure we have at least one submission to display
        if (response.length > 0) {

            // Clear the table before building
            clearTable('submission-table-body');

            for (let i = 0; i < response.length; i++) {

                // Convert the timestamp
                response[i].dateSubmitted = timeConverter(response[i].dateSubmitted);

                // Date resovled can be null, if it is, just replace with 'N/A'
                if (response[i].dateResolved != null)
                    response[i].dateResolved = timeConverter(response[i].dateResolved);
                else
                    response[i].dateResolved = 'N/A';

                // Build the table with each submission
                buildTable(response[i]);
            }

        } else {

            // Display the alert message
            alertMessage(DASHBOARD_ALERT_ID, INFO_ALERT_CLASS, 'No submissions to display', false);
        }

    } else {

        // Display the alert message
        alertMessage(DASHBOARD_ALERT_ID, DANGER_ALERT_CLASS, 'Unable to process request!', false);
    }
}

/**
 * Function to make a PUT request to update
 * the user information that we allow. Some fields can be
 * empty, if the user chooses not to update those.
 */
async function updateProfile() {

    // The array we send to the server with the updated info
    let updatedInfo = new Array();

    // Values
    let email;
    let currentPassword;
    let newPassword;
    let confirmPassword;

    // If the email field has a value, validate and add them to the array
    if (document.getElementById('update-email').value != '') {

        email = document.getElementById('update-email').value;

        if (validateEmail(email))
            updatedInfo.push(email);
        else {

            // Display the alert message
            alertMessage(DASHBOARD_ALERT_ID, DANGER_ALERT_CLASS, 'Email invalid format!', false);
            return;
        }
    }


    // If the password field has a value, then the other fields must have values
    if (document.getElementById('current-password').value != '') {

        // Gather the password fields
        currentPassword = document.getElementById('current-password').value;
        newPassword = document.getElementById('new-password').value;
        confirmPassword = document.getElementById('confirm-password').value;

        // Create an array with our passwords
        let arr = [
            currentPassword,
            newPassword,
            confirmPassword
        ];

        // Make sure they are not empty fields
        if (checkForEmptyFields(arr)) {

            // Check that the new password and confirm password match
            if (checkPasswordsMatch(newPassword, confirmPassword)) {

                // Before adding, make sure password follows proper form
                if (validatePassword(newPassword))
                    updatedInfo.push(newPassword);
                else {

                    // Display the alert message
                    alertMessage(DASHBOARD_ALERT_ID, DANGER_ALERT_CLASS, 'Password is in invalid format!', false);
                    return;
                }
            } else {

                // Display the alert message
                alertMessage(DASHBOARD_ALERT_ID, DANGER_ALERT_CLASS, 'Passwords do not match!', false);
                return;
            }
        } else {

            // Display the alert message
            alertMessage(DASHBOARD_ALERT_ID, DANGER_ALERT_CLASS, 'Invalid Fields!', false);
            return;
        }
    }

    // If the array length is 0, don't make the request
    if (updatedInfo.length == 0) {

        // Display the alert message
        alertMessage(DASHBOARD_ALERT_ID, INFO_ALERT_CLASS, 'Nothing to update, Please fill out the form', false);
        return;
    }

    // Make the PUT request to the server
    let request = await fetch('update', {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('jwt'),
            'UserId': localStorage.getItem('userId')
        },
        body: JSON.stringify(updatedInfo)
    });

    if (request.status == 200) {

        // Display the alert message
        alertMessage(DASHBOARD_ALERT_ID, SUCCESS_ALERT_CLASS, 'Update Successful', false);

        // Clear the form
        clearUpdateForm();
    } else {

        // Display the alert message
        alertMessage(DASHBOARD_ALERT_ID, DANGER_ALERT_CLASS, 'Could not process request!', false);
    }
}

/**
 * Function to clear the fields in the update form.
 */
function clearUpdateForm() {

    document.getElementById('update-email').value = '';
    document.getElementById('current-password').value = '';
    document.getElementById('new-password').value = '';
    document.getElementById('confirm-password').value = '';
}

/**
 * Function to dynamically create the table elements using
 * the response object for submissions.
 * 
 * @param {any} submission 
 */
function buildTable(submission) {

    // Get the table body
    let tbody = document.getElementById('submission-table-body');

    // Create the row
    let trow = document.createElement('tr');

    // First column will be reimbursement ID
    let reimbursementId = document.createElement('td');
    reimbursementId.innerHTML = submission.id;

    // Append it to the row
    trow.append(reimbursementId);

    // Second column is the author ID
    let authorId = document.createElement('td');
    authorId.innerHTML = submission.authorId;

    // Append it to the row
    trow.append(authorId);

    // Third column is the reimbursement type
    let reimbursementType = document.createElement('td');
    reimbursementType.innerHTML = submission.reimbursementType.type;

    // Append it to the row
    trow.append(reimbursementType);

    // Fourth column is the reimbursement amount
    let reimbursementAmount = document.createElement('td');
    reimbursementAmount.innerHTML = '$' + submission.amount;

    // Append to the row
    trow.append(reimbursementAmount);

    // Fifth column is the reimbursement Description
    let reimbursementDescription = document.createElement('td');
    reimbursementDescription.innerHTML = submission.description;

    // Append to the row
    trow.append(reimbursementDescription);

    // Sixth column is the reimbursement date submitted
    let reimbursementDateSubmitted = document.createElement('td');
    reimbursementDateSubmitted.innerHTML = submission.dateSubmitted;

    // Append to the row
    trow.append(reimbursementDateSubmitted);

    // Seventh column is the reimbursement date resolved
    let reimbursementDateResolved = document.createElement('td');
    reimbursementDateResolved.innerHTML = submission.dateResolved;

    // Append to the row
    trow.append(reimbursementDateResolved);

    // Eigth column is the reimbursement status
    let reimbursementStatus = document.createElement('td');
    reimbursementStatus.innerHTML = submission.reimbursementStatus.status;

    // Append to the row
    trow.append(reimbursementStatus);

    // Append the entire row to the body
    tbody.append(trow);
}

/**
 * Function to clear the request form and hide any alerts that may
 * be showing.
 */
function clearRequestForm() {

    // Clear the values
    document.getElementById('reimbursement-amount').value = '';
    document.getElementById('reimbursement-description').value = '';
}

/**
 * Function that uses the id of an element as the name to set the hidden attribute
 * of that element to either show or hide that form.
 * 
 * @param {String} name The name of the id that belongs to the form to display.
 */
function showForm(name) {

    switch (name) {

        case 'request-form':
            document.getElementById('request-form').hidden = false;
            document.getElementById('submissions-form').hidden = true;
            document.getElementById('update-form').hidden = true;
            break;

        case 'submissions-form':
            document.getElementById('request-form').hidden = true;
            document.getElementById('submissions-form').hidden = false;
            document.getElementById('update-form').hidden = true;
            break;

        case 'update-form':
            document.getElementById('request-form').hidden = true;
            document.getElementById('submissions-form').hidden = true;
            document.getElementById('update-form').hidden = false;
            break;
    }

    // Turn off the alert
    alertMessage(DASHBOARD_ALERT_ID, '', '', true);
}