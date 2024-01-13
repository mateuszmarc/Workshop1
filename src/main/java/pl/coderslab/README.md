# TaskWorkshop
Console program for managing tasks

## Description 
This is a simple program that allows the user to manage tasks. 
This console program prompts user to enter following command to perform operations of addition or removal of the task. 
It also allows to list all tasks.

### add:
After choosing this option the user will be prompted to enter following data:
- Task description: 
- Due date 
- Importance of the task (if it is important: true/ false): You can also enter "t" or "f". It will work just fine.

There are some rules when entering the data:
- Task description cannot be shorter than 5 characters.
- The date provided cannot be the past date. Who would add the tasks with overdue date? :)
Task Manager checks also if the date is valid based on year and month. 

If you changed your mind after choosing add option you can still come back to the main options by entering the "cancel" command,
or you can terminate the program by entering "exit" command.


### remove:
This option will list all tasks with sorted in order when they were added. 
In order to remove the task the user is prompted to enter the number of the task.
Number entered by the user gets validated against the list number.
You can always enter "cancel" to come back to the main screen or "exit" to terminate the program.


### list:
This option will list all added tasks in numerical order beginning from the earliest added. 

### exit: 
This option exits the program and saves all tasks added during program execution to csv file located in under the filepath provided as a starting parameter. 
Every task is saved to the file in following format: 
Task description, due date, significance


## How to use
Simply clone the project and run TaskManager class in the terminal with filepath to the csv file you want to save the tasks to
as a starting parameter.
If you provide invalid filepath then it will result with tasks not being saved. 
If you provide valid filepath but csv file doesn't exist then TaskManager will create csv file in provided directory. 


