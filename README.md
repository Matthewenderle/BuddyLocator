# BuddyWatch
A simple Minecraft Spigot plugin to display the coordinates to your buddy.

# Intro
I created this plugin for my buddy (Xeron998) and myself. Often times we'd be out exploring and we get lost from each other. We wanted a way to see where the other person was to be able to navigate back to them.

This plugin will show the targeted player's location, with the options to display health, experience level, and the world.

![PNG of plugin in action](https://i.imgur.com/PflNRc6.png)
![Main Menu](https://imgur.com/8dC3hra.png)

# How to Use
* When a player is online use /bl create <playername> 


# Commands/Permissions
Command |	Description	| Permission
-------- | ----------- | -----------
/bl create <playername> | Create a Location Board for the player | buddylocator.use
/bl remove | Removes your Locator Board on your screen | buddylocator.use
/bl remove <playername> | Removes the targeted player's Locator Board on thier screen | buddylocator.remove
/bl clear | Removes all Locator Boards from every player | buddylocator.clear
 

# Features
* Display a Buddy's Location, World, Health, and Experience Level on your screen
* Customizable update polling period

# Future Features
* Get a workaround for the re-arranging scores.

# Caveats
* The scores will re-arrange themselves on the board in order of decreasing value. This is a limitation of the native Scoreboard API within Minecraft.

# Changelog
### Version 1.0.1 (Current)
* Built on Spigot-1.16.1
* Patched an NPE error that would be thrown when a player quits the server.
* Added update checking


### Version 1.0
* Built on Spigot-1.16.1
* Release
