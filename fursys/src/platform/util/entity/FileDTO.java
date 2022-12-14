package platform.util.entity;

import java.io.InputStream;

public class FileDTO {

	private String fileName;
	private InputStream inputStream;
	long fileSize;

	private String partNumber;
	private String partRev;
	private String color;
	private String erpCode;
	private String finish_code;
	private String material_code;

	public FileDTO(String fileName, InputStream is, long fileSize, String partNumber, String partRev, String color,
			String erpCode, String finish_code, String material_code) {
		this.fileName = fileName;
		this.inputStream = is;
		this.fileSize = fileSize;
		this.partNumber = partNumber;
		this.partRev = partRev;
		this.color = color;
		this.erpCode = erpCode;
		this.finish_code = finish_code;
		this.material_code = material_code;
	}

	public String getErpCode() {
		return erpCode;
	}

	public String getFinish_code() {
		return finish_code;
	}

	public String getMaterial_code() {
		return material_code;
	}

	public String getFileName() {
		return fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public String getPartRev() {
		return partRev;
	}

	public String getColor() {
		return color;
	}
}
