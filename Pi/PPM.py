#blog.devcolor.org/heating-up-with-firebase-tutorial-on-how-to-integrate-firebase-into-your-app-6ce97440175d
#github.com/thisbejim/Pyrebase
import pyrebase
#www.reddit.com/r/Python/comments/3xh8p2/how_to_hide_user_input_with_asterisks/
import getpass

config = {
"apiKey": "AIzaSyCO9AelHbfLCiKAjN_f8a2vZVkNwUMPmQE",
"authDomain": "iot-enters-home-2ab63.firebaseapp.com",
"databaseURL": "https://iot-enters-home-2ab63.firebaseio.com",
"projectId": "iot-enters-home-2ab63",
"storageBucket": "iot-enters-home-2ab63.appspot.com",
"serviceAccount": "/home/kyle/Desktop/IoT-Enters-Home.json"
}

firebase = pyrebase.initialize_app(config)

email = input("Enter the email address: ")
password = getpass.getpass("Enter the password: ")

auth = firebase.auth()
database = firebase.database()
user = auth.sign_in_with_email_and_password(email, password)

users = database.child("Users").get(user['idToken'])

print(users.val())
