# IoT Failure Management Similator (FM-Simulator)
The FM Simulator allows the simulation of failures injection and recovery on Internet of Things devices. It is an extention of the simulator [iFogSim2](), and its failure injection and recovery method is inspired from [ClouSimPlus](). 
  
iFogSim2 currently encompasses several new usecases such as:
 * Audio Translation Scenario
 * Healthcare Scenario
 * Crowd-sensing Scenario

# How to run FM-Simulator ?
* Eclipse IDE:
  * Create a Java project
  * Inside the project directory, initialize an empty Git repository with the following command:
  ```
  git init
  ```
  * Add the Git repository of FM-Simulator as the `origin` remote:
  ```
  git remote add origin https://github.com/Orange-OpenSource/collaborativeDM-FM-Simulator
  ```
  * Pull the contents of the repository to your machine:
  ```
  git pull origin main
  ```
  * Include the JARs to your project  
  * Run the example files (e.g. SmartHomeApplication.java) to get started

* IntelliJ IDEA:
  * Clone the FM-Simulator Git repository to desired folder:
  ```
  git clone https://github.com/Orange-OpenSource/collaborativeDM-FM-Simulator
  ```
  * Select "project from existing resources" from the "File" drop-down menu
  * Verify the Java version
  * Verify the external libraries in the "JARs" Folder are added to the project
  * Run the example files (e.g. SmartHomeApplication.java) to get started


# References
 * Redowan Mahmud, Samodha Pallewatta, Mohammad Goudarzi, and Rajkumar Buyya, <A href="https://arxiv.org/abs/2109.05636">iFogSim2: An Extended iFogSim Simulator for Mobility, Clustering, and Microservice Management in Edge and Fog Computing Environments</A>, Journal of Systems and Software (JSS), Volume 190, Pages: 1-17, ISSN:0164-1212, Elsevier Press, Amsterdam, The Netherlands, August 2022.
 * Harshit Gupta, Amir Vahid Dastjerdi , Soumya K. Ghosh, and Rajkumar Buyya, <A href="http://www.buyya.com/papers/iFogSim.pdf">iFogSim: A Toolkit for Modeling and Simulation of Resource Management Techniques in Internet of Things, Edge and Fog Computing Environments</A>, Software: Practice and Experience (SPE), Volume 47, Issue 9, Pages: 1275-1296, ISSN: 0038-0644, Wiley Press, New York, USA, September 2017.
 * Redowan Mahmud and Rajkumar Buyya, <A href="http://www.buyya.com/papers/iFogSim-Tut.pdf">Modelling and Simulation of Fog and Edge Computing Environments using iFogSim Toolkit</A>, Fog and Edge Computing: Principles and Paradigms, R. Buyya and S. Srirama (eds), 433-466pp, ISBN: 978-111-95-2498-4, Wiley Press, New York, USA, January 2019.
 ## License
 
 This software is distributed under [GPL v3](LICENSE.txt). 

Copyright (c) 2023 Orange


## Maintainer
 
 * Amal GUITTOUM e-mail: amal.guittoum@orange.com
