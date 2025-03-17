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
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class LibraryModel {
	// INSTANCE VARIABLES
	private ArrayList<Song> songs;
	private ArrayList<Artist> artists;
	private ArrayList<Album> albums;
	private ArrayList<Playlist> playlists;
	
	private ArrayList<User> users;
	private LinkedBlockingQueue<Song> recentSongs;
		
	// CONSTRUCTOR 
	public LibraryModel() {
		this.albums = new ArrayList<Album>();
		this.artists = new ArrayList<Artist>();
		this.songs = new ArrayList<Song>();
		this.playlists = new ArrayList<Playlist>();
		
		this.users = new ArrayList<User>();
		this.recentSongs = new LinkedBlockingQueue<Song>(10);
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
	public ArrayList<Song> getSongByTitleOrArtist(String title) {
	    ArrayList<Song> results = new ArrayList<Song>();
	    for (Song s : songs) {
	    	// Ignore case for better user experience
	        if (s.getTitle().equalsIgnoreCase(title) || s.getArtist().equalsIgnoreCase(title)) {
	            results.add(s);
	        }
	    }
	    return results;
	}

/*  // Create new ArrayList with found songs
	public ArrayList<Song> getSongByGenre(String genre) {
	    ArrayList<Song> results = new ArrayList<Song>();
	    for (Song s : songs) {
	    	// Ignore case for better user experience
	        if (s.getAlbum().getGenre().equalsIgnoreCase(genre)) {
	            results.add(s);
	        }
	    }
	    return results;
	}*/
	
	// Create new ArrayList with found albums
	public ArrayList<Album> getAlbumByTitleOrArtist(String title) {
	    ArrayList<Album> results = new ArrayList<>();
	    for (Album a : albums) {
	        if (a.getTitle().equalsIgnoreCase(title) || a.getArtist().equalsIgnoreCase(title)) {
	            results.add(a);
	        }
	    }
	    return results;
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
	
	// Play song from library - check for duplicates and nonexistent.
	public String playSong(String title, MusicStore musicStore, String artist) {
		ArrayList<Song> playingSongs = new ArrayList<>();
		
		// Iterate through the MusicStore songs
		for (Song s : musicStore.getSongs()) {
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
			
			if(!songs.contains(song)) {
				Song incCountSong = song.incrementCount();  // Replace song with increased count song (immutable)
				songs.add(incCountSong);  // Deal with songs directly rather than string ArrayList
				
				// if the playlist has 10 recent played, remove last
				if(recentSongs.remainingCapacity() == 0) {
					recentSongs.poll();
				}
				recentSongs.offer(incCountSong);
				
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
			if (song2.getArtist().equalsIgnoreCase(artist) && !songs.contains(song2)) {
				Song incCountSong2 = song2.incrementCount();
				songs.add(incCountSong2);  // Deal with songs directly rather than string ArrayList
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
			return "Artist needed. More than one song with the same title.";
		}
		
		// Get album-artist match
		for (Album album2 : addingAlbums) {
			if (album2.getArtist().equalsIgnoreCase(artist) && !albums.contains(album2)) {
				albums.add(album2);  // Deal with songs directly rather than string ArrayList
				return "Album: " + album2 + "\nAlbum has been added to library";
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
				return "Song not found in playlist";
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
				return "Song is not in the playlist";
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
		return Collections.unmodifiableList(playlists);
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
	    List<String> songTitles = new ArrayList<>();
	    for (Song s : songs) {
	        songTitles.add(s.getTitle());
	    }
	    for (Album a : albums) {
	        for (Song s : a.getSongs()) {
	        	songTitles.add(s.getTitle());
	        }
	    }
	    return songTitles;
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
	    
	    return artistNames;
	}
	
	// Album titles
	public List<String> getAlbumTitles() {
	    List<String> albumTitles = new ArrayList<>();
	    for (Album a : albums) {
	        albumTitles.add(a.getTitle());
	    }
	    for (Song s : songs) {
	        albumTitles.add(s.getAlbum());
	    }
	    return albumTitles;
	}

	// Playlist names
	public List<String> getPlaylistNames() {
	    List<String> playlistNames = new ArrayList<>();
	    for (Playlist p : playlists) {
	        playlistNames.add(p.getName());
	    }
	    return playlistNames;
	}

	// Favorite Songs
	public List<Song> getFavoriteSongs() {
	    List<Song> favoriteSongs = new ArrayList<>();
	    for (Song s : songs) {
	        if (s.isFavorite()) {
	            favoriteSongs.add(s);
	        }
	    }
	    return favoriteSongs;
	}
	
	// Use stream class to sort and limit (StackOverFlow)
	public List<Song> getFrequentSongs() {
		List<Song> frequentSongs = songs.stream()
				.sorted(Comparator.comparing(Song::getCount).reversed())
				.limit(10)
				.collect(Collectors.toList());
		return new ArrayList<>(frequentSongs);
	}
	
	// return list: shallow copy because song is immutable
	public List<Song> getRecentlyPlayedSongs() {
		return new ArrayList<>(recentSongs);
	}

	
	///SORTING METHODS (bubblesort)
	
	//sort the songs alphabetically by title
	public List<Song> getSortedSongsByTitle() {
		List<Song> sortedByTitle = new ArrayList<>(songs);
		int n = sortedByTitle.size();
		
		for(int i = 0; i < n-1; i++) {
			for(int j = 0; j < n-i-1; j++) {
				if (sortedByTitle.get(j).getTitle().compareToIgnoreCase(sortedByTitle.get(j+1).getTitle()) > 0) {
					Song tempSong = sortedByTitle.get(j);
					sortedByTitle.set(j, sortedByTitle.get(j+1));
					sortedByTitle.set(j+1,  tempSong);
				}
			}
		}
		return sortedByTitle;
	}
	
	//sort the songs alphabetically by artist
	public List<Song> getSortedSongsByArtist() {
		List<Song> sortedByArtist = new ArrayList<>(songs);
		int n = sortedByArtist.size();
		
		for(int i = 0; i < n-1; i++) {
			for(int j = 0; j < n-i-1; j++) {
				if (sortedByArtist.get(j).getArtist().compareToIgnoreCase(sortedByArtist.get(j+1).getArtist()) > 0) {
					Song tempSong = sortedByArtist.get(j);
					sortedByArtist.set(j, sortedByArtist.get(j+1));
					sortedByArtist.set(j+1,  tempSong);
				}
			}
		}
		return sortedByArtist;
	}
	
		
	//sort songs by rating in  ascending order
	public List<Song> getSortedSongsByRating() {
		List<Song> sortedByRating = new ArrayList<>(songs);
		int n = sortedByRating.size();
		
		for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (sortedByRating.get(j).getRating().getStars() > sortedByRating.get(j+1).getRating().getStars()) {
          
                    Song temp = sortedByRating.get(j);
                    sortedByRating.set(j, sortedByRating.get(j+1));
                    sortedByRating.set(j+1, temp);
                }
            }
        }
        return sortedByRating;
		
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
                albums.remove(album);
                return "Removed the album " + title + " by " + artist;
            }
        }
        return "Album was not found";
    }
    
    
    public boolean albumInLibrary(Album album) {
    	return albums.contains(album);
    }


	
	
	
	
	// Load MusicStore info for use
	public void loadAlbums() throws IOException {
		MusicStore musicStore = new MusicStore();
		musicStore.loadAlbums();
	}
	
}
