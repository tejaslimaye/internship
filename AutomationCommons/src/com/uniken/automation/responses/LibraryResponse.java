package com.uniken.automation.responses;
import java.util.ArrayList;
import com.uniken.automation.beans.LibraryBean;
public class LibraryResponse extends Response {

		private ArrayList<LibraryBean> library_details;

		public ArrayList<LibraryBean> getLibrary_details() {
			return library_details;
		}

		public void setLibrary_details(ArrayList<LibraryBean> library_details) {
			this.library_details = library_details;
		}

		
}
