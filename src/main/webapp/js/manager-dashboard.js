/**
 * manager-dashboard.js file used to configure the manager dashboard
 * page of the application.
 * 
 * @author Jose Rivera
 */

// Constant variable for the alert id
let MANAGER_DASHBOARD_ALERT_ID = "manager-dashboard-alert-msg";

configureManagerDashboard();

/**
 * Configuration of the Dashboard page. Sets up the forms and buttons.
 */
function configureManagerDashboard() {

    // Display the user
    document.getElementById('current-user').innerHTML = 'Welcome, ' + localStorage.getItem('username');

    // Hide the alert message
    alertMessage(MANAGER_DASHBOARD_ALERT_ID, '', '', true);

    // Hide the tables
    document.getElementById('all-users-table').hidden = true;
    document.getElementById('requests-table').hidden = true;

    document.getElementById('get-all-users-btn').addEventListener('click', function() {

        // Hide the alert message
        alertMessage(MANAGER_DASHBOARD_ALERT_ID, '', '', true);

        document.getElementById('all-users-table').hidden = false;
        document.getElementById('requests-table').hidden = true;

        requestAllUsers();
    });

    document.getElementById('pending-requests-btn').addEventListener('click', function() {

        // Hide the alert message
        alertMessage(MANAGER_DASHBOARD_ALERT_ID, '', '', true);
    });

    document.getElementById('approved-requests-btn').addEventListener('click', function() {

        // Hide the alert message
        alertMessage(MANAGER_DASHBOARD_ALERT_ID, '', '', true);
    });

    document.getElementById('denied-requests-btn').addEventListener('click', function() {

        // Hide the alert message
        alertMessage(MANAGER_DASHBOARD_ALERT_ID, '', '', true);
    });
}

/**
 * Function that will make a GET request to get all users.
 */
async function requestAllUsers() {

    // Perform our GET request
    let request = await fetch('getusers', {
        method: 'GET',
        headers: {
            'Authorization': localStorage.getItem('jwt'),
        }
    });

    if (request.status == 200) {

        let response = await request.json();

        // Check to make sure we have at least one submission to display
        if (response.length > 0) {

            // Clear the table before building
            clearTable('users-table-body');

            for (let i = 0; i < response.length; i++) {

                // Build the table with each submission
                buildUsersTable(response[i]);
            }
        } else {

            // Display the alert message
            alertMessage(MANAGER_DASHBOARD_ALERT_ID, INFO_ALERT_CLASS, 'No Users to display', false);
        }
    } else {

        // Display the alert message
        alertMessage(MANAGER_DASHBOARD_ALERT_ID, DANGER_ALERT_CLASS, 'Unable to process request!', false);
    }
}

/**
 * Function to dynamically build the users table.
 * 
 * @param {any} submission The user element.
 */
function buildUsersTable(submission) {

    // Get the table body
    let tbody = document.getElementById('users-table-body');

    // Create the row
    let trow = document.createElement('tr');

    // First column will be employee ID
    let employeeId = document.createElement('td');
    employeeId.innerHTML = submission.id;

    // Append it to the row
    trow.append(employeeId);

    // Second column is the first name
    let firstName = document.createElement('td');
    firstName.innerHTML = submission.firstName;

    // Append it to the row
    trow.append(firstName);

    // Third column is the last name
    let lastName = document.createElement('td');
    lastName.innerHTML = submission.lastName;

    // Append it to the row
    trow.append(lastName);

    // Fourth column is the username
    let username = document.createElement('td');
    username.innerHTML = submission.username;

    // Append it to the row
    trow.append(username);

    // Fifth column is the email
    let email = document.createElement('td');
    email.innerHTML = submission.email;

    // Append it to the row
    trow.append(email);

    // Sixth column is the Role
    let role = document.createElement('td');
    role.innerHTML = submission.role.role;

    // Append it to the row
    trow.append(role);

    // Append the entire row to the body
    tbody.append(trow);
}

/**
 * Function to clear the table and get it ready for new values.
 * 
 * @param {String} id The ID of the table element.
 */
function clearTable(id) {

    // This will clear the values in the table
    while (document.getElementById(id).firstChild) {
        document.getElementById(id).removeChild(document.getElementById(id).firstChild);
    }
}