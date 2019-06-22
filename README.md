\[ ⚠️ Work in progress 💻]  TESS 🌡
======================================
TESS
----
Maybe is your girlfriend or just your Thermostat!

Overview
--------
Thermal Economy Smart System, these words enclose the main goals of this project.
Started as a game and some experimentation it has become a real side project that trying to provide a cheap, easy and smart thermal system for every user who wants to try it out for free.

The entire project is designed to work on Android Things, used to collect and organize all sensor data and send it to Firebase.
The thermal data collected by the sensors is obtained by exploiting the Aqara Gateway from Xiaomi. 
This Gateway provides a developer mode that exposes into the LAN some open port to communicate with it and retrieve the sensors data.
On the other side, Firebase collects the organized dataset and provide a back-end structure to the mobile application.

Tech Specs
----------
The main focus of this project is to use and play with all the latest best technologies available for Android Development.
TESS is entirely written in Kotlin and it uses lots of useful libraries, that is right now a defacto standard.
Some examples:
*  [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
*  [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
*  [Koin](https://github.com/InsertKoinIO/koin) (Dependency Injection)
*  [Arrow](https://arrow-kt.io/) (Functional Programming)
*  [Room](https://developer.android.com/topic/libraries/architecture/room) (Database)
*  [Firebase Firestone](https://firebase.google.com/products/firestore/) (Remote Database)
*  [Mockk](https://mockk.io/) (Mocking Library)

Project Status Backlog
----------------------

* [x] ~~Create a base structure for the project~~
* [x] ~~Receive the Multicast messages from the Aqara Gateway~~
* [x] ~~Communicate with the Gateway via UDP messages~~
* [x] ~~Create a mechanism that can automatically detect the paired sensors and identify those~~
* [ ] Create a first back-end structure based on Firebase technologies
* [ ] Start Mobile App development
* [ ] Organize the data collected from the Aqara Gateway and store it in Firebase Firestone
* [ ] Integrate the Mobile App with the back-end
* [ ] And so on...

Contributions
-------------
Feel free to send suggestions, improve existing functionality, report issues, propose new features and contribute in every way you want.
Since this project is still in its very early stages, if your change is substantial, please raise an issue first to discuss it.

License
-------

    Copyright 2019

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.