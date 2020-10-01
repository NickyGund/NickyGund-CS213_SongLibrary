package view;
import java.util.Comparator;
	//recreated this class because I accidentally deleted the old one ;)
public class Song{
	// Private to prevent accidental access
	private String name;
	private String artist;
	private String album = "N/A";
	private String year = "N/A";
	//---------------------------------------------------------------------------------------------
	// constructor to create song object, requires all fields, use if you need 3 or 4
	//---------------------------------------------------------------------------------------------
	public Song(String name, String artist, String album, String year) {
		this.name = name;
		this.artist = artist;
		if(!album.equals("")) {
			this.album = album;
		}
		if(!year.equals("")) {
			this.year = year;
		}
	}
	//-----------------------------------------------------------------------------------------------
	// overloaded constructor only requiring name and artist field, use if you need the 2 only
	//-----------------------------------------------------------------------------------------------
	public Song(String name, String artist) {
		this.name = name;
		this.artist = artist;
	}
	//-----------------------------------------------------------------------------------------------
	// set methods
	//-----------------------------------------------------------------------------------------------
	public void setName(String name) {
		this.name = name;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public void setYear(String year) {
		this.year = year;
	}
	//-------------------------------------------------------------------------------------------------
	// get methods
	//-------------------------------------------------------------------------------------------------
	public String getName() {
		return name;
	}
	public String getArtist() {
		return artist;
	}
	public String getAlbum() {
		return album;
	}
	public String getYear() {
		return year;
	}
	//--------------------------------------------------------------------------------------------------
	// Comparator for sorting the list for Song methods
	//--------------------------------------------------------------------------------------------------
	// sort by song name
	public Comparator<Song> sgNmeComparator =new Comparator<Song>() {
		public int compare(Song s1,Song s2) {
			String songName1 = s1.getName().toUpperCase();
			String songName2 = s2.getName().toUpperCase();
			
			return songName1.compareTo(songName2);
		}
	};
	// sort by artist
	public Comparator<Song> sgArtComparator =new Comparator<Song>() {
		public int compare(Song s1,Song s2) {
			String songArtist1 = s1.getName().toUpperCase();
			String songArtist2 = s2.getName().toUpperCase();
			
			return songArtist1.compareTo(songArtist2);
		}
	};
}
