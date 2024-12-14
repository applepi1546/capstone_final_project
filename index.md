<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vy Huynh ePortfolio</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
            color: #333;
        }
        header {
            background: #333;
            color: #fff;
            padding: 1rem 0;
            text-align: center;
        }
        section {
            max-width: 900px;
            margin: 2rem auto;
            padding: 1rem;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        h1, h2, h3 {
            color: #333;
        }
        a {
            color: #007bff;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        .badge {
            display: inline-block;
            margin: 0.2rem;
        }
        iframe {
            display: block;
            margin: 1rem auto;
            border: none;
        }
    </style>
</head>
<body>
    <header>
        <h1>Vy Huynh ePortfolio</h1>
    </header>
    <section>
        <h2>Languages</h2>
        <img class="badge" src="https://img.shields.io/badge/c++-%2300599C.svg?style=for-the-badge&logo=c%2B%2B&logoColor=white" alt="C++">
        <img class="badge" src="https://img.shields.io/badge/python-3670A0?style=for-the-badge&logo=python&logoColor=ffdd54" alt="Python">
        <img class="badge" src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
    </section>
    <section>
        <h2>Databases</h2>
        <img class="badge" src="https://img.shields.io/badge/sqlite-%2307405e.svg?style=for-the-badge&logo=sqlite&logoColor=white" alt="SQLite">
        <img class="badge" src="https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white" alt="MongoDB">
    </section>
    <section>
        <h2>Professional Self-Assessment</h2>
        <p>I began learning computer science during my sophomore year of high school with a foundational Java programming class. This sparked my interest and passion for problem-solving and coding. Since then, I’ve diversified my programming skills, mastering Python and C++ alongside Java. This versatility enables me to choose the best language for a given project.</p>
        <p>Mobile app development, though not an initial focus, became an exciting challenge during my journey. Learning Android Studio provided valuable insights and skills that I plan to leverage in future projects. Additionally, I gained essential database knowledge with SQL and MongoDB, equipping me to handle modern application requirements efficiently.</p>
        <p>My skills align with my career goal of becoming a data analyst. Python's prominence in data analysis and MongoDB's popularity in enterprise applications ensure that I am well-prepared for this path. By continuously learning and adapting, I aim to meet industry demands and excel in my field.</p>
    </section>
    <section>
        <h2>Code Review</h2>
        <iframe width="800" height="500" src="https://www.youtube.com/embed/Aj3ZXD58txM/" allowfullscreen></iframe>
    </section>
    <section>
        <h2>Artifact One: Software Design and Engineering</h2>
        <p><strong>Project Links:</strong></p>
        <ul>
            <li><a href="https://github.com/applepi1546/capstone_final_project/tree/main/artifact%20one/original">Original Enhancement</a></li>
            <li><a href="https://github.com/applepi1546/capstone_final_project/tree/main/artifact%20one/enhancement">Updated Enhancement</a></li>
        </ul>
        <h3>Description</h3>
        <p>This project, originally created in C++ during CS210, involved designing a clock program that converts military time to AM/PM format. It also allowed users to increment the clock by one hour, minute, or second.</p>
        <h3>Enhancements</h3>
        <ul>
            <li>Rewrote the project in Python.</li>
            <li>Added a live, self-running clock feature.</li>
            <li>Allowed users to manually input the hours, minutes, and seconds to adjust.</li>
        </ul>
        <p>These enhancements automated the clock functionality, making it more user-friendly and interactive. This project demonstrates my ability to refactor and optimize code for better usability.</p>
    </section>
    <section>
        <h2>Artifact Two: Algorithms and Data Structures</h2>
        <p><strong>Project Links:</strong></p>
        <ul>
            <li><a href="https://github.com/applepi1546/capstone_final_project/tree/main/artifact%20two/original">Original Enhancement</a></li>
            <li><a href="https://github.com/applepi1546/capstone_final_project/tree/main/artifact%20two/enhancement">Updated Enhancement</a></li>
        </ul>
        <h3>Description</h3>
        <p>This project, built using Android Studio during CS360, is an inventory manager app. It allows users to log in, manage their inventory, and perform CRUD operations.</p>
        <h3>Enhancements</h3>
        <ul>
            <li>Validated usernames as valid email addresses.</li>
            <li>Ensured passwords meet strong security criteria (e.g., length, capital letters, special characters).</li>
            <li>Added sorting functionality for inventory items by name or quantity.</li>
            <li>Implemented a search feature to locate specific items quickly.</li>
        </ul>
        <p>These enhancements improve app security, usability, and functionality, demonstrating my ability to design secure and efficient algorithms.</p>
    </section>
    <section>
        <h2>Artifact Three: Databases</h2>
        <p><strong>Project Links:</strong></p>
        <ul>
            <li><a href="https://github.com/applepi1546/capstone_final_project/tree/main/artifact%20three/original">Original Enhancement</a></li>
            <li><a href="https://github.com/applepi1546/capstone_final_project/tree/main/artifact%20three/enhancement">Updated Enhancement</a></li>
        </ul>
        <h3>Description</h3>
        <p>This project involves building a dashboard for an animal shelter using MongoDB, Python, and HTML. The dashboard displays data such as animal type, breed, and age, allowing users to query the database.</p>
        <h3>Enhancements</h3>
        <ul>
            <li>Renamed columns to improve readability.</li>
            <li>Added a dropdown filter for selecting animal types.</li>
            <li>Secured credentials by encrypting them instead of storing them in plain text.</li>
            <li>Cleaned up unnecessary data fields, like latitude and longitude, for better usability.</li>
        </ul>
        <p>These changes enhance the dashboard’s functionality, security, and user experience, aligning with my goal of creating efficient and secure database-driven applications.</p>
    </section>
</body>
</html>
