console.log('dashboard.js loaded');

configureDashboard();

/**
 * Configuration of the Dashboard page. Sets up the forms and buttons.
 */
function configureDashboard() {

    // Display the user
    document.getElementById('current-user').innerHTML = localStorage.getItem('username');

    // Hide the forms
    document.getElementById('request-form').hidden = true;
    document.getElementById('submissions-form').hidden = true;
    document.getElementById('update-form').hidden = true;

    // Hide the alert message
    alertMessage('', '', true);

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
}

async function submitRequest() {

    // Variable for the type id of the reimbursement
    let type;

    // Get the select input value
    switch (document.getElementById('reimbursement-type').value) {

        case 'Lodging':
            type = 1;
            break;

        case 'Travel':
            type = 2;
            break;

        case 'Food':
            type = 3;
            break;

        case 'Other':
            type = 4;
            break;
    }

    // Gather the input fields
    let fieldsArr = [
        document.getElementById('reimbursement-amount').value,
        document.getElementById('reimbursement-description').value,
    ];

    if (fieldsValid(fieldsArr)) {

        // Create a reimbursement
        let reimbursement = {
            id: 0,
            amount: fieldsArr[0],
            dateSubmitted: Date.now(),
            dateResolved: null, // Date Resolved will be set when the reimbursement is resolved
            description: fieldsArr[1],
            authorId: localStorage.getItem('userId'),
            resolverId: 1, // All reimbursements need a default value for resolver ( Due to database constraints )
            statusId: 1, // All reimbursements created this way have a status of ( 1 PENDING )
            typeId: type
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
            alertMessage(SUCCESS_ALERT_CLASS, 'Submission Successful!', false);

            // Clear our request form
            clearRequestForm();

        } else {

            // Display the alert message
            alertMessage(DANGER_ALERT_CLASS, 'Unable to process request!', false);
        }
    } else {

        // Display the alert message
        alertMessage(DANGER_ALERT_CLASS, 'Invalid Fields!', false);
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

        console.log(response);

    } else {

        // Display the alert message
        alertMessage(DANGER_ALERT_CLASS, 'Unable to process request!', false);
    }
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
 * Helper function to check if the fields are not empty.
 * 
 * @param {String[]} fieldsArr The array of strings to check.
 * 
 * @return True if the fields are valid, false if not.
 */
function fieldsValid(fieldsArr) {

    for (let i = 0; i < fieldsArr.length; i++) {
        if (fieldsArr[i] == '')
            return false;
    }

    return true;
}

/**
 * Helper function used to show a requested form when clicked.
 * @param {String} name The name of the form to show ( using id as name ).
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
}

/**
 * Function that displays the alert and configures it appropriately.
 * 
 * @param {String} type Class attribute from bootstrap, either alert-danger or alert-success.
 * @param {String} message The message the alert displays.
 * @param {boolean} hidden boolean whether the message is hidden.
 */
function alertMessage(type, message, hidden) {

    document.getElementById('dashboard-alert-msg').hidden = hidden;
    document.getElementById('dashboard-alert-msg').setAttribute('class', type);
    document.getElementById('dashboard-alert-msg').innerHTML = message;
}