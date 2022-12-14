package platform.dist.vo;

import lombok.Getter;
import lombok.Setter;
import wt.content.ApplicationData;

@Getter
@Setter
public class DistFileVO {

	private ApplicationData pdfFile = null;
	private ApplicationData dwgFile = null;
	private ApplicationData stpFile = null;
	private ApplicationData excelFile = null;
	private ApplicationData partPdfFile = null;
	private ApplicationData jpg2DFile = null;
	private ApplicationData jpg2DSmallFile = null;
	private ApplicationData jpg3DFile = null;
	private ApplicationData jpg3DSmallFile = null;

}
