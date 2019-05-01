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

        showTable(null);
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
function requestAllUsers() {

    // Perform our GET request
    let request = await fetch('getusers', {
        method: 'GET',
        headers: {
            'Authorization': localStorage.getItem('jwt'),
        }
    });

    if (request.status == 200) {



    } else {


    }
}