#ifndef VYHUYNHCLOCK_CLOCK_H
#define VYHUYNHCLOCK_CLOCK_H
#include <string>
using namespace std;


class Clock
{
public:
    Clock(int hour, int mins, int secs);
    string formatTime24();
    string formatTime12();
    void displayClock();
    static void printMenu();
    void mainMenu();
    static static string twoDigitString(int);
    bool flag;
    int hour;
    int mins;
    int sec;
};


#endif //VYHUYNHCLOCK_CLOCK_H
