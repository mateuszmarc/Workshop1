# TaskWorkshop
Console program for managing tasks

## Description 
This is a simple program that allow the user to manage tasks. 
This console program prompts user to enter following command to perform operations: 

###add:
After choosing this option the user will be prompted to enter following data:
- Task description
- Due date 
- Importance of the task (if it is important: true/ false)
Each task will be stored as array of strings in following format:
{taskDescription, dueDate, taskImportance}


###remove:
This option will list all tasks with sorted in order when they were added. 
In order to remove the task the user is prompted to enter the number of the task.


###list:
This option will list all added tasks in numerical order beginning from the earlies added. 

###exit 
This option exits the program and saves all tasks added during program execution to the tasks.csv file.
Every task is saved to the file in following format: 
Task description due date significance


##How to use
Simply clone the project and run TaskManager class in the terminal.

