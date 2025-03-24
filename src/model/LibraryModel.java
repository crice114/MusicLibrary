/* LibraryModel.java
 * This Java file include the LibraryModel class.
 * It incorporates the program functionality and 
 * connects with the View and MusicStore to retrieve
 * and return information. */

package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class LibraryModel {
	// INSTANCE VARIABLES
	private ArrayList<Song> songs;
	private ArrayList<Artist> artists;
	private ArrayList<Album> albums;
	private ArrayList<Playlist> playlists;
	
	private ArrayList<User> users;
	private Deque<Song> recentSongs;
	
	private ArrayList<Playlist> playlistsAuto;
		
	// CONSTRUCTOR 
	public LibraryModel() {
		this.albums = new ArrayList<Album>();
		this.artists = new ArrayList<Artist>();
		this.songs = new ArrayList<Song>();
		this.playlists = new ArrayList<Playlist>();
		
		this.users = new ArrayList<User>();
		this.recentSongs = new LinkedList<Song>();
		
		this.playlistsAuto = new ArrayList<Playlist>();
	}
	
	// METHODS
	public User checkUser(String username) {
		for (User u : users) {
			if (u.getUsername().equals(username)) {
				return u;
			}
		}
		return null;
	}
	
	public void addUser(User user) {
		users.add(user);
	}
	
	
	// Create new ArrayList with found songs
	public ArrayList<Song> getSongsByTitleOrArtist(String title) {
	    ArrayList<Song> results = new ArrayList<Song>();
	    for (Song s : songs) {
	    	// Ignore case for better user experience
	        if (s.getTitle().equalsIgnoreCase(title) || s.getArtist().equalsIgnoreCase(title)) {
	            results.add(s);
	        }
	    }
	    return new ArrayList<>(results);
	}
	
	// Create new ArrayList with found albums
	public ArrayList<Album> getAlbumsByTitleOrArtist(String title) {
	    ArrayList<Album> results = new ArrayList<>();
	    for (Album a : albums) {
	        if (a.getTitle().equalsIgnoreCase(title) || a.getArtist().equalsIgnoreCase(title)) {
	            results.add(a);
	        }
	    }
	    return new ArrayList<>(results);
	}
	
    public ArrayList<Song> getSongsByGenre(String genre) {
    	ArrayList<Song> results = new ArrayList<>();
    	for (Song s : songs) {
    		String songAlbum = s.getAlbum();
    		for (Album a : albums) {
    			if (a.getTitle().equalsIgnoreCase(songAlbum) && a.getGenre().equalsIgnoreCase(genre)) {
    				results.add(s);
    			}
    		}
    	}
    	return new ArrayList<>(results);
    }
	
	// Add song to library - check for duplicates and nonexistent.
	public String addSong(String title, MusicStore musicStore, String artist) {
		ArrayList<Song> addingSongs = new ArrayList<>();
		
		// Iterate through the MusicStore songs
		for (Song s : musicStore.getSongs()) {
			// Ignore case
			if (s.getTitle().equalsIgnoreCase(title)) {
					addingSongs.add(s);  // Deal with songs directly rather than string ArrayList
			}
		}
		
		// Nonexistent song
		if (addingSongs.size() == 0) {
			return "Song not in store!";
		}
		
		// One song available -> check if duplicated
		if (addingSongs.size() == 1) {
			Song song = addingSongs.get(0);
			
			if(!songs.contains(song)) {
				songs.add(song);  // Deal with songs directly rather than string ArrayList
				
				boolean albumFound = false;
				
				for(Album a : albums) {
					if (a.getTitle().equalsIgnoreCase(song.getAlbum())) {
						albumFound = true;
						break;
					}
				}
				
				if (albumFound == false) {
					addPartAlbum(song.getAlbum(), musicStore, song.getArtist());
				}
				
				return "Song " + song + " added to library";
			}
			else {
				return "Duplicated song";
			}
		}
		
		// If more than one song with same title, go back to ask for artist
		else if (artist == null) {
			return "Artist needed.";
		}
		
		// Get song-artist match
		for (Song song2 : addingSongs) {
			if (song2.getArtist().equalsIgnoreCase(artist) && !songs.contains(song2)) {
				songs.add(song2);  // Deal with songs directly rather than string ArrayList
				return "Song " + song2 + " added to library";
			}
		}
		return "Try again.";
	}
	
	public String addPartAlbum(String title, MusicStore musicStore, String artist) {
		ArrayList<Album> addingAlbums = new ArrayList<>();
		
		// Iterate through the MusicStore songs
		for (Album a : musicStore.getAlbums()) {
			// Ignore case
			if (a.getTitle().equalsIgnoreCase(title)) {
					addingAlbums.add(a);  // Deal with songs directly rather than string ArrayList
			}
		}
		
		// Nonexistent album
		if (addingAlbums.size() == 0) {
			return "Album not in store!";
		}
		
		// One album available -> check if duplicated
		if (addingAlbums.size() == 1) {
			Album album = addingAlbums.get(0);
			
			if(!albums.contains(album)) {
				albums.add(album);  // Deal with albums directly rather than string ArrayList
				return "Album " + album + "\nadded to library";
			}
			else {
				return "Duplicated album";
			}
		}
		
		// If more than one album with same title, go back to ask for artist
		else if (artist == null) {
			return "Artist needed. More than one album with the same title.";
		}
		
		// Get album-artist match
		for (Album album2 : addingAlbums) {
			if (album2.getArtist().equalsIgnoreCase(artist) && !albums.contains(album2)) {
				if(!albums.contains(album2)) {
					albums.add(album2);  // Deal with albums directly rather than string ArrayList
					return "Album " + album2 + "\nadded to library";
				}
			}
		}
		return "Try again.";
	}
	
	// Play song from library - check for duplicates and nonexistent.
	public String playSong(String title, String artist) {
		ArrayList<Song> playingSongs = new ArrayList<>();
		
		// Iterate through the MusicStore songs
		for (Song s : songs) {
			// Ignore case
			if (s.getTitle().equalsIgnoreCase(title)) {
					playingSongs.add(s);  // Deal with songs directly rather than string ArrayList
			}
		}
		
		// Nonexistent song
		if (playingSongs.size() == 0) {
			return "Song not in store!";
		}
		
		// One song available -> check if duplicated
		if (playingSongs.size() == 1) {
			Song song = playingSongs.get(0);
			
			if(songs.contains(song)) {
				Song incCountSong = song.incrementCount();  // Replace song with increased count song (immutable)
				songs.remove(song);
				songs.add(incCountSong);  // Deal with songs directly rather than string ArrayList
				
				// remove if instead of for loop (avoid exception)
				recentSongs.removeIf(s -> (s.toString().equals(incCountSong.toString())));
				
				// if the playlist has 10 recent played, remove last
				if(recentSongs.size() == 10) {
					recentSongs.pollLast();
				}
				
				recentSongs.addFirst(incCountSong);
				
				return "Song " + incCountSong + " played\nCount: " + incCountSong.getCount();
			}
			else {
				return "Song cannot be played";
			}
		}
		
		// If more than one song with same title, go back to ask for artist
		else if (artist == null) {
			return "Artist needed.";
		}
		
		// Get song-artist match
		for (Song song2 : playingSongs) {
			if (song2.getArtist().equalsIgnoreCase(artist) && songs.contains(song2)) {
				Song incCountSong2 = song2.incrementCount();
				songs.remove(song2);
				songs.add(incCountSong2);  // Deal with songs directly rather than string ArrayList
				if(recentSongs.size() == 10) {
					recentSongs.pollLast();
				}
				recentSongs.remove(incCountSong2);
				recentSongs.addFirst(incCountSong2);
				return "Song " + incCountSong2 + " played\nCount: " + incCountSong2.getCount();
			}
		}
		return "Try again.";
	}
	
	// Add song to library - check for duplicates and nonexistent.
	public String addAlbum(String title, MusicStore musicStore, String artist) {
		ArrayList<Album> addingAlbums = new ArrayList<>();
		
		// Iterate through the MusicStore songs
		for (Album a : musicStore.getAlbums()) {
			// Ignore case
			if (a.getTitle().equalsIgnoreCase(title)) {
					addingAlbums.add(a);  // Deal with songs directly rather than string ArrayList
			}
		}
		
		// Nonexistent album
		if (addingAlbums.size() == 0) {
			return "Album not in store!";
		}
		
		// One album available -> check if duplicated
		if (addingAlbums.size() == 1) {
			Album album = addingAlbums.get(0);
			
			int songsInAlbumLibrary = 0;
			
			for(Song s : album.getSongs()) {
				if(songs.contains(s)) {
					songsInAlbumLibrary++;
				}
			}
			
			if (songsInAlbumLibrary == album.getSongs().size()) {
				return "Duplicated album";
			}
			
			for(Song s : album.getSongs()) {
				if (!songs.contains(s)) {
					songs.add(s);
				}
			}
			
			if(!albums.contains(album)) {
				for (Song s : album.getSongs()) {
					if (!songs.contains(s)) {
						songs.add(s);
					}
				}
				albums.add(album);  // Deal with albums directly rather than string ArrayList
			}
			return "Album " + album + "\nadded to library";
		}
		
		// If more than one album with same title, go back to ask for artist
		else if (artist == null) {
			return "Artist needed. More than one album with the same title.";
		}
		
		// Get album-artist match
		for (Album album2 : addingAlbums) {
			if (album2.getArtist().equalsIgnoreCase(artist) && !albums.contains(album2)) {
				int songsInAlbumLibrary2 = 0;
				
				for(Song s2 : album2.getSongs()) {
					if(songs.contains(s2)) {
						songsInAlbumLibrary2++;
					}
				}
				
				if (songsInAlbumLibrary2 == album2.getSongs().size()) {
					return "Duplicated album";
				}
				
				for(Song s2 : album2.getSongs()) {
					if (!songs.contains(s2)) {
						songs.add(s2);
					}
				}
				
				if(!albums.contains(album2)) {
					for (Song s2 : album2.getSongs()) {
						if (!songs.contains(s2)) {
							songs.add(s2);
						}
					}
					albums.add(album2);  // Deal with albums directly rather than string ArrayList
					return "Album " + album2 + "\nadded to library";
				}
			}
		}
		return "Try again.";
	}
	
	// Create playlist using name
	public String createPlaylist(String title) {
		for (Playlist p : playlists) {
			if(p.getName().equalsIgnoreCase(title)) {
				return "Playlist " + title + " already exists in library.";
			}
		}
		for (Playlist p : playlistsAuto) {
			if(p.getName().equalsIgnoreCase(title)) {
				return "Playlist " + title + " automatically exists in library and cannot be manually edited.";
			}
		}
		playlists.add(new Playlist(title));
		return "Playlist " + title + " added.";
	}
	
	// Add song to playlist and return string result
	public String addToPlaylist(String nameOfPlaylist, String song) {
		for(Playlist p : playlists) {
			if(p.getName().equalsIgnoreCase(nameOfPlaylist)) {
				for (Song s : songs) {
					if (s.getTitle().equalsIgnoreCase(song)) {
						p.addSong(s);
						return s.getTitle() + " is now in playlist " + nameOfPlaylist;
					}
				}
				//if code gets here then song is not found
				return "Song not found in library";
			}
		}
		return "This playlist does not exist";
	}
	
	// Remove song from playlist and return string result
	public String removeFromPlaylist(String nameOfPlaylist, String song) {
		for (Playlist p : playlists) {
			if(p.getName().equalsIgnoreCase(nameOfPlaylist)) {
				for (Song s : p.getSongs()) {
					if (s.getTitle().equalsIgnoreCase(song)) {
						p.removeSong(s);
						return "Removed " + s.getTitle() + " from playlist";
					}
					
				}
				//if code gets here, song is not found
				return "Song is not in the library";
			}
		}
		return "No playlist exists by that name";
	}
	
	// Get playlist by name
	public Playlist getPlaylistByName(String name) {		
	    for (Playlist p : playlists) {
	        if (p.getName().equalsIgnoreCase(name)) {
	            return p;
	        }
	    }
	    
	    for (Playlist p : playlistsAuto) {
	        if (p.getName().equalsIgnoreCase(name)) {
	            return p;
	        }
	    }
	    
	    // Return null if no playlist is found
	    return null;
	}
		
	// Rate songs using the rating enum
	public String rateSong(String title, int rating) {
	    for (Song s : songs) {
	        if (s.getTitle().equalsIgnoreCase(title)) {
	        	// Update song fields with a rating
	            Song ratedSong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), Rating.values()[rating], s.isFavorite(), s.getCount());
	            // Replace the old song with the song that now has a rating
	            songs.remove(s);
	            songs.add(ratedSong);
	            
	            // If rating is 5 stars, automatically set it to favorite.
	            if(Rating.values()[rating] == Rating.FIVE) {
	            	setToFavorite(title);
	            	return ratedSong.getTitle() + " rated " + Rating.values()[rating].getStars() + "/5 stars and has been added to favorites.";
	            }
	           
	           // Rate regular song (not favorite)
	           return ratedSong.getTitle() + " rated " + Rating.values()[rating].getStars() + "/5 stars";
	        }
	    }
	    // If code gets here the song is not in the library
	    return "Song is not in the library.";
	}
	
	
	// Function to mark a song as favorite
	public String setToFavorite(String title) {
		for (Song s : songs) {
			if (s.getTitle().equalsIgnoreCase(title)) {
				Song favoriteSong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating(), true, s.getCount());
				// Replace old song with new version of song set as favorite
				songs.remove(s);
				songs.add(favoriteSong);
				return favoriteSong + " set to favorite.";
			}
		}
		return "Song not found.";
	}
	
	
	// GET ENTIRE LISTS
	// SETTERS AND GETTERS
	// return Collections.unmodifiableList rather than ArrayLists for encapsulation
	
	public List<Album> getAlbums() {
		return Collections.unmodifiableList(albums);
	}
	
	public List<Playlist> getPlaylists() {
		ArrayList<Playlist> masterP = new ArrayList<>();
		masterP.addAll(playlists);
		masterP.addAll(playlistsAuto);
		
		return Collections.unmodifiableList(masterP);
	}
	
	public List<Song> getSongs() {
		return Collections.unmodifiableList(songs);
	}

	public List<Artist> getArtists() {
		return Collections.unmodifiableList(artists);	
	}
	
	// Add getters to return lists of artists, songs, albums, playlists (per spec)
	// Song titles by iterating songs and albums
	public List<String> getSongTitles() {
		// Set avoids duplicated
	    Set<String> songTitles = new HashSet<>();
	    // use toString to identify songs with same titles
	    for (Song s : songs) {
	        songTitles.add(s.toString());
	    }
	    for (Album a : albums) {
	        for (Song s : a.getSongs()) {
	        	if(songs.contains(s)) {
	        		songTitles.add(s.toString());
	        	}
	        }
	    }
	    return new ArrayList<>(songTitles);
	}
	
	public List<String> getArtistNames() {
	    List<String> artistNames = new ArrayList<>();
	    
	    // Artists from songs
	    for (Song s : songs) {
	        if (!artistNames.contains(s.getArtist())) {
	            artistNames.add(s.getArtist());
	        }
	    }
	    
	    // Artists from albums
	    for (Album a : albums) {
	        if (!artistNames.contains(a.getArtist())) {
	            artistNames.add(a.getArtist());
	        }
	    }
	    
	    return new ArrayList<>(artistNames);
	}
	
	// Album titles
	public List<String> getAlbumTitles() {
	    List<String> albumTitles = new ArrayList<>();
	    for (Album a : albums) {
	    	if(!albumTitles.contains(a.getTitle())) {
	    		albumTitles.add(a.getTitle());
	    	}
	    }
	    for (Song s : songs) {
	    	if(!albumTitles.contains(s.getAlbum())) {
	    		albumTitles.add(s.getAlbum());
	    	}
	    }
	    return new ArrayList<>(albumTitles);
	}

	// Playlist names
	public List<String> getPlaylistNames() {
	    List<String> playlistNames = new ArrayList<>();
	    for (Playlist p : playlists) {
	        playlistNames.add(p.getName());
	    }
	    for (Playlist p : playlistsAuto) {
	        playlistNames.add(p.getName());
	    }
	    return new ArrayList<>(playlistNames);
	}

	// Favorite Songs
	public List<Song> getFavoriteSongs() {
	    List<Song> favoriteSongs = new ArrayList<>();
	    for (Song s : songs) {
	        if (s.isFavorite()) {
	            favoriteSongs.add(s);
	        }
	    }
	    return new ArrayList<>(favoriteSongs);
	}
	
	// Comparator sorting algorithm - have all three sorting ways in one method
	// let user query
	public List<String> getSortedSongs(String method) {
		List<Song> sorted = new ArrayList<>(songs);
		
		switch(method.toLowerCase()) {
		case "title":
			Collections.sort(sorted, Song.titleFirstComparator());
			break;
		
		case "artist":
			Collections.sort(sorted, Song.artistFirstComparator());
			break;
	
		case "rating":
			Collections.sort(sorted, Song.ratingFirstComparator());
			break;
			
		default:
			return new ArrayList<>();
		}
		
		List<String> sortedStr = new ArrayList<>();
		
		for (Song s : sorted) {
			sortedStr.add(s.toString());
		}
		
		return new ArrayList<>(sortedStr);
	}	
		
	//use iterator to shuffle songs
    public Iterator<Song> shuffleLibrary() {
        List<Song> shuffledList = new ArrayList<>(songs);
        Collections.shuffle(shuffledList);
        return shuffledList.iterator();
    }
	    
	    
	 // Remove a song from the library
    public String removeSong(String title, String artist) {
        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(title) && song.getArtist().equalsIgnoreCase(artist)) {
                songs.remove(song);
                return "Removed the song " + title + " by " + artist;
            }
        }
        return "Song was not found";
    }
	    
    //get shuffled playlist
    public Iterator<Song> getShuffledPlaylist(String playlistName) {
        Playlist playlist = getPlaylistByName(playlistName);
        if (playlist != null) {
            return playlist.shufflePlaylist();
        }
        return null; // Return null if the playlist does not exist
    }


    public String removeAlbum(String title, String artist) {
        for (Album album : albums) {
            if (album.getTitle().equalsIgnoreCase(title) && album.getArtist().equalsIgnoreCase(artist)) {
            	for (Song s : album.getSongs()) {
            		songs.remove(s);
				}
            	albums.remove(album);
                return "Removed the album " + title + " by " + artist;
            }
        }
        return "Album was not found";
    }
    
    
    public boolean albumInLibrary(Album album) {
    	return albums.contains(album);
    }
	
	// genre playlist
	public List<Playlist> getAutomaticPlaylists() {
		playlistsAuto.clear();
		
		// HashMap with genre, number of songs order
		HashMap<String, Integer> numSongsGenre = new HashMap<String, Integer>();
		List<Playlist> nonUserP = new ArrayList<>();
				
		Playlist pRecent = new Playlist("Recently Played Songs");
		Playlist pFrequent = new Playlist("Frequently Played Songs");
		Playlist pFav = new Playlist("Favorite Songs");
		Playlist pTop = new Playlist("Top Rated Songs");
		
		for (Song s : songs) {
	        if (s.isFavorite()) {
	            pFav.addSong(s);
	        }
	        if (s.getRating() == Rating.FOUR || s.getRating() == Rating.FIVE) {
	            pTop.addSong(s);
	        }
	    }
		
		List<Song> frequentSongsCount = new ArrayList<>();
		
		for (Song s : songs) {
			if (s.getCount() >= 1) {
				frequentSongsCount.add(s);
			}
		}
		
		// Use stream class to sort and limit (StackOverFlow)
		List<Song> frequentSongs = frequentSongsCount.stream()
				.sorted(Comparator.comparing(Song::getCount).reversed())
				.limit(10)
				.collect(Collectors.toList());
		
	    for (Song s : frequentSongs) {
	    	pFrequent.addSong(s);
	    }
	    
		for (Song s : recentSongs) {
	    	pRecent.addSong(s);
	    }
		
	    nonUserP.add(pFrequent);
	    nonUserP.add(pRecent);
		nonUserP.add(pTop);
		nonUserP.add(pFav);		
		
		// populate hashmap with genre and number of songs with said genre
		for (Song s : songs) {
			String albumTitle = s.getAlbum();
			for (Album a: albums) {
				if (a.getTitle().equals(albumTitle)) {
					if (!numSongsGenre.containsKey(a.getGenre())) {
						numSongsGenre.put(a.getGenre(), 1);
					}
					else {
						numSongsGenre.put(a.getGenre(), numSongsGenre.get(a.getGenre()) + 1);
					}
				}
			}
		}
		
		// use entry to get key:value element, create playlists using genre name
		for (Entry<String, Integer> entry : numSongsGenre.entrySet()) {
			if(entry.getValue() >= 10) {
				Playlist genreP = new Playlist(entry.getKey());
				for (Song s : songs) {
					String albumTitle = s.getAlbum();
					for (Album a : albums) {
						if (a.getTitle().equals(albumTitle) && a.getGenre().equals(genreP.getName())) {
							genreP.addSong(s);
						}
					}
				}
				// since there are more than one possible playlists, add them to a bigger list
				nonUserP.add(genreP);
			}
		}
		playlistsAuto.addAll(nonUserP);
		return Collections.unmodifiableList(nonUserP);
	}

	// clear lib for each user
	public void clearLibrary() {
	    songs.clear();
	    albums.clear();
	    playlists.clear();
	}
	
	// Methods for loading user info
	public void addSong(Song song, String genre, int year) {
	    if (!songs.contains(song)) {
	        songs.add(song);
	    }
	  
	    boolean albumExists = false;
	    
	    for (Album a : albums) {
	        if (a.getTitle().equalsIgnoreCase(song.getAlbum())) {
	            albumExists = true;
	            break;
	        }
	    }
	    
	    if (!albumExists) {
	        // You can try loading it from the MusicStore if needed
	        // For now, we assume only song is saved, so album metadata may not be reloaded
	    	albums.add(new Album(song.getAlbum(), song.getArtist(), genre, year));
	    }
	}

	public void addPlaylists(List<Playlist> loadedPlaylists) {
	    for (Playlist p : loadedPlaylists) {
	        playlists.add(p); // Only user-created playlists
	    }
	}
	
	public List<Playlist> getUserPlaylists() {
	    return Collections.unmodifiableList(playlists);
	}


	
	
	// Load MusicStore info for use
	public void loadAlbums() throws IOException {
		MusicStore musicStore = new MusicStore();
		musicStore.loadAlbums();
	}
	
}



