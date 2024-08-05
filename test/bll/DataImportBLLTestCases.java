package bll;


import org.junit.jupiter.api.Test;

import dal.FileImporterStub;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DataImportBLLTestCases {
	FileImporterStub fileImporterStub = new FileImporterStub();
    DataImporterBLLTest dataImporterBLLTest = new DataImporterBLLTest(fileImporterStub);

    @Test
    void importData_shouldCallImportFile() {
        // Arrange
        File selectedFile = new File("testFile.txt");

        // Act
        dataImporterBLLTest.importData(selectedFile);

        // Assert
        assertTrue(((FileImporterStub) fileImporterStub).isImportFileCalled());
        assertTrue(fileImporterStub.getImportedFile() == selectedFile);
    }
}


