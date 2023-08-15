# **Quick Draw**

The project that I will design this term is a basic drawing application with various functions, similar to Microsoft Paint.  Like most drawing applications, it will have (but not limited to):

- A pencil tool
- A text tool
- A line tool
- Various shape tools
- Different colours
- An eraser

The user starts with a blank space, which can be drawn onto using the tools to create an image or diagram.  Although there are not enough various and precise tools for an artist to use, anyone can use this to quickly draw a picture or diagram, for fun or for a quick explanation.  This project is of interest to me because I sometimes need to quickly draw diagrams when working online, usually when I'm explaining a concept to a friend.  However, all the free drawing applications I have tried are flawed in some way.  For instance, some have permanent controls or settings that I don't like, some have tools that don't work properly, and some take too long to open and start from scratch.  There is no perfect application that is suitable for my needs.  My goal is to create a drawing application that can be used quickly for simple images, but also has many useful functions.

## User Stories

**Drawing and Editing**
- As a user, I want to be able to view the drawing that I have created
- As a user, I want to be able to draw multiple lines between different points on my current drawing
- As a user, I want to be able to erase any lines that I drew earlier
- As a user, I want to be able to add text of my choice to my drawing
- As a user, I want to be able to change the colour of my lines

**Storage of Drawings**
- As a user, I want to be able to save my drawing to a file
- As a user, I want to be able to load my drawing from a file
- As a user, I want to be given the option to save my work before I quit
- As a user, I want to be able to choose to use an autosave function that will save every time I edit my drawing

**Phase 4: Task 3**
If I had more time, there are some things that I would like to refactor.
- Add a class for handling the pop-up windows so that Display would be more readable
- Remove the model.Point class and use the java.awt.Point class instead so that I wouldn't have to switch between the two
- Make the editing tools edit Drawing instead of calling methods in GuiDrawing to edit a Drawing object

## Side Notes
- This GUI version takes its ui systems and structures from Professor Paul Carter's Simple Drawing Player, at https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete.
- File reading and writing systems take most of their systems and structures from Professor Paul Carter's Json Serialization Demo, at https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.
- Event logging system and classes are from Professor Paul Carter's Alarm System, at https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.