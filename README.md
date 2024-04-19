[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]
<a name="readme-top"></a>

<br />
<div align="center">
<h3 align="center">Workshop 1</h3>

  <p align="center">
  Console program for managing tasks
    <br />
    <a href="https://github.com/mateuszmarc/Workshop1"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/mateuszmarc/Workshop1">View Demo</a>
    ·
    <a href="https://github.com/mateuszmarc/Workshop1/issues/new?labels=bug&template=bug-report---.md">Report Bug</a>
    ·
    <a href="https://github.com/mateuszmarc/Workshop1/issues/new?labels=enhancement&template=feature-request---.md">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
        <li><a href="#implementation">Implementation</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li>
<a href="#usage">Usage</a>
      <ul>
        <li><a href="#add">Add</a></li>
        <li><a href="#remove">Remove</a></li>
        <li><a href="#list">List</a></li>
        <li><a href="#exit">Exit</a></li>
      </ul>
</li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project
This is a simple program that allows the user to manage tasks.
This console program prompts user to enter following command to perform operations of addition or removal of the task.
It also allows to list all tasks.

### Built With

* [![Java][Java.com]][Java-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started

### Prerequisites
*  Code editor, for example: [intelliJ](https://www.jetbrains.com/idea/)

* Installed [Java JDK](https://www.java.com/pl/)


### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/mateuszmarc/Workshop1.git
   ```
2. Open the project in IntelliJ IDEA.
3. Set starting parameter as the filepath to the csv file that you will store your task - if the file is not found by app it will be created
4. Run app in terminal

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage
This app can perform following operations: 

### Add:
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


### Remove
This option will list all tasks with sorted in order when they were added.
In order to remove the task the user is prompted to enter the number of the task.
Number entered by the user gets validated against the list number.
You can always enter _cancel_ to come back to the main screen or _exit_ to terminate the program.


### List
This option will list all added tasks in numerical order beginning from the earliest added.

### Exit
This option exits the program and saves all tasks added during program execution to csv file located in under the filepath provided as a starting parameter.
Every task is saved to the file in following format:
Task description, due date, significance

<!-- LICENSE -->

## License

Distributed under the MIT License. See `LICENSE` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTACT -->
## Contact

Mateusz Marcykiewicz - mmarcykiewicz@gmail.com

Project Link: [https://github.com/mateuszmarc/Workshop1](https://github.com/mateuszmarc/Workshop1)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/mateuszmarc/Workshop1.svg?style=for-the-badge
[contributors-url]: https://github.com/mateuszmarc/Workshop5/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/mateuszmarc/Workshop1.svg?style=for-the-badge
[forks-url]: https://github.com/mateuszmarc/Workshop1/network/members
[stars-shield]: https://img.shields.io/github/stars/mateuszmarc/Workshop1.svg?style=for-the-badge
[stars-url]: https://github.com/mateuszmarc/Workshop1/stargazers
[issues-shield]: https://img.shields.io/github/issues/mateuszmarc/Workshop1.svg?style=for-the-badge
[issues-url]: https://github.com/mateuszmarc/Workshop1/issues
[license-shield]: https://img.shields.io/github/license/mateuszmarc/Workshop1.svg?style=for-the-badge
[license-url]: https://github.com/mateuszmarc/Workshop1/blob/main/LICENSE
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/mateusz-marcykiewicz/


[Java.com]: https://img.shields.io/badge/Java-000?style=for-the-badge&&logoColor=white
[Java-url]: https://www.java.com/pl/
[mysql.com]: https://img.shields.io/badge/MySql-4479A1?style=for-the-badge&logo=mysql&logoColor=white
[mysql-url]: https://www.mysql.com/




