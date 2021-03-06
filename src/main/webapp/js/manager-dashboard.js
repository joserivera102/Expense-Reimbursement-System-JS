/**
 * manager-dashboard.js file used to configure the manager dashboard
 * page of the application.
 * 
 * @author Jose Rivera
 */
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

        // Show the right table
        document.getElementById('all-users-table').hidden = false;
        document.getElementById('requests-table').hidden = true;

        requestAllUsers();
    });

    document.getElementById('pending-requests-btn').addEventListener('click', function() {

        // Hide the alert message
        alertMessage(MANAGER_DASHBOARD_ALERT_ID, '', '', true);

        // Show the right table
        document.getElementById('all-users-table').hidden = true;
        document.getElementById('requests-table').hidden = false;

        getRequestByStatus(1);
    });

    document.getElementById('approved-requests-btn').addEventListener('click', function() {

        // Hide the alert message
        alertMessage(MANAGER_DASHBOARD_ALERT_ID, '', '', true);

        // Show the right table
        document.getElementById('all-users-table').hidden = true;
        document.getElementById('requests-table').hidden = false;

        getRequestByStatus(2);
    });

    document.getElementById('denied-requests-btn').addEventListener('click', function() {

        // Hide the alert message
        alertMessage(MANAGER_DASHBOARD_ALERT_ID, '', '', true);

        // Show the right table
        document.getElementById('all-users-table').hidden = true;
        document.getElementById('requests-table').hidden = false;

        getRequestByStatus(3);
    });
}

/**
 * Function that will make a GET request to the server
 * to retrieve all users.
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
 * Function to make a GET request to the server to retrieve all reimbursements by a status code.
 * 
 * @param {number} statusId The status id to lookup. ( 1 = PENDING, 2 = APPROVED, 3 = DENIED )
 */
async function getRequestByStatus(statusId) {

    // Perform our GET request
    let request = await fetch('reimbursements/' + statusId, {
        method: 'GET',
        headers: {
            'Authorization': localStorage.getItem('jwt'),
        }
    });

    if (request.status == 200) {

        let response = await request.json();

        // Check the status id to turn on or off the approve/deny buttons
        if (statusId == 1) // Status PENDING, buttons should be on
            document.getElementById('approve-deny-btn').hidden = false;
        else
            document.getElementById('approve-deny-btn').hidden = true;

        // Check to make sure we have at least one submission to display
        if (response.length > 0) {

            // Clear the table before building
            clearTable('requests-table-body');

            for (let i = 0; i < response.length; i++) {

                // Convert the timestamp
                response[i].dateSubmitted = timeConverter(response[i].dateSubmitted);

                // Date resovled can be null, if it is, just replace with 'N/A'
                if (response[i].dateResolved != null)
                    response[i].dateResolved = timeConverter(response[i].dateResolved);
                else
                    response[i].dateResolved = 'N/A';

                // Build the table with each submission
                buildRequestTable(response[i]);
            }
        } else {

            // Clear the table
            clearTable('requests-table-body');

            // Display the alert message
            alertMessage(MANAGER_DASHBOARD_ALERT_ID, INFO_ALERT_CLASS, 'No reimbursements to display', false);
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
 * Function to dynamically build the reimbursements table.
 * 
 * @param {any} submission The reimbursement element
 */
function buildRequestTable(submission) {

    // Get the table body
    let tbody = document.getElementById('requests-table-body');

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

    // If the status is pending, create the button elements
    if (submission.reimbursementStatus.status == 'PENDING') {

        // Create the button table element
        let buttons = document.createElement('td');

        // Create the buttons
        let approveBtn = document.createElement('button');
        let denyBtn = document.createElement('button');

        // Set the class of these buttons to use bootstrap
        approveBtn.setAttribute('class', 'btn btn-primary btn-sm');
        denyBtn.setAttribute('class', 'btn btn-warning btn-sm');

        // Set the text of the buttons
        approveBtn.innerHTML = 'Approve';
        denyBtn.innerHTML = 'Deny';

        // Set up event listeners for these buttons
        approveBtn.addEventListener('click', async function() {

            let response = await updateReimbursement(submission.id, 'approved');

            if (response != null) {

                // Convert the new date resolved
                response.dateResolved = timeConverter(response.dateResolved);

                // Update the reimbursement details in the table
                reimbursementDateResolved.innerHTML = response.dateResolved;
                reimbursementStatus.innerHTML = response.reimbursementStatus.status;
            }
        });

        denyBtn.addEventListener('click', async function() {

            let response = await updateReimbursement(submission.id, 'denied');

            if (response != null) {

                // Convert the new date resolved
                response.dateResolved = timeConverter(response.dateResolved);

                // Update the reimbursement details in the table
                reimbursementDateResolved.innerHTML = response.dateResolved;
                reimbursementStatus.innerHTML = response.reimbursementStatus.status;
            }
        });

        // Append the buttons
        buttons.append(approveBtn);
        buttons.append(denyBtn);

        // Append to the row
        trow.append(buttons);
    }

    // Append the entire row to the body
    tbody.append(trow);
}

/**
 * Function to make a PUT request to the server to update the reimbursement
 * status.
 * 
 * @param {String} id The id of the reimbursement to update
 * @param {String} status The status of the reimbursement to update
 */
async function updateReimbursement(id, status) {

    // Store our id and status in an array
    let info = [id, status];

    // Perfore the PUT request to the reimbursement
    let request = await fetch('changestatus', {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('jwt')
        },
        body: JSON.stringify(info)
    });

    if (request.status == 200) {

        // Display the alert message
        alertMessage(MANAGER_DASHBOARD_ALERT_ID, SUCCESS_ALERT_CLASS, 'Update Successful', false);
        return request.json();
    } else {

        // Display the alert message
        alertMessage(MANAGER_DASHBOARD_ALERT_ID, DANGER_ALERT_CLASS, 'Update Failed', false);
        return null;
    }
}