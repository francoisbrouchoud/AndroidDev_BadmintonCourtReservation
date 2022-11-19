# Badminton courts reservation android App
**Authors:** François Brouchoud and Luca Del Buono

## Courts
In our app, we can add new courts by clicking on the plus (floating action button). We can edit them by long-clicking on the court line. On the edit page, we can delete the court by clicking on the trash icon.

## Players
We can create new players by on the plus (floating action button). By long-clicking, we can delete the player. The simple click will allow the user to edit the player.

## Reservations activity
To reserve a court, we need to simple click on a court line. We can also delete the reservation by long-clicking on the line from the reservation page. A simple click will display the edit reservation page.

### Reservation check
When we create a new reservation, the following checks will be done:
* Check if the date and time are not empty
* Check if the date and time are in the past
* Check if there is already a reservation for the same court at the same time and date

## Settings
The settings are accessible with the three dots on the toolbar.

### Dark mode
It is possible to switch between light and dark mode. This mode will be saved when the user quits the app and will be reloaded at the same mode on app reopening.

### About
The about page displays some info about the app. Furthermore it's possible to write feedback and send it to the developers by email. The HES-SO logo will automatically refresh depending on the theme (dark or light).