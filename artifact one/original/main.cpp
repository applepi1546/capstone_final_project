#include <iostream>
#include "clock.h"
//hi
using namespace std;


int main() {
    int hour, mins, sec;
    cout << "Please enter your time in 24-hour format (ex: 23:12:09): ";
    cin >> hour;
    cin.ignore();
    cin >> mins;
    cin.ignore();
    cin >> sec;

    Clock myClock(hour, mins, sec);

    while (myClock.flag)
    {
        myClock.displayClock();
        myClock.printMenu();
        myClock.mainMenu();
    }

    return 0;
}

