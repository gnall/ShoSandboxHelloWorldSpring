importScripts('https://www.gstatic.com/firebasejs/3.5.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/3.5.0/firebase-messaging.js');
 
var config = {
    apiKey: "AIzaSyBHw638MUKXjZIUTZO_146nxtYpmwDhWpY",
    authDomain: "sho-sandbox-flex.firebaseapp.com",
    messagingSenderId: "930764944572",
};
firebase.initializeApp(config);
 
var messaging = firebase.messaging();
messaging.setBackgroundMessageHandler(function (payload) {
    var dataFromServer = JSON.parse(payload.data.notification);
    var notificationTitle = dataFromServer.title;
    var notificationOptions = {
        body: dataFromServer.body,
        icon: dataFromServer.icon,
        data: {
            url:dataFromServer.url
        }
    };
    return self.registration.showNotification(notificationTitle,
        notificationOptions);
});

self.addEventListener("notificationclick", function (event)
{
    var urlToRedirect = event.notification.data.url;
    event.notification.close();
    event.waitUntil(self.clients.openWindow(urlToRedirect));
});
