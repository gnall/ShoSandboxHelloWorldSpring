importScripts('https://www.gstatic.com/firebasejs/3.5.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/3.5.0/firebase-messaging.js');
 
var config = {
    apiKey: "AIzaSyBcG6ROqsakbdDPTRjDiU-05KKpkEIxGN4",
    authDomain: "shosandboxspring.firebaseapp.com",
    messagingSenderId: "853094636535",
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
