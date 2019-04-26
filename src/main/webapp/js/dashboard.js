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