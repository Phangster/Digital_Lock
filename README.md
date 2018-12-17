# Lock Lock: A Smart Digital Lock

This project is an Android app designed for SUTD ISTD's 50.001 Introduction to Information Systems and Programming module. 
It was inspired by today’s home-sharing economy, as despite the success and ubiquity of such platforms as AirBnB and
CouchSurfing,there still does not exist an optimal way in which house access can be shared with guests -
that is, one which affords a sense of security for home-owners and is also mutually-convenient for both home-owners and guests.
Current methods include physical handover of keys or sharing of passcodes which presents several issues such as
inconvenience and lack of security. We therefore propose an IoT-based smart lock that helps to solve all the aforementioned
issues.

# How to use our code

Run the programme on your Android device. Upon registering and logging in, you will be taken to the "Key List" page. This
is the owner's page, where owners of locks are able to specify the exact lock and the user whom they are giving access to, 
as well as the date and time period in which they have access to the home for.

Upon clicking the hamburger menu at the top right hand corner and going to the "My Locks" page, one would be able to
see all the homes that he or she has been given access to. In this case, we have simulated an event in which the currently
logged in user has access to "Jin Kiat's Mansion" from 16th December 2018 to 16th January 2019. Upon pressing unlock,
the app will fetch the password associated with this lock (which is intentionally designed to be hidden from the user for
security and privacy issues), which will be passed to the Bluetooth access page. Pressing "LOCK LOCK UNLOCK LAH" after pairing
with the associated lock will cause the password to be passed as a string from the device to the lock, and upon receiving
the correct string, the associated lock will unlock automatically.

# Acknowledgements

We'd like to thank Professor Norman Lee, our course professor for the code that he uses in lessons as these have helped
us tremendously in terms of making our Android application. Additionally, we'd also like to thank both Professor Norman
Lee and Professor Ngai-Man Cheung for their advice throughout the term in terms of our project design. 
