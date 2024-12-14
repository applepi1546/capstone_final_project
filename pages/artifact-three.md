---
title: Artifact Three Databases
layout: default
---

<div align="justify">
<h3 style="text-align:center;">Artifact Three: Databases</h3>
<p>	The last artifact that I will be working on is my MongoDB dashboard project.  It’s an animal shelter dashboard project that uses MongoDB, python, and HTML. The project uses a database that is provided which has many types of information like animal type, breed, age, sex, and more. All this information is then used to build a dashboard where users can search and query for all sorts of different data. My enhancement plan for this project is to make it more user-friendly and legible while cleaning up the unnecessary data. A problem that I see with this project that can be enhanced and improved upon is that right now the column are named after the MongoDB column, such as ‘outcome_type’ or ‘date_of_birth’. All of these should be renamed to a better-looking name. Next, I would like to a filter for the animal type. This means instead of manually typing in dog or cat or other, I can just have a dropdown filter that allows the user to just select the animal type. Another concern I have with the project is that right now the user credential is just hard-coded into the python file as plain text, which makes it really risky and easy to crack. So, I would like to hide the credentials instead of allowing a credential to be unencrypted. Finally, I would like to overall clean up the dashboard as right now, there is a lot of information I that consider unnecessary on the dashboard right now, such as location latitude and longitude. I would like to hide this information and make it easier for the user to only see the necessary information. The changes I want to improve the dashboard by cleaning it up and making it more tidy, therefore allowing for easier navigation. The main benefit of all these changes is that it allows users using the interface to have an easier time navigating the dashboard. They will also have the ability to filter animal type so that instead of just being able to filter by ‘dog’ or ‘cat’, they can filter it by ‘dog’ AND ‘cat’. I would say I did meet the original plan that I set out for this artifact, the goal for this project was clean up the dashboard, prettify the dashboard, hide the credentials, and finally add a checkbox for animal's type. I was able to hit all my objectives, so I would say it was a success. In terms of objectives for this course, I would say I have met all my course object that I was set out to do for this class.</p>
<p>This artifact was straightforward to do, as I still remember working on this project last semester. The first thing that I did was add a credential for hiding credential instead of hard-coding the credentials. For that, I use python-dotenv library (link). It was quite straightforward for me to use because I have experience with using an env file for my other python project that uses actual credentials. The next thing that I did was figure out how to rename my column. For that, I read a few different Stack Overflow pages and settled on using df.rename() to rename all my column, I made a dictionary for mapping and easier renaming, (link). The next thing that I did was hide all the columns that I deemed unnecessary. I didn’t have to do any research for this step. All I did was make a for loop that checks if ‘i’ is not in a list called ‘hide_column’. If it’s not, then I append ‘i’ into my column list. Next, to add the checkbox into my dashboard, I first referenced my old code when I added the buttons into my dashboard. To add the checkbox I googled and read the documentation made by Dash (link) and referenced that along with my old button to make the new checklist. It took a few tries to get the spacing and text working correctly, but thereafter, I was able to move on and get the function working. This was the quickest project as I had a base code that I was familiar with, and I didn’t have to rewrite the whole project or write the project in another programming language. </p>