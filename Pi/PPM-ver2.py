#blog.devcolor.org/heating-up-with-firebase-tutorial-on-how-to-integrate-firebase-into-your-app-6ce97440175d
#github.com/thisbejim/Pyrebase
import pyrebase
#www.reddit.com/r/Python/comments/3xh8p2/how_to_hide_user_input_with_asterisks/
import getpass
import time
import sys

def initFB():
    config = {
    "apiKey": "AIzaSyCO9AelHbfLCiKAjN_f8a2vZVkNwUMPmQE",
    "authDomain": "iot-enters-home-2ab63.firebaseapp.com",
    "databaseURL": "https://iot-enters-home-2ab63.firebaseio.com",
    "projectId": "iot-enters-home-2ab63",
    "storageBucket": "iot-enters-home-2ab63.appspot.com",
    "serviceAccount": "/home/ppm-xi/Desktop/Pi/IoT-Enters-Home.json"
    }

    firebase = pyrebase.initialize_app(config)

    email = input("Enter the email address: ")
    password = getpass.getpass("Enter the password: ")

    def mainCode():
        auth = firebase.auth()
        database = firebase.database()
        user = auth.sign_in_with_email_and_password(email, password)
        uToken = user['idToken']
        users = database.child("Users").get(uToken)

        theList = []
        mainUsers = str(users.val()).split(")")
        therUser = ""

        for user in mainUsers:
            if email in user:
                theUser = user[15:43]

        heating = database.child("Users").child(str(theUser)).child("System").child("Heating").get(uToken)
        lighting = database.child("Users").child(str(theUser)).child("System").child("Lighting").get(uToken)

        heatingObj = []
        lightingObj = []
        
        roomH = []
        roomL = []
        
        for h in heating.each():
            heatingObj.append(h.key())
            heatingObj.append(h.val())

        print("\n---------------------------------------- Heating ----------------------------------------")
        for thing in heatingObj:
            if "-" in thing:
                room, rObject = thing.split("-")
                if room in roomH:
                    print(rObject, end=": ")
                else:
                    roomH.append(room)
                    print("\n" + room)
                    print(rObject, end=": ")
            else:
                print(thing)

        for l in lighting.each():
            lightingObj.append(l.key())
            lightingObj.append(l.val())

        print("\n---------------------------------------- Lighting ----------------------------------------")
        for thing in lightingObj:
            if "-" in thing:
                room, rObject = thing.split("-")
                if room in roomL:
                    print(rObject, end=": ")
                else:
                    roomL.append(room)
                    print("\n" + room)
                    print(rObject, end=": ")
            else:
                print(thing)

        print("\n---------------------------------- Update System ---------------------------------- ")

        def updateDevice(deviceToUpdate):
            print(deviceToUpdate)
        
        yesOp = ["Yes", "yes", "YES", "Y", "y"]
        noOp = ["No", "no", "NO", "N", "n"]
        option = input("\nWould you like to update a device?")
        while option not in noOp:
            if option in yesOp:
                deviceToUpdate = ""
                devOpt = input("""\nChoose a device:
    A) Heating
    B) Lighting""")
                if devOpt.upper() == "A":
                    devices = []
                    num = 0
                    theDevice = ""
                    for device in heatingObj:
                        if "-" in device:
                            num = num + 1
                            theDevice = str(num) + ") " + device.replace("-", " - ") + ": "
                        else:
                            theDevice += device
                        devices.append(theDevice)

                    for dev in devices:
                        print(dev)
                            
                    roomOpt = input("""\nWhich device would you like to update?
    Press the number that matches the device: """)

                    for dev in devices:
                        if roomOpt in dev:
                            deviceToUpdate = dev
                        else:
                            pass
                        
                elif devOpt.upper() == "B":
                    devices = []
                    num = 0
                    theDevice = ""
                    for device in lightingObj:
                        if "-" in device:
                            num = num + 1
                            theDevice = str(num) + ") " + device.replace("-", " - ") + ": "
                        else:
                            theDevice += device
                        devices.append(theDevice)

                    for dev in devices:
                        print(dev)
                            
                    roomOpt = input("""\nWhich device would you like to update?
    Press the number that matches the device: """)

                    for dev in devices:
                        if roomOpt in dev:
                            deviceToUpdate = dev
                        else:
                            pass
                    
                else:
                    print("Invalid option")
                updateDevice(deviceToUpdate)
                
            elif option in noOp:
                pass
            else:
                print("Invalid option")
            option = input("\nWould you like to update a device?")

    try:
        mainCode()
    except:
        time.sleep(2)
        mainCode()

initFB()

