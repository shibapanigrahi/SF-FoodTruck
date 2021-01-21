# SF-FoodTruck
This is a simple Java based command line application which displaythe list of food trucks opened in the current day and current time. It will display 10 food trucks at a time and there will be 3 options
1. To display next 10 entries
2. To display prev 10 entries
3. Exit

Source: https://dev.socrata.com/foundry/data.sfgov.org/bbb8-hzi6.

## Pre-requisites

- Basic requirement Java 1.7 or later installed on your machine.

## Run Application
- Clone the repository into your machine.

- Open the terminal/command prompt and navigate to the classFile directory:
  - For Linux: cd SF-FoodTruck/classFile
  - For Windows: cd SF-FoodTruck\classFile
  
- Complie and Run Steps
  - For Linux: 
      ```
      javac -cp "../depedency/*" -d . ../src/*.java
      java -cp "../depedency/*" FoodTruckMain
      ```
   - For Windows:
      ```
      javac -cp "..\depedency\*;" -d . ..\src\*.java
      java -cp "..\depedency\*;" FoodTruckMain
      ```
