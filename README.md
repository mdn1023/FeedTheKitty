# 436

11/26: 

Things to be done off the top of my head:

- FireBase DB linked
- Facebook Login
- Formally create, store Events
- Associate events with respective users
- Google Pay


GENERAL WALKTHROUGH OF APP FROM CURRENT POINT:

- upon opening app user greeted with welcome login screen
  - user has the option to:
    1. //TODO: Recover Password.
      - opens new Activity to send email w/ new password to registered email address
      - bonus pts for hashing password?
    2. //TODO: Sign Up
      - opens new activity asking for:
        A. First, Last name
        B. DOB
        C. ?
    3. //TODO: Facebook Login
      - I honestly have no idea how to do this
    4. Enter username, pw, and hit 'Sign In'
      
- Upon signing in, user is greeted with 'Main Menu Page' from Mockup.
  - option to 'Create An Event', 
  - //TODO: View 'Ongoing Events', and 
  - //TODO: View 'Past Events'
- Upon selecting 'Create an Event', user prompted with Event Creation page.
  - Enter Unique name for event
  - Select whether event is public or private to attendees (feature could be expanded upon if this turns into a "social network crowdfunding thing"
  - Add Expenses for various budget-line items for event
    - Upon hitting 'Add Expenses', user greeted with Add Expenses page.
      - Give expense unique name
      - Select amount to designate for particular expense in multiples of $1, $5, $10, and $50
      - Reset amount if they accidentally go over
      - //TODO: submit expense to be added to ListView on Event Creation page for final viewing (???????????)
  - //TODO remove items from Expenses list before creating event
  
  Everything not touched on above (inviting users, sending money) has NOT been fleshed out at all. 
  
  
  IN TERMS OF CODE LOCATIONS:
  
  - Login stuff:
    1. LoginActivity (NOT BasicLogin) is the file where all login stuff is handled.
    2. DO NOT UNCOMMENT OUT THE 3 instances of showProgress() throughout the code. they'll cause it to break. I haven't bothered to figure out why yet
  - Expense stuff:
    1. AddExpense. Pretty straightforward
  - Create Event stuff:
    1. TestActivity. I messed up naming the file and I don't care enough to change it right now.
  - anything not listed above probably doesn't exist yet. 
  
  *** IF YOU ARE DOING UI ELEMENTS, PLEASE LOOK AT /res/layout/activity_basic_login.xml OR activity_test.xml FOR A MORE INTUITIVE TAKE ON HOW TO DO UI STUFF. I made both of those myself and am more than happy to help where needed so just @ me in the groupme. 
