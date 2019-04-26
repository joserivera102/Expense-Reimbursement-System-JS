console.log('dashboard.js loaded');

// Constant Variables
const SUCCESS_ALERT_CLASS = "alert alert-success text-center";
const DANGER_ALERT_CLASS = "alert alert-danger text-center";

configureDashboard();

/**
 * Configuration of the Dashboard page. Sets up the forms and buttons.
 */
function configureDashboard() {

    // Hide the forms
    document.getElementById('request-form').hidden = true;
    document.getElementById('submissions-form').hidden = true;
    document.getElementById('update-form').hidden = true;

    // Hide the alert message
    document.getElementById('dashboard-alert-msg').hidden = true;
    document.getElementById('dashboard-alert-msg').innerHTML = '';

    // Add event listeners to buttons
    document.getElementById('create-new-request-btn').addEventListener('click', function() {
        showForm('request-form');
    });

    document.getElementById('view-my-submissions-btn').addEventListener('click', function() {
        showForm('submissions-form');
    });

    document.getElementById('update-profile-btn').addEventListener('click', function() {
        showForm('update-form');
    });

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
            dateResolved: null,
            description: fieldsArr[1],
            authorId: 1,
            resolverId: 0,
            statusId: 1,
            typeId: type
        }

        console.log(reimbursement);

        // Perform our POST request
        let request = await fetch('submit', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(reimbursement)
        });

        if (request.status == 200) {

            let response = await request.json();

            console.log(response);

        } else {

        }

    } else {

        // Display the alert message
        document.getElementById('dashboard-alert-msg').hidden = false;
        document.getElementById('dashboard-alert-msg').setAttribute('class', DANGER_ALERT_CLASS);
        document.getElementById('dashboard-alert-msg').innerHTML = 'Invalid Fields!';
    }
}

function clearRequestForm() {

}

/**
 * Helper function to check if the fields are not empty.
 * 
 * @param {String Array} fieldsArr The array of strings to check.
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