# Floating Japanese Dictionary

This app provides a floating window in which one can look up the definitions of Japanese words even when another app is running. It takes up too much space to to use on small screens, but it works great on a 7-inch tablet (like a Nexus 7).

* Requires at least Andriod 4.0 (Ice Cream Sandwich)

[Download Link](https://github.com/ispedals/FloatingJapaneseDictionary/releases); [Github Repo](https://github.com/ispedals/FloatingJapaneseDictionary)


## Screenshots

![alt text](screenshots/collapsed.manga.png "collapsed.manga") ![alt text](screenshots/expanded.manga.png "expanded.manga") ![alt text](screenshots/definition.manga.png "definition.manga")

![alt text](screenshots/video.png "video")

## Usage

### Looking up words
If the dictionary file has been downloaded and processed, the floating window will appear

![alt text](screenshots/expanded.empty.keyboard.png "expanded.empty.keyboard")

Type the word you want to look up in the the text field in Japanese (romaji is not supported)

![alt text](screenshots/expanded.filled.png "expanded.filled")

If the word is not in the dictionary, the window will display **No Results**. To quickly clear the text field, tap the X button (if you tap the button when the text field is empty, it will cause the window to collapse, see **Expanding the window** to learn how to expand the window again).

![alt text](screenshots/no.results.launcher.png "no.results.launcher")

When you look up a word that is in the dictionary, you will be presented with a list of possible definitions

![alt text](screenshots/inflected.png "inflected")

You can look through all the definitions by swiping up and down on the list of definitions. Notice that the app attempts to deinflect the given word.

#### Searching for words online
You can perform a web search with the current text by selecting **Lookup** from the dropdown menu (see **The dropdown menu** to learn how to access it).

#### Saving a word
When you want to save a word with its definition, tap the entry. The entry will be saved to the file `Words.txt` in the device's `Download` directory. Accessing the file is left to the user.

### Interacting with window
#### Collapsing the window
To collapse the window so it does not take up as much space, tap the X button next to the text field  when the text field is empty. Therefore, if the text field contains text, tapping the X button once will make the text field empty and tapping the button again will cause the window to collapse. The window will now be smaller and contain an button with a search icon (a magnifiying glass).

![alt text](screenshots/expanded.empty.png "expanded.empty") ![alt text](screenshots/collapsed.png "collapsed")

#### Expanding the window
When the window is collapsed, to restore the window to its expanded state so that you can look up words, tap the button with the search icon.

![alt text](screenshots/collapsed.png "collapsed") ![alt text](screenshots/expanded.empty.png "expanded.empty")

#### Resizing the window
When the window is expanded, you can drag the bottom-right corner to make the window larger.

#### Moving the window
You can move the window around by tap-dragging the window by either tapping and dragging the black space in the title bar or any visible blank gray space. When the window is collapsed, the blank gray space is the space next to the button with the search icon. When the window is expanded, the blank gray space is the space visible when the window is not displaying definitions.

#### Closing the window
To close the window, tap the close button in the titlebar (the button with the * icon in the upper right corner). To open the window, you must start the app again.

#### The dropdown menu
To display the dropdown menu, tap the button with the + icon in the upper left corner. From here you can also close the window, view licensing information, and delete the downloaded dictionary file by tapping **Reset**.

![alt text](screenshots/dropdown.png "dropdown")

If text is present in the text field, an option **Lookup** is available, which performs a web search with the current text.

![alt text](screenshots/dropdown.lookup.png "lookup")

### Looking up words using your voice
**Note: In order for this to work, you appear to need to have Google Voice Search installed and have set the voice language setting to Japanese in Google Voice Search's options**

If you have a capable device, a button with a microphone icon will appear next to the text field.

![alt text](screenshots/microphone.png "microphone")


Tapping on the microphone will prompt you to say the word

![alt text](screenshots/prompt.png "prompt")

The text field will not contain the word you said (or attempted to say) and will display the definitions of the word.


## Developing
This app depends on the StandOut library. Instructions on how to import the library are available at http://forum.xda-developers.com/showthread.php?t=1688531

## License
This app is licensed under the GNU General Public License version 2 with the various copyright notices in the source code. It also uses the MIT-licensed [StandOut Library](http://pingpongboss.github.com/StandOut) by Mark Wei. In addition, many of the dictionary searching functions have been strongly inspired by Rikaichan.

The dictionary file is a derivative of the EDICT dictionary file. This file is the property of the Electronic Dictionary Research and Development Group, and is used in conformance with the Group's licence. See http://www.csse.monash.edu.au/~jwb/edict.html and http://www.edrdg.org/.

## Changes
* v3.0
	* Bundle dictionary to remove need for download on first install
	* Change minimum version to Android 4.0
	* Fix lint errors
	* Correct image links in readme

* v2.0
    * Implement search on type so you no longer have to hit submit
    * Allow the window to be resized when expanded

* v1.0
	* Initial release
