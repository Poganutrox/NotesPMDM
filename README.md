# Notes PMDM

## Description
This project is a note-taking application with a view-detail structure. It displays a list of notes on the initial screen, obtaining information from a database accessed using ROOM. Additionally, it applies the MVVM pattern and concepts of Clean Architecture.

## Features
- Custom application icon related to the theme of note-taking.
- Two main activities:
  - `MainActivity`: Displays the list of created notes. It includes a menu in the Toolbar with options "Disable filter" and "Sort by Title (A-Z)".
  - `DetailNoteActivity`: Displays the details of a note, with options to add or edit a note.
- Utilizes MaterialToolbar for both activities.
- Uses RecyclerView to display the list of notes in the MainActivity.
- Allows adding, editing, and deleting notes.
- Provides functionality to undo the deletion of a note.

## Usage
1. When the application is launched:
   - If there are no notes in the database, it displays the message "No notes to show".
2. To add a note:
   - Press the "Add" button in the top bar to navigate to the detail view.
   - Enter a title and description for the note. Title is mandatory.
   - Press the save button in the top bar to save the note.
3. To view or edit a note:
   - Tap on a note in the list to view its details.
   - Edit the title or description if needed.
   - Save the changes using the save button in the top bar.
4. To delete a note:
   - Tap on the delete icon next to the note in the list.
   - The application prompts for confirmation to delete the note.
   - Provides an option to undo the deletion.

## Screenshots

![image](https://github.com/Poganutrox/NotesPMDM/assets/63597815/53a44cb9-13e6-4e49-ae0a-6bba44df774c)

![image](https://github.com/Poganutrox/NotesPMDM/assets/63597815/f0fe8393-6fbc-4f41-9af4-625326a26511)

![image](https://github.com/Poganutrox/NotesPMDM/assets/63597815/c18ce0d8-0ce7-4a9a-8916-da64612810b9)




