Music Player

The user will be able to go to a /login.html URL, which contains a form with a text entry for a username and password. There will be a 24-hours cookie that indicates the client's username. The loginServlet's doPost method will open up the SQLite DB and check the provided password against what is stored. If this is a match, the user will be returned to a page with a list of all the songs in their DB. If it is not, send them back to login.html, with a message indicating that they were not successful.

Features:
- Searching by artist, album, and song
- Building a playlist. 
- Play music
- Add and delete songs
- Search artist Bio using a REST API provided by TheAudioDB
