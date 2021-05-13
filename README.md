EMRP Axelor project setup
================================

Prerequisite for emrp data excess 
---------------------------------------
1. postgres SQL for data management https://www.postgresql.org/download/windows/
2. Git for version control https://desktop.github.com/
3. Python for API call https://www.python.org/downloads/
4. Insomaina for read Cookies https://insomnia.rest/download
5. Tomcat Server 8.5.0 for run project on localhost https://tomcat.apache.org/tomcat-8.5-doc/index.html
6. Eclipse for change data-model https://www.eclipse.org/downloads/
7. Java minimum version 8

EMRP Axelor setup section going to show project direcotry structure, For that need to clone this branch shown below 
 * https://github.com/rolfbecker/MIE_EA.02_EMRP_WS2020/tree/mqtt_axelor_root
 * https://github.com/rolfbecker/MIE_EA.02_EMRP_WS2020/tree/mqtt_axelor

Following steps to run MQTT-Axelor project on localhost

Step 1: clone root project 
```bash
$ git clone https://github.com/rolfbecker/MIE_EA.02_EMRP_WS2020.git
$ cd MIE_EA.02_EMRP_WS2020/
$ git fetch origin mqtt_axelor_root
$ git pull origin mqtt_axelor_root
$ git co mqtt_axelor_root
```
From above command clone this "MIE_EA.02_EMRP_WS2020" folder and change branch with mqtt_axelor_root  

Step 2: 
1) Change The folder name "MIE_EA.02_EMRP_WS2020" to "axelor-erp"
2) Write password in file "axelor-erp/src/main/resources/application.properties" password is given on discord 
3) Run following command 
```bash
$ ./gradlew build
```
Step 3: Now set up sub modules for that go to location "axelor-erp/modules/abs"
clone sub modules and change the branch 
```bash
$ git clone https://github.com/rolfbecker/MIE_EA.02_EMRP_WS2020.git
$ cd MIE_EA.02_EMRP_WS2020/
$ git fetch origin mqtt_axelor
$ git pull origin
$ git co git co mqtt_axelor
```
Important : 

1) Cut folder "MIE_EA.02_EMRP_WS2020" from "axelor-erp/modules/abs/" and past on "axelor-erp/modules/"
2) Delete empty folder "abs"
3) Rename folder from "MIE_EA.02_EMRP_WS2020" to "abs" on location "axelor-erp/modules/"

All set up done ....next run command for build project

Setp 4:

Run command from location "axelor-erp/"
```bash
$ ./gradlew clean classes build -x test cleanEclipse eclipse
$ ./gradlew --no-daemon run
```
It takes some time to run project, you find link on terminal http://localhost:8080/axelor-erp
or past this link to browser 


EMRP Axelor project setup for Windows
=======================================

Step 1: clone root project 
```bash
$ git clone https://github.com/rolfbecker/MIE_EA.02_EMRP_WS2020.git
$ cd MIE_EA.02_EMRP_WS2020/
$ git fetch origin mqtt_axelor_root
$ git pull origin mqtt_axelor_root
$ git co mqtt_axelor_root
```
From above command clone this "MIE_EA.02_EMRP_WS2020" folder and change branch with mqtt_axelor_root  

Step 2: 
1) Change The folder name "MIE_EA.02_EMRP_WS2020" to "axelor-erp"
2) Write password in file "axelor-erp/src/main/resources/application.properties" password is given on discord 
3) Run following command for windows from CMD 
```bash
$ gradlew.bat clean classes build -x test cleanEclipse eclipse
```
Step 3: Now set up sub modules for that go to location "axelor-erp/modules/abs"
clone sub modules and change the branch 
```bash
$ git clone https://github.com/rolfbecker/MIE_EA.02_EMRP_WS2020.git
$ cd MIE_EA.02_EMRP_WS2020/
$ git fetch origin mqtt_axelor
$ git pull origin
$ git co git co mqtt_axelor
```
Important : 

1) Cut folder "MIE_EA.02_EMRP_WS2020" from "axelor-erp/modules/abs/" and past on "axelor-erp/modules/"
2) Delete empty folder "abs"
3) Rename folder from "MIE_EA.02_EMRP_WS2020" to "abs" on location "axelor-erp/modules/"

All set up done, next run command for build project Run Following command 

```bash
$ gradlew.bat clean classes build -x test cleanEclipse eclipse
$ gradlew.bat --no-daemon run
```

Step 4 is going to show the Eclipse IDE setup for modify project as per further need 
Setp 4: Eclipse 
1) Import project with gradle import select root project folder
2) Assign server tomcat 8.5 and load project into server
    - Change configuration timeout with time limit(increase from 45 to 4500)
    - Running server port 8080
3) Debug server environment 

Next, load project on browser with  http://localhost:8080/axelor-erp


Sensore data management
================================

Data comes from MQTT broker "eu.thethings.network" which is running on 8883 port for retrive sensore data, Run pythons script which is contain "hsrw_iotlab_lse01" ttn_app_id for this project. Data retrive with json format that can observe in terminal or CMD depending on operating system.

Source code given on discord filename "TTN_HSRW_IoT_API".
Install python library for run the script.
1. pip3 install paho-mqtt python-etcd
2. pip install requests

API access for send JSON data to the axelor application.
```bash
    print("PAYLOAD: %s" % (payload))
    data = {
     'domain' : 'com.axelor.apps.rku.db.Account'
    }
    url = "http://localhost:8080/axelor-erp/ws/action/"
    headers = {
     "Content-Type": "application/json",
     "Cookie": "JSESSIONID=0AFD014B6EFCE3ACB5A3B7A95C1FCFD9"
    }
    body = {
     "action": "com.axelor.apps.mqtt.web.RequestDataController:getSensorData",
     "data": payload
    }
```

From above API need to give Seesion Cookie for authentication. Insomania API testing tool use to retrive session Cookie.
LogIn Authentication API
```bash
method : post
url : http://localhost:8081/axelor-erp/login.jsp
body: 
{
"username":"admin",
"password" : "admin"
}
header : Content-Type: application/json
```
 From above Login API Insomania get Seesion Cookie copy it and pest to "TTN_HSRW_IoT_API" file as shown in above example.
 
 Run TTN_HSRW_IoT_API.py script 
```bash
$ python TTN_HSRW_IoT_API.py
```
All setup success completed, Open axelor application can view data which come from sensore.


Table or Entity for "TrashCan"
---------------------------------------

1) TrashCan  : Trashcan contains multipal device with property.    
2) DeviceInformation : It is shows all device(sensor) Information.
3) Device (Sensore) : Device is a sensore which can sense multipal property.
4) Property : Property is element that sense by sensore (e.g. Tempreture, Humidity etc..)
5) Data : collection of information about property. 
6) Dashboard (Dashboard for data overview) : Dashboard is for visualization of Sensor data. 

Define Relationship model 
---------------------------------------
1. TrashCan with Device (OneToMany)
2. Device With Property (OneToMany)
3. Property With Data (OneToMany)
4. Device With DeviceInformation (ManyToOne)
5. Dashboard (No Entity Defination only defin menu Item)

Further Development....
1) Data analysis
2) Graph or Chart generation
3) Implement more functionality related to data model   


 



  
