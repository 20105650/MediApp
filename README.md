Medicare is a mobile app with three types of users. Patients, Doctors, and an Admin. It has the functionalities like registration, login, profile update, appointment booking, etc. Firebase Realtime Database is used as database and Firebase cloud storage is used for storing images.
1.1 Third Party APIs Used
Realtime Database is used to store data. The Firebase Realtime Database is a database stored in the cloud. Every connected client receives real-time data synchronization in JSON format.

Cloud Storage for Firebase is designed for app developers who need to store and serve user-generated material, such images or videos, and is based on quick and secure Google Cloud infrastructure. It is used to store image here.

1.2 APP Features

Splash Screen Medicare is launching with a splash screen with a logo and a small text. 
Login Page  After a specific interval splash screen redirects to the Login page. From there a user can login to app or redirect to the signup page.
Signup Page Successful signup functionality registers a user in the app.
Navigation Drawer is implemented for smooth redirection
Admin home page shows buttons like users, bookings, categories, slots, reports.
•	Categories – List all existing categories. Create new specialization for doctors, update and delete categories.
•	Bookings – List all available bookings.
•	Slots - List all existing slots. Create new booking slots , update and delete slots.
•	Reports – List all available reports.

Patient Home page This page lists all doctors in a list view. Onclick of each redirect to create an appointment page.
Patient appointment Page This page list all upcoming and cancelled appointments.
Doctor Home page This page lists all appointments in a list view. 
Doctor Report Page This page list all reports. Doctor can create a report after appointment completed. 

Model View View-Model (MVVM)structure used to develop the app.

Swipe to left is enabled for admin category to edit category.
Swipe to right is enabled for admin category to delete category.
Search is enabled for doctors list in patent home page and admin user list page 
