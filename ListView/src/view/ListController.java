// Sai Nayan Malladi srm275
// Nicolas Gundersen neg62
// CS 213 Software Methodology 
// Sesh

// The ListController class adds as the 'controller' between our model(the fxml file), and our ListApp. Our Listcontroller contains all of our main
// functions.
package view;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

@SuppressWarnings("unused")
public class ListController {
	//Initialize our buttons from our fxml. These variables point to the fx:id on the fxml.
	// Also initialize our ListView, ObservableList, and our ArrayList(Song)
	@FXML ListView<String> listView;                
	@FXML Button addSongButton;
	@FXML Button editSongButton;
	@FXML Button deleteSongButton;
	private ObservableList<String> obsList;    
	ArrayList<Song> songArrayList = new ArrayList<Song>();
		
	
	public void start(Stage mainStage) {                
		// create an ObservableList 
		// from an ArrayList  
		// In this case our observable ArrayList is empty, and we will populate it from the songArrayList
		obsList = FXCollections.observableArrayList();
		
		try {
			FileReader reader = new FileReader(
				"songData.txt");
			if(reader==null) {
				System.out.println("Error: no songData.txt file found");
				System.out.println("songData.txt must be in the project folder");
			}
			int chara;
			String stream ="";
			String name ="";
			String artist ="";
			String album ="";
			String year ="";
			
			//read entire txt into stream string
			while ((chara = reader.read()) != -1) {
				stream+=(char)chara;
			}
			if (!stream.equals("")) {
				
			//split into array of song's information
			String[] tokens = stream.split("\n");
			for(int i=0;i< tokens.length;i++) {
				//split up info within songs
				String[] info = tokens[i].split(",");
				
				name = info[0];
				artist = info[1];
				if(info.length == 3) {
					album = info[2];
				}
				else if(info.length ==4) {
					album = info[2];
					year = info[3];
				}
				
				// create song object with above information
				Song sg = new Song(name,artist,album,year);
				//System.out.println(sg.toString());
				songArrayList.add(sg);
				//restart for next iter
				name ="";
				artist ="";
				album ="";
				year ="";
			}
			
			reader.close();
			}
			//System.out.println(obsList.size());
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		//sort arraylist(before obslist is populated
		Collections.sort(songArrayList,Song.sgComparator);
		for (int i = 0; i < songArrayList.size(); i++) {
			String songstring = (songArrayList.get(i).getName() + ", " + songArrayList.get(i).getArtist());
			obsList.add(songstring);
		}
		
		listView.setItems(obsList); 

		// select the first item
		listView.getSelectionModel().select(0);

		// set listener for the items
		listView
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				showSong(mainStage));
		
		mainStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			public void handle(WindowEvent we) {
		          System.out.println("Stage is closing");
					try {
						FileWriter fw = new FileWriter("songData.txt",false);
						for(int i=0; i < songArrayList.size(); i++) {
							
							fw.write(songArrayList.get(i).getName() + ", " + songArrayList.get(i).getArtist() + ", "
									+ songArrayList.get(i).getAlbum() + ", " + songArrayList.get(i).getYear() + "\n");

						}
						fw.close();
					}
						catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
		          
		          }
		      });

	}
	// The showSong method uses the Alert object to show the songs name, Artist, album, and year.
	//
	private void showSong(Stage mainStage) {                
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(mainStage);
		alert.setTitle("Song Info");
		alert.setHeaderText(
				"Selected song properties");

		int index = listView.getSelectionModel().getSelectedIndex();
		
		String content = "Songname: " + 
				(songArrayList.get(index).getName()) + 
		"\nArtist: " + (songArrayList.get(index).getArtist()) +
		"\nAlbum: " + (songArrayList.get(index).getAlbum()) +
		"\nYear: " + (songArrayList.get(index).getYear());

		alert.setContentText(content);
		alert.showAndWait();
	}
	
	private void showItemInputDialog(Stage mainStage) {                
		String item = listView.getSelectionModel().getSelectedItem();
		int index = listView.getSelectionModel().getSelectedIndex();

		TextInputDialog dialog = new TextInputDialog(item);
		dialog.initOwner(mainStage); dialog.setTitle("List Item");
		dialog.setHeaderText("Selected Item (Index: " + index + ")");
		dialog.setContentText("Enter name: ");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) { obsList.set(index, result.get()); }
	}
	
	//this will take the action of the addSong, editsong, deletesongbutton.
		public void buttonEvent(ActionEvent e) throws IOException {
			Button b = (Button)e.getSource();
			//The button clicked is the @FXMLaddSongButton. We make a dialog bog that is a textinputdialog, set a header, our 4 labels, and our 
			//4 text fields. We add this to a grid and then set the contents to the crid. If the user clicks yes
			if (b == addSongButton)
			{
				TextInputDialog dialog = new TextInputDialog();
				dialog.setHeaderText("Please enter your new sonng, you MUST enter a song name and Artist, "
						+ "but album name and year are optional");
				dialog.setResizable(true);
				Label songnamelabel = new Label("Enter song name: ");
				Label artistnamelabel = new Label("Enter artist name: ");
				Label albumlabel = new Label("(OPTIONAL)Enter album name: ");
				Label yearlabel = new Label("(OPTIONAL)Enter song year: ");
				TextField songtext = new TextField();
				TextField artisttext = new TextField();
				TextField albumtext = new TextField();
				TextField yeartext = new TextField();

				GridPane grid = new GridPane();
				grid.add(songnamelabel, 1, 1);
				grid.add(songtext, 2, 1);
				grid.add(artistnamelabel, 1, 2);
				grid.add(artisttext, 2, 2);
				grid.add(albumlabel, 1, 3);
				grid.add(albumtext, 2, 3);
				grid.add(yearlabel, 1, 4);
				grid.add(yeartext, 2, 4);
				dialog.getDialogPane().setContent(grid);
				
				
				
				// The optional object shows a pop up window for our dialog. We check to see if the button was pressed with isPresent(). From there
				// we create a new Song with our inputs. and then we add it to the obsList with .add, casting our objects to string.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
					Song addedSong = new Song(songtext.getText(), artisttext.getText(), albumtext.getText(), yeartext.getText());
					songArrayList.add(addedSong);
					Collections.sort(songArrayList,Song.sgComparator);
					for (int i = 0; i < songArrayList.size(); i++) {
						String songstring = (songArrayList.get(i).getName() + ", " + songArrayList.get(i).getArtist());
						if (!songstring.equals(obsList.get(i))) {
							obsList.add(i,songstring);
						}
					}

					FileWriter fw = new FileWriter("songData.txt", true);
					fw.write(addedSong.getName() + ", " + addedSong.getArtist() + ", " + addedSong.getAlbum() + ", " + addedSong.getYear() + "\n");
					fw.close();
				}
			}
			else if (b == deleteSongButton)
			{
				int index = listView.getSelectionModel().getSelectedIndex();
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Delete Song?");
				String message = "Are you sure you want to delete: " + (songArrayList.get(index).getName()) 
						+ " by " + (songArrayList.get(index).getArtist());
				alert.setContentText(message);
				
				Optional<ButtonType> result = alert.showAndWait();
				if((result.isPresent()) && (result.get() == ButtonType.OK)) {
					songArrayList.remove(index);
					obsList.remove(index);
					
				}
				//select next song as default if there is one
				if(songArrayList.size()>0 && songArrayList.size()-1 == index-1) {
					listView.getSelectionModel().select(index-1);
				}
				//otherwise select previous
				else if(songArrayList.size()>0) {
					listView.getSelectionModel().select(index);
				}
				//if it's empty list prevent selection
				else {
					//BUT HOW?!
				}
				
			}

			else if(b == editSongButton)
			{
			
			int editindex = listView.getSelectionModel().getSelectedIndex();
			String song = listView.getSelectionModel().getSelectedItem();
			
			TextInputDialog editdialog = new TextInputDialog();
			editdialog.setHeaderText("Please change anything you like, remember - you MUST enter a song name and Artist");
			editdialog.setResizable(true);
			Label songnamelabel = new Label("change song name:  ");
			Label artistnamelabel = new Label("change artists name: ");
			Label albumlabel = new Label("(OPTIONAL)change album name: ");
			Label yearlabel = new Label("(OPTIONAL)change song year: ");
			TextField songtext = new TextField(songArrayList.get(editindex).getName());
			TextField artisttext = new TextField(songArrayList.get(editindex).getArtist());
			TextField albumtext = new TextField(songArrayList.get(editindex).getAlbum());
			TextField yeartext = new TextField(songArrayList.get(editindex).getYear());;

			GridPane grid2 = new GridPane();
			grid2.add(songnamelabel, 1, 1);
			grid2.add(songtext, 2, 1);
			grid2.add(artistnamelabel, 1, 2);
			grid2.add(artisttext, 2, 2);
			grid2.add(albumlabel, 1, 3);
			grid2.add(albumtext, 2, 3);
			grid2.add(yearlabel, 1, 4);
			grid2.add(yeartext, 2, 4);
			editdialog.getDialogPane().setContent(grid2);
			
			
			Optional<String> editresult = editdialog.showAndWait();
			if(editresult.isPresent()) {
				Song changedSong = new Song(songtext.getText(), artisttext.getText(), albumtext.getText(), yeartext.getText());
				songArrayList.set(editindex, changedSong);
				//sort after edit is complete
				Collections.sort(songArrayList,Song.sgComparator);
				for (int i = 0; i < songArrayList.size(); i++) {
					obsList.set(i, songArrayList.get(i).getName() + ", " + songArrayList.get(i).getArtist());
				}
			}
				
				
			}
		
		}
	}





	
