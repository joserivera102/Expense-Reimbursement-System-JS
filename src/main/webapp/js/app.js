/**
 * app.js file to help setup the event listeners for the page. Will also
 * be responsible for navigating pages and updating scripts. 
 * 
 * @author Jose Rivera
 */

// Global Variables
let APP_VIEW = document.getElementById('app-view');
let DYNAMIC_CSS = document.getElementById('dynamic-css');
let dynamicJS;

/**
 * Onload function to set up event listeners when the page loads.
 * Will also check for a JWT in the local storage and load the dashboard
 * exists.
 */
window.onload = function() {

    // Button event listeners
    document.getElementById('to-home').addEventListener('click', loadHome);
    document.getElementById('to-login').addEventListener('click', loadLogin);
    document.getElementById('to-register').addEventListener('click', loadRegister);
    document.getElementById('to-dashboard').addEventListener('click', function() {

        // Check for a JWT and role to load appropriate page
        if (localStorage.getItem('jwt') && localStorage.getItem('role') == 'EMPLOYEE') {
            loadDashboard();
        } else if (localStorage.getItem('jwt') && localStorage.getItem('role') == 'FINANCE_MANAGER') {
            loadManagerDashboard();
        }
    });

    document.getElementById('to-logout').addEventListener('click', logout);

    // Check for a JWT and role to load appropriate page
    if (localStorage.getItem('jwt') && localStorage.getItem('role') == 'EMPLOYEE') {
        loadDashboard();
    } else if (localStorage.getItem('jwt') && localStorage.getItem('role') == 'FINANCE_MANAGER') {
        loadManagerDashboard();
    } else {
        loadLogin();
    }
}

/**
 * Function that triggers when the home label is clicked.
 * Will reset all dynamic variables to default values.
 */
function loadHome() {
    APP_VIEW.innerHTML = '';
    DYNAMIC_CSS.href = '';
    dynamicJS.src = '';
}

/**
 * Function that loads the login view from the server and 
 * loads the login css and js scripts.
 */
async function loadLogin() {

    /*
        If a jwt is present, then a user is logged in. 
        We should not load the login page if this is the case
     */
    if (localStorage.getItem('jwt') == null) {
        APP_VIEW.innerHTML = await fetchView('login.view');
        DYNAMIC_CSS.href = 'css/login.css';
        changeScript('js/login.js');
    }
}

/**
 * Function that loads the register view from the server and
 * loads the register css and js scripts.
 */
async function loadRegister() {

    /*
        If a jwt is present, then a user is logged in. 
        We should not load the register page if this is the case
     */
    if (localStorage.getItem('jwt') == null) {
        APP_VIEW.innerHTML = await fetchView('register.view');
        DYNAMIC_CSS.href = 'css/register.css';
        changeScript('js/register.js');
    }
}

/**
 * Function that loads the dashboard view from the server and
 * loads the dashboard css and js scripts.
 */
async function loadDashboard() {

    APP_VIEW.innerHTML = await fetchView('dashboard.view');

    /*
         Dashboard should not load if the user was not authenticated.
         If the fetchview() did not return the proper text, leave this function call.
    */
    if (APP_VIEW.innerHTML == '')
        return;

    DYNAMIC_CSS.href = 'css/dashboard.css';
    changeScript('js/dashboard.js');
}

/**
 * Function that loads the manager dashboard view from the server and
 * loads the dashboard css and js scripts.
 */
async function loadManagerDashboard() {

    APP_VIEW.innerHTML = await fetchView('manager-dashboard.view');

    /*
         Dashboard should not load if the user was not authenticated.
         If the fetchview() did not return the proper text, leave this function call.
    */
    if (APP_VIEW.innerHTML == '')
        return;

    DYNAMIC_CSS.href = 'css/dashboard.css';
    changeScript('js/manager-dashboard.js');
}

function logout() {

    // Clear the local storage
    localStorage.clear();

    // Reload the login page
    loadLogin();
}

/**
 * Functions that dynamically changes the script tag at the end of the body.
 * Requires a new script name as a string. 
 * 
 * @param {String} src The name of the script to be loaded.
 */
function changeScript(src) {

    // Check the script tag
    if (document.getElementsByTagName('body')[0].lastChild == dynamicJS) {

        // Remove the previous script tag
        document.getElementsByTagName('body')[0].removeChild(dynamicJS);
    }

    // Create a new script tag
    dynamicJS = document.createElement('script');
    dynamicJS.src = src;

    // Append the new script element
    document.getElementsByTagName('body')[0].appendChild(dynamicJS);
}

/**
 * Function that makes a GET request to the server
 * to load the view based on the uri that is passed in. 
 * Contains the JWT in the header for authentication.
 * 
 * If the status returned is 401 ( Unauthorized ), reloads the login page.
 * @param {String} uri 
 */
async function fetchView(uri) {

    let response = await fetch(uri, {
        method: 'GET',
        mode: 'cors',
        headers: {
            'Authorization': localStorage.getItem('jwt')
        }
    });

    if (response.status == 401)
        loadLogin();

    return await response.text();
}