import os
import select
import sys
import time
import tty
import termios


class Clock:
    def __init__(self, hour=0, mins=0, sec=0):
        current_time = time.localtime() # get current time
        self.hour = current_time.tm_hour
        self.mins = current_time.tm_min
        self.sec = current_time.tm_sec

    def two_digit_string(self, n): # format the time to two digits
        return f"{n:02}"

    def format_time_24(self): # format the time to 24-hour clock
        hour = self.hour if self.hour < 24 else 0
        return f"{self.two_digit_string(hour)}:{self.two_digit_string(self.mins)}:{self.two_digit_string(self.sec)}"

    def format_time_12(self): # format the time to 12-hour clock
        am_pm = "AM"
        hour = self.hour
        if hour >= 12:
            hour -= 12
            am_pm = "PM"
        if hour == 0:
            hour = 12
        return f"{self.two_digit_string(hour)}:{self.two_digit_string(self.mins)}:{self.two_digit_string(self.sec)} {am_pm}"

    def print_menu(self): # print the menu
        print('*' * 26)
        print("* 1 - Add One Hour       *")
        print("* 2 - Add One Minute     *")
        print("* 3 - Add One Second     *")
        print("* 4 - Add Custom Time    *")
        print("* 5 - Exit Program       *")
        print('*' * 26)

    def display_clock(self):
        while True:
            os.system('clear') # clear the screen
            print('*' * 26 + '            ' + '*' * 26)
            print("*    12-Hour Clock       *            *     24-Hour Clock      *")
            print(f"*    {self.format_time_12()}         *            *      {self.format_time_24()}          *") # display the time
            print('*' * 26 + '            ' + '*' * 26)
            self.print_menu()
            print('Please enter a number (Please press "ENTER" before entering a number): ')
            if self.check_key_press(): # check if a key is pressed
                key = self.get_key_press() # get the key pressed
                if key == '1':
                    self.add_hour(1)
                    print("You added an hour.")
                elif key == '2':
                    self.add_minute(1)
                    print("You added a minute.")
                elif key == '3':
                    self.add_second(1)
                    print("You added a second.")
                elif key == '4':
                    hour, mins, sec = self.add_custom_time()
                    print(f"You added {hour} hours, {mins} minutes, and {sec} seconds.")
                    time.sleep(0.5)
                elif key == '5':
                    print("Program Ended.")
                    sys.exit()
                else:
                    print("That's not a valid response, please enter a number from 1-4.")
            self.add_second(1)
            time.sleep(1)

    def get_key_press(self): # get the key pressed
        # reference: https://gist.github.com/jasonrdsouza/1901709
        # reference: https://stackoverflow.com/questions/1258566/how-to-get-user-input-during-a-while-loop-without-blocking
        fd = sys.stdin.fileno()
        old_settings = termios.tcgetattr(fd)
        try:
            tty.setcbreak(fd)
            key = sys.stdin.read(1)
        finally:
            termios.tcsetattr(fd, termios.TCSADRAIN, old_settings)
            return key

    def add_hour(self, hour):
        self.hour = (self.hour + hour) % 24

    def add_minute(self, mins):
        total_minutes = self.mins + mins
        self.mins = total_minutes % 60
        self.hour = (self.hour + total_minutes // 60) % 24

    def add_second(self, sec):
        total_seconds = self.sec + sec
        self.sec = total_seconds % 60
        extra_minutes = total_seconds // 60
        self.add_minute(extra_minutes)

    def check_key_press(self): # check if a key is pressed
        # reference: https://stackoverflow.com/questions/3762881/how-do-i-check-if-stdin-has-some-data
        # reference: https://gist.github.com/davstott/5086945

        return (select.select([sys.stdin], [], [], 0) == ([sys.stdin], [], []))

    def add_custom_time(self): # add custom time
        print("Please enter the time you would like to add.")
        hour = int(input("Enter the hour (0 - ∞): "))
        mins = int(input("Enter the minutes (0 - ∞): "))
        sec = int(input("Enter the seconds (0 - ∞): "))
        self.add_hour(hour)
        self.add_minute(mins)
        self.add_second(sec)
        return hour, mins, sec
