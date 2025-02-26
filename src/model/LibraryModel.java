package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class LibraryModel {
	// INSTANCE VARIABLES
	
	//make the instance variables Lists of objects(Song,Artist etc) rather than Arraylists of Strings. can deal with the objects directly.(they are objects themselves, just like Strings are)
	private ArrayList<Song> songs;
	private ArrayList<Artist> artists;
	private ArrayList<Album> albums;
	//add playlists
	private ArrayList<Playlist> playlists;
	
	
	
	/*
	private ArrayList<Song> songs;
	private ArrayList<Artist> artists;
	private ArrayList<Album> albums;
	//private ArrayList<Playlist> playlists;
	*/
	
	
	
	// CONSTRUCTOR
	public LibraryModel() {
		this.albums = new ArrayList<Album>();
		//this.playlists = new ArrayList<Playlist>();
		this.artists = new ArrayList<Artist>();
		// initialize songs CR 2/17
		this.songs = new ArrayList<Song>();
		this.playlists = new ArrayList<Playlist>();
		
		
	}
	
	
	// METHODS
	
	// SEARCH AND RETURN (can have more than one. that's why array)
	// Also prints all results
	/*
	// return song given title
	public ArrayList<String> getSongByTitleOrArtist(String title) {
		ArrayList<String> songsList = new ArrayList<String>();
		for (Song s : songs) {
			if (s.getTitle().equals(title) || s.getArtist().equals(title)) {
				songsList.add(s.toString());
			}
		}
		
		if (!songsList.isEmpty()) {
			return songsList;
		}
		else {
			songsList.add("Song is not available.");
			return songsList;
		}
	}
	*/
	
	
	//change these methods to Lists of Song type. can then return a list of Songs rather than an arraylist.
	public ArrayList<Song> getSongByTitleOrArtist(String title) {
	    ArrayList<Song> results = new ArrayList<Song>();
	    for (Song s : songs) {
	    	//ignore case so client input is case insensitive as explained on piazza
	        if (s.getTitle().equalsIgnoreCase(title) || s.getArtist().equalsIgnoreCase(title)) {
	            results.add(s);
	        }
	    }
	    return results;
	}
	
	
	//same logic here. return a list of albums rather than an arraylist of strings
	public ArrayList<Album> getAlbumByTitleOrArtist(String title) {
	    ArrayList<Album> results = new ArrayList<>();
	    for (Album a : albums) {
	    	//make search case insensitive, like on spotify
	        if (a.getTitle().equalsIgnoreCase(title) || a.getArtist().equalsIgnoreCase(title)) {
	            results.add(a);
	        }
	    }
	    return results;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	// return song given artist
	/*public ArrayList<String> getSongByArtist(String artist) {
		ArrayList<String> songsList = new ArrayList<String>();
		for (Song s : songs) {
			if (s.getArtist().equals(artist)) {
				songsList.add(s.toString());
			}
		}
		
		if (!songsList.isEmpty()) {
			return songsList;
		}
		else {
			songsList.add("SSSong is not available.");
			return songsList;
		}
	}*/
	/*
	// return album given title
	public ArrayList<String> getAlbumByTitleOrArtist(String title) {
		ArrayList<String> albumsList = new ArrayList<String>();
		for (Album a : albums) {
			if (a.getTitle().equals(title) || a.getArtist().equals(title)) {
				albumsList.add(a.toString());
			}
		}
		
		if (!albumsList.isEmpty()) {
			return albumsList;
		}
		else {
			albumsList.add("Album is not available.");
			return albumsList;
		}
	}
	*/
	// return album given artist
	/*public ArrayList<String> getAlbumByArtist(String artist) {
		ArrayList<String> albumsList = new ArrayList<String>();
		for (Album a : albums) {
			if (a.getArtist().equals(artist)) {
				albumsList.add(a.toString());
			}
		}
		
		if (!albumsList.isEmpty()) {
			return albumsList;
		}
		else {
			albumsList.add("Album is not available.");
			return albumsList;
		}
	}*/	
	// to add a song to library we dont need to return any Song objects. can make this void.
	//public Song addSong(String title) {
	
	
	//public void addSong(String title) {
	public void addSong(String title, MusicStore musicStore) {
		//ArrayList<String> songsList = new ArrayList<String>();        //no need for arraylist when we can deal with songs directly
		//for (Song s : songs) {
		for (Song s : musicStore.getSongs()) {
			//ignore case
			if (s.getTitle().equalsIgnoreCase(title)) {
				//songsList.add(s.toString());
				
				//avoid adding duplicates
				if(!songs.contains(s)) {
					songs.add(s);  //deal with songs directly rather than string arraylist
					System.out.println("Song " + s + " added to library");
					return;
				}
				else {
					System.out.println("Duplicate song");
					return;
				}
			}
		}
		//if this code is reached, the song is not in the music store
		System.out.println("Song is not available.");
	}
		/*
		if (!songsList.isEmpty()) {
			return songsList[0];
		}
		
		else if(songsList.size() > 1) {
			
		}
		
		else {
			songsList.add("Album is not available.");
			return songsList[0];
		}
	}
	*/
	
	
	
	//void return type, no need to return anything
	//public Song addAlbum(String title) {
	//public void addAlbum(String title) {
	public void addAlbum(String title, MusicStore musicStore) {
		//ArrayList<String> albumList = new ArrayList<String>();       //
		//for (Album a : albums) {
		for (Album a : musicStore.getAlbums()) {
			//ignore case
			if (a.getTitle().equalsIgnoreCase(title)) {
				//avoid duplicates
				if(!albums.contains(a)) {
					albums.add(a);   //add the album to 
					System.out.println("Album " + a + " \nadded to the library");
					return;
				}
				else {
					System.out.println("Duplicate album");
					return;
				}
				
			}
		}
		// if code gets here then the almub is not in the music store
		System.out.println("Album is not available.");
	}
	/*
		if (!albumList.isEmpty()) {
			return albumList[0];
		}
		
		else if(albumList.size() > 1) {
			
		}
		
		else {
			albumList.add("Album is not available.");
			return albumList[0];
		}
	}
	*/
	
	
	
	
	//function to create playlists
	public void createPlaylist(String title) {
		playlists.add(new Playlist(title));
		System.out.println("Playlist " + title + " added.");
	}
	
	
	
	
	
	public void addToPlaylist(String nameOfPlaylist, String song) {
		for(Playlist p : playlists) {
			if(p.getName().equalsIgnoreCase(nameOfPlaylist)) {
				for (Song s : songs) {
					if (s.getTitle().equalsIgnoreCase(song)) {
						p.addSong(s);
						System.out.println(s.getTitle() + "is now in playlist " + nameOfPlaylist);
						return;
					}
				}
				//if code gets here then song is not found
				System.out.println("song not found");
				return;
			}
		}
		System.out.println("this playlist does not exist");
	}
	
	//method to remove song from playlist
	public void removeFromPlaylist(String nameOfPlaylist, String song) {
		for (Playlist p : playlists) {
			if(p.getName().equalsIgnoreCase(nameOfPlaylist)) {
				for (Song s : p.getSongs()) {
					if (s.getTitle().equalsIgnoreCase(song)) {
						p.removeSong(s);
						System.out.println("removed " + s.getTitle() + " from playlist");
						return;
					}
					
				}
				//if code gets here, song is not found
				System.out.println("song is not in the playlist");
				return;
			}
		}
		System.out.println("No playlist exists by that name");
	}
	
	
	
	
	
	//function to rate the songs
	public void rateSong(String title, int rating) {
	    for (Song s : songs) {
	        if (s.getTitle().equalsIgnoreCase(title)) {
	        	//update song fields with a rating
	            Song ratedSong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), Rating.values()[rating], s.isFavorite());
	            //replace the old song with the song that now has a rating
	            songs.remove(s);
	            songs.add(ratedSong);
	            System.out.println("Song " + ratedSong + "rated.");
	            return;
	        }
	    }
	    //if code gets here the song is not in the library
	    System.out.println("Song is not in the library.");
	}
	
	
	//function to mark a song as favorite
	public void setToFavorite(String title) {
		for (Song s : songs) {
			if (s.getTitle().equalsIgnoreCase(title)) {
				Song favoriteSong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating(), true);
				//replace old song with new version of song set as favorite
				songs.remove(s);
				songs.add(favoriteSong);
				System.out.println("Song" + favoriteSong + "set to favorite.");
				return;
			}
		}
	}
	
	
	// GET ENTIRE LISTS
	// SETTERS AND GETTERS
	// return Collections.unmodifiableList rather than ArrayLists for encapsulation
	
	public List<Album> getAlbums() {
		//return new ArrayList<>(albums);
		return Collections.unmodifiableList(albums);
	}
	
	//this should work now
	public List<Playlist> getPlaylists() {
		//return new ArrayList<>(playlists);
		return Collections.unmodifiableList(playlists);
	}
	
	public List<Song> getSongs() {
		//return new ArrayList<>(songs);
		return Collections.unmodifiableList(songs);
	}

	
	public List<Artist> getArtists() {
		//return new ArrayList<>(artists);
		return Collections.unmodifiableList(artists);
	}
	
	public void loadAlbums() throws IOException {
		MusicStore musicStore = new MusicStore();
		
		musicStore.loadAlbums();
		
		// could use a setter, but idk about encapsulation?
		
		//i think this loads all the songs in MusicStore into the library intitailly, and the library is supposed to be empty initially
		
		/*
		songs = musicStore.getSongs();
		albums = musicStore.getAlbums();
		artists = musicStore.getArtists();
		*/
	}
	
}
