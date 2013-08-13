# Floating Japanese Dictionary
This app provides a floating window in which one can look up the definition of Japanese words even when another app is running.

##Usage
**This app assumes the presence of an external or built in SD card. Not having one will prevent the app from working**
###Start the app
If the app is running for the first time, you will be prompted to let the app download the dictionary file (11.2MB). If you chose to download the file later, the app will close and will ask for permission again the next time you start the app.
<Insert screenshot of dialog>
###Looking up words
If the dictionary file has been downloaded and processed, the floating window will appear

<Insert screenshot of just opened window>

Type the word you want to look up in the the text field and either hit enter or tap the button with a triangle'
<small>Remember to switch to Japanese input</small>

<insert screenshot of no results>

If the word is not in the dictionary, the window will display **No Results**. To quickly clear the text field, tap the X button (if you tap the button when the text field is empty, it will cause the window to collapse, see **Making the window larger (expanding the window)** to learn how to expand the window again).

When you look up that is in the dictionary, you will be presented with a list of possible definitions

<insert screenshot of inflected word definitions>

You can look through all the definitions by swiping up and down on the list of definitions. Notice that the app attempts to deinflect the given word.

####Saving a word
When you want to save a word with its definition, tap the entry. The entry will be saved to the file `Words.txt` in the device's `Download` directory. Accessing the file is left to the user.

###Interacting with window
####Making the window smaller (collapsing the window)
To collapse the window so it does not take up as much space, tap the X button next to the text field  when the text field is empty. Therefore, if the text field contains text, tapping the X button once will make the text field empty and tapping the button again will cause the window to collapse. The window will now be smaller and contain an button with a search icon (a magnifiying glass).

<insert screenshot expanded and empty text field>

<insert screenshot collapsed>
####Making the window larger (expanding the window)
When the window is collapsed, to restore the window to its expanded state so that you can look up words, click the button with the search icon.

<insert screenshot collapsed>

<insert screenshot expanded and empty text field>
####Moving the window
You can move the window around by tap-dragging the window by either tapping and dragging the black space in the title bar or any visible blank gray space. When the window is collapsed, the blank gray space is the space next to the button with the search icon. When the window is expanded, the blank gray space is the space visible when the window is not displaying definitions.
####Maximizing the window
When viewing a list of words you can maximize the window by tapping the maximize button in the title bar (the button with the + icon in the upper right corner ). Click the button again to restore the window to its normal size.

<insert screenshot with window maximized>

####Closing the window
To close the window, click the close button in the titlebar (the button with the * icon in the upper right corner next to the maximize button).

####The dropdown menu
In the upper left corner of the window, by clicking the button with the + icon, the dropdown menu is displayed. From here you can also close the window, view licensing information, and delete the downloaded dictionary file by tapping **Reset**.

###Looking up words using your voice
**Note: In order for this to work, you appear to need to have Google Voice Search installed and have set the voice language setting to Japanese in Google Voice Search's options**

If you have a capable device, a button with a microphone icon will appear next to the text field.

<insert screenshot with microphone visible>

Clicking on the microphone will prompt you to say the word

<insert screenshot with prompt>

The text field will not contain the word you said (or attempted to say) and will display the definitions of the word.

##License
This app is licensed under the GNU General Public License version 2 with the various copyright notices in the source code. In addition, many of the dictionary searching functions have been strongly inspired by Rikaichan.

When downloaded, the dictionary file is a derivative of the EDICT dictionary file. This file is the property of the Electronic Dictionary Research and Development Group, and is used in conformance with the Group's licence. See http://www.csse.monash.edu.au/~jwb/edict.html and http://www.edrdg.org/. 