#include "clock.h"
using namespace std;
#include <string>
#include <iostream>

Clock::Clock(int hour, int mins, int secs)
{
    this->hour = hour;
    this->mins = mins;
    this->sec = secs;
    flag = true;}

string Clock::twoDigitString(int n) //function to convert single digit into double digits
{
    if (n < 10) {
        return "0" + to_string(n);
    } else
        return to_string(n);
}

string Clock::formatTime24() //function to format time in 24 hours
{
    if(hour == 24)
        hour = 0;
    string time = twoDigitString(hour) + ":" + twoDigitString(mins) + ":" + twoDigitString(sec);
    return time;
}

string Clock::formatTime12() //function to format time in 12 hours
{
    int hr12;
    string amPM = "AM ";
    if (hour >= 12)
    {
        hr12 = hour - 12;
        amPM = "PM ";
    }
    else
    {
        hr12 = hour;
    }
    if(hr12 == 0)
    {
        hr12 = 12;
    }
    string time = twoDigitString(hr12) + ":" + twoDigitString(mins) + ":" + twoDigitString(sec) + " "+ amPM;
    return time;
}

void Clock:: printMenu() //print display menu
{
    for(int i = 0; i < 26; i++)
    {
        cout << '*';
    }
    cout << endl;
    cout << "* 1 - Add One Hour       *\n";
    cout << "* 2 - Add One Minute     *\n";
    cout << "* 3 - Add One Second     *\n";
    cout << "* 4 - Exit Program       *\n";
    for(int i = 0; i < 26; i++)
    {
        cout << '*';
    }
    cout << endl;
}
void Clock:: displayClock() //print display clock and time
{
    for(int i = 0; i < 26; i++)
    {
        cout << '*';
    }
    cout << "            ";
    for(int i = 0; i < 26; i++)
    {
        cout << '*';
    }
    cout << endl;
    string hr24 = formatTime24();
    cout << "*    12-Hour Clock       *            " ;
    cout << "*     24-Hour Clock      *\n";
    cout << "*     " << formatTime12() << "       *            ";
    cout << "*       " << hr24 << "         * \n";
    for(int i = 0; i < 26; i++)
    {
        cout << '*';
    }
    cout << "            ";
    for(int i = 0; i < 26; i++)
    {
        cout << '*';
    }
    cout << endl;
}

void Clock:: mainMenu() //function to handle user input
{
    int key; //hi
    bool flag = false;
    cout << "Please enter a number: ";
    cin >> key;
    switch(key)
    {
        case 1: //if user pick 1 hour
            hour ++;
            break;
        case 2: //if user pick 1 min
            mins++;
            if(mins == 60)
            {
                mins = 0;
                hour++;
            }
            break;
        case 3: //if user pick 1 second
            sec++;
            if(sec == 60)
            {
                sec = 0;
                mins++;
                if(mins == 60)
                {
                    mins = 0;
                    hour ++;
                }
            }
            break;
        case 4: //if user decide to get of the program
            cout << "Program Ended.";
            exit(0);


        default: //else if user chooses anything else
            cout << "That's not a valid response, please enter a number from 1-4.\n";
    }
}
