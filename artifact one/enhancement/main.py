from clock import Clock # import the Clock class from clock.py

def main():
    try:
        my_clock = Clock() # create an instance
        while True:
            my_clock.display_clock() # display the clock

    except Exception as e:
        print(e)
        print("Program Ended.")

if __name__ == "__main__":
    main()
