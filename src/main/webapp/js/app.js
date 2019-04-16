/**
 * app.js file to help setup the event listeners for the page. Will also
 * be responsible for navigating pages. 
 */

// Constant Variables
let APP_VIEW = document.getElementById('app-view');
let DYNAMIC_CSS = document.getElementById('dynamic-css');

// Global Variables
let dynamicJS;

/**
 * Onload function to setup event listeners when the page loads.
 */
window.onload() {

    document.getElementById('to-home').addEventListener('click', loadHome);
    document.getElementById('to-login').addEventListener('click', loadLogin);
    document.getElementById('to-register').addEventListener('click', loadRegister);
    document.getElementById('to-dashboard').addEventListener('click', loadDashboard);
    document.getElementById('to-contact-us').addEventListener('click', loadContactUs);
    document.getElementById('to-logout').addEventListener('click', loadLogout);
}

/**
 * Function that triggers when the home label is clicked. Will reset all dynamic variables
 * to default values.
 */