/**
 * app.js file to help setup the event listeners for the page. Will also
 * be responsible for navigating pages. 
 * 
 * @author Jose Rivera
 */

// Constant Variables
let APP_VIEW = document.getElementById('app-view');
let DYNAMIC_CSS = document.getElementById('dynamic-css');

// Global Variables
let dynamicJS;

/**
 * Onload function to set up event listeners when the page loads.
 */
window.onload = function() {

    document.getElementById('to-home').addEventListener('click', loadHome);
    document.getElementById('to-login').addEventListener('click', loadLogin);
    document.getElementById('to-register').addEventListener('click', loadRegister);
    document.getElementById('to-dashboard').addEventListener('click', loadDashboard);
    document.getElementById('to-contact-us').addEventListener('click', loadContactUs);
    document.getElementById('to-logout').addEventListener('click', logout);
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

    console.log('in loadLogin()');

    APP_VIEW.innerHTML = await fetchView('login.view');
    DYNAMIC_CSS.href = 'css/login.css';
    changeScript('js/login.js');
}

/**
 * Function that loads the register view from the server and
 * loads the register css and js scripts.
 */
async function loadRegister() {
    APP_VIEW.innerHTML = await fetchView('register.view');
    DYNAMIC_CSS.href = 'css/register.css';
    changeScript('js/register.js');
}

/**
 * Function that loads the dashboard view from the server and
 * loads the dashboard css and js scripts.
 */
async function loadDashboard() {
    APP_VIEW.innerHTML = await fetchView('dashboard.view');
    DYNAMIC_CSS.href = 'css/dashboard.css';
    changeScript('js/dashboard.js');
}

/**
 * Function that loads the contact us view from the server and
 * loads the contact us css and js scripts.
 */
async function loadContactUs() {
    APP_VIEW.innerHTML = await fetchView('contact-us.view');
    DYNAMIC_CSS.href = 'css/contact-us.css';
    changeScript('js/contact-us.js');
}

function logout() {

    // TODO
}

/**
 * Functions that dynamically changes the script tag at the end of the body.
 * Requires a new script name as a string. 
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
 * 
 * If the status returned is anything but 200, reload the login page
 * @param {String} uri 
 */
async function fetchView(uri) {

    let response = await fetch(uri);

    if (response.status == 401)
        loadLogin();

    return await response.text();
}