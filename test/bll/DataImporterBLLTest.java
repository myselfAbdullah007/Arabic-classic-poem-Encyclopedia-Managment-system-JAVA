package bll;

import java.io.File;

import businesslogic.DataImportInterface;
import dal.FileImporterStub;

public class DataImporterBLLTest implements DataImportInterface {

	private FileImporterStub imp;

	public DataImporterBLLTest(FileImporterStub dal) {
		this.imp = dal;
	}

	public void importData(File selectedFile) {
		imp.importFile(selectedFile);
	}
}
