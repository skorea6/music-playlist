# Sharing a music playlist
## Demo URL
- WEB Demo URL: [playlist.abz.kr](https://playlist.abz.kr)

## Service Introduction
- This service is a platform that allows you to create playlists while listening to music together in 'real time'.
- Users will be 'voting' and 'chatting' in real time regarding the ongoing music vote.
- 'WebSockets' are a key feature.

## Technology skills used
- Java
- Websocket STOMP
- Spring 3.2.2
- Spring Data JPA
- Spring Security
- MySQL

## Service Flow

### Login and Registration
- **Login**: Enter username and password.
- **Registration**: Provide username, password, and nickname.

### Home and User Screens
- **Home**: Lists all available playlists with play options.
![image](https://github.com/user-attachments/assets/ac75957a-1862-4f92-8eee-4f5769e83b46)
- **My Playlists**: Shows playlists created by the user.


### Playlist Room Creation and Management
- **Create Room**: The host sets up a playlist name and invites others using a link.
- **Music Interaction**: Host shares real-time YouTube music links for live listening, voting, and chatting.
- **Decision Making**: Host decides on the inclusion of tracks based on votes.
- **Finalization**: The playlist is finalized once all tracks are selected.
![image](https://github.com/user-attachments/assets/b3a73a31-e726-421b-ad30-716e7f77b198)

- **Track Management**: View and manage music tracks with options for deletion.
- **Current and New Music**: Display and manage current and newly added tracks.
- **YouTube Integration**: Input links, sync playback, and manage music voting.
- **Chat Feature**: Engage in real-time discussions.
