package businesslogic;

import java.io.File;

import dataacess.FacadeDalInterface;

public class DataimportBLL implements DataImportInterface {

	private FacadeDalInterface imp;

	public DataimportBLL(FacadeDalInterface dal) {
		this.imp = dal;
	}

	public void importData(File selectedFile) {
		imp.importFile(selectedFile);
	}
}
