<%@page import="com.raonwiz.keditor.RAONKHandler"%><%@page import="com.raonwiz.keditor.event.*"%><%@page import="com.raonwiz.keditor.util.EventVo"%><%@page import="com.raonwiz.keditor.common.ImageApi"%><%@ page contentType="text/html;charset=utf-8"%><%
	//response.setHeader("Access-Control-Allow-Methods", "POST, GET");
	//response.setHeader("Access-Control-Allow-Headers", "content-type, RAONK-Encoded");
	//response.setHeader("Access-Control-Allow-Origin", "*");

	try {
		
	
	
	out.clear(); // Servlet으로 handler 작업을 하시려면 제거해주세요.

	request.setCharacterEncoding("UTF-8");
	
	RAONKHandler editor = new RAONKHandler();

	editor.settingVo.setAllowFileExtension("gif, jpg, jpeg, png, bmp, wmv, asf, swf, avi, mpg, mpeg, mp4, txt, doc, docx, xls, xlsx, ppt, pptx, hwp, zip, pdf, flv, webm, ogv");
	
	//디버깅
  	//2번째 파라미터의 Log Mode 설명
  	//- C : 일반로그 출력(System.out.println 로그 출력)
  	//- L : Log4j 모듈에 의한 로그 출력(/handler/JAVA 폴더의 log4j.properties 파일을 WEB-INF/classes에 적용)
  	//editor.settingVo.setDebugMode(true, "C");
  	//editor.settingVo.setDebugMode(true, "L");
	
  	///////////////////////////////
	//        이벤트를 등록 처리          //
	///////////////////////////////
	EventClass event = new EventClass();

	/*
	event.addBeforeUploadEventListener(new BeforeUploadEventListener() {
		public void beforeUploadEvent(EventVo eventVo) {
			//파일을 업로드하기 전에 발생하는 이벤트 입니다.
	        //파일에 대한 저장 경로를 변경해야 하는 경우 사용합니다.
	        
	        //HttpServletRequest request = eventVo.getRequest(); //Request Value
	        //HttpServletResponse response = eventVo.getResponse(); //Response Value
	        //String strNewFileLocation = eventVo.getNewFileLocation(); //NewFileLocation Value

	        //String[] aryParameterValue = eventVo.getEditor().getParameterValue("ParameterName"); //클라이언트에서 AddFormData를 이용하여 추가된 파라미터를 얻습니다.
	        
	        //eventVo.setNewFileLocation(strNewFileLocation); //Change NewFileLocation Value
			
	        //eventVo.setCustomError("사용자오류");
		}
  	});
	*/
	
	/*
	event.addImageEventListener(new ImageEventListener() {
		public void imageEvent(EventVo eventVo) {
			//이미지 파일을 업로드한 후에 발생하는 이벤트 입니다.
	        //업로드된 파일을 변경하는 경우 사용됩니다.(Image API 처리)
	        //경로가 변경된 경우 파일경로를 변경해야 합니다.(NewFileLocation)
	        
	        //HttpServletRequest request = eventVo.getRequest(); //Request Value
	        //HttpServletResponse response = eventVo.getResponse(); //Response Value
	        //String strNewFileLocation = eventVo.getNewFileLocation(); //NewFileLocation Value
	        
	        //String[] aryParameterValue = eventVo.getEditor().getParameterValue("ParameterName"); //클라이언트에서 AddFormData를 이용하여 추가된 파라미터를 얻습니다.

	        //이미지 파일 업로드시 bmp 파일이나 디지털 카메라로 찍은 고용량 이미지의 크기를 줄이고자 할 때 사용하는 옵션입니다.
        	//기본값은 "빈값" 입니다.
        	//값을 jpg로 지정하면 원본 이미지 파일을 jpg 파일로 변환합니다.
        	//ImageConvertWidth 와 ImageConvertHeight 둘의 값이 빈값 또는 "0" 이고, ImageConvertFormat 의 값이 빈값이면 bmp 파일만 원본 크기의 jpg로 변환합니다. 
        	//SMB Protocol 설정 후 업로드시 사용이 불가능합니다.
        	//eventVo.setImageConvertFormat("jpg");
        
        	//이미지파일 업로드시 저장될 이미지의 너비를 변환 할 때 사용하는 옵션입니다.
        	//기본값은 "0" 입니다.
        	//ImageConvertWidth 가 빈값 또는 "0" 이고, ImageConvertWidth 의 값이 0 보다 큰 정수 일때 입력 값을 기준으로 원본 비율의 width 값을 계산하여 jpg로 변환합니다. 
        	//SMB Protocol 설정 후 업로드시 사용이 불가능합니다.
        	//eventVo.setImageConvertWidth(0);
        
        	//이미지파일 업로드시 저장될 이미지의 높이를 변환 할 때 사용하는 옵션입니다.
        	//기본값은 "0" 입니다.
        	//ImageConvertHeight 가 빈값 또는 "0" 이고, ImageConvertHeight 의 값이 0 보다 큰 정수 일때 입력 값을 기준으로 원본 비율의 height 값을 계산하여 jpg로 변환 합니다. 
        	//SMB Protocol 설정 후 업로드시 사용이 불가능합니다.
        	//eventVo.setImageConvertHeight(0);

	        //이미지 처리 관련 API (SMB Protocol 설정 후 업로드시 사용이 불가능합니다.)
	        try {
				//float fJpegQuality = 100; // JPG 품질 (1 ~ 100)
				//boolean bAntialiasing = false; // 이미지 안티앨리어싱 활성화
				
              	//String strTempFilePath = "";                
              	//String strSourceFileFullPath = strNewFileLocation;
              
              	//동일 폴더에 이미지 썸네일 생성하기
              	//strTempFilePath = ImageApi.makeThumbnail(strSourceFileFullPath, "", 200, 0, true, fJpegQuality, bAntialiasing);

              	//특정위치에 이미지 썸네일 생성하기
              	//String targetFileFullPath = "c:\\temp\\test_thumb.jpg";
              	//strTempFilePath = ImageApi.makeThumbnailEX(strSourceFileFullPath, targetFileFullPath, 200, 0, false, fJpegQuality, bAntialiasing);

              	//이미지 포멧 변경
              	//strTempFilePath = ImageApi.convertImageFormat(strSourceFileFullPath, "", "png", false, false, fJpegQuality, bAntialiasing);

              	//이미지 크기 변환
              	//ImageApi.convertImageSize(strSourceFileFullPath, 500, 30, fJpegQuality, bAntialiasing);

              	//비율로 이미지 크기 변환
              	//ImageApi.convertImageSizeByPercent(strSourceFileFullPath, 50, fJpegQuality, bAntialiasing);

              	//이미지 회전
              	//ImageApi.rotate(strSourceFileFullPath, 90, fJpegQuality);

              	//이미지 워터마크
              	//String strWaterMarkFilePath = "c:\\temp\\raonk_logo.png";
              	//ImageApi.setImageWaterMark(strSourceFileFullPath, strWaterMarkFilePath, "TOP", 10, "RIGHT", 10, 0, fJpegQuality);

              	//텍스트 워터마크
              	//com.raonwiz.keditor.util.TextWaterMarkVo txtWaterMarkVo = new com.raonwiz.keditor.util.TextWaterMarkVo("RAONK Editor", "굴림", 12, "#FF00FF");                
              	//ImageApi.setTextWaterMark(strSourceFileFullPath, txtWaterMarkVo, "TOP", 10, "CENTER", 10, 0, 0, fJpegQuality);

              	//이미지 크기
              	//java.awt.Dimension size = ImageApi.getImageSize(strSourceFileFullPath);
              	//int _width = size.width;
              	//int _height = size.height;
				
				//EXIF 추출 (Exif standard 2.2, JEITA CP-2451)
       			//jdk 1.6 이상에서만 사용가능합니다.
				//기능 활성화를 원하시면 1.6버전으로 컴파일된 jar를 고객센터로 요청하십시오.
				//com.raonwiz.keditor.common.ImageExif exif = new com.raonwiz.keditor.common.ImageExif();
              	//com.raonwiz.keditor.common.exif.ExifEntity exifData = exif.getExifData(strSourceFileFullPath);

				//동일 폴더에 잘라낸 이미지 생성하기 (좌,우,상,하)
                //strTempFilePath = ImageApi.makeCropImage(strSourceFileFullPath, "", 10, 10, 10, 10, true, fJpegQuality);

                //특정위치에 잘라낸 이미지 생성하기 (좌,우,상,하)
                //String targetFileFullPath = "c:\\temp\\test_crop.jpg";
                //strTempFilePath = ImageApi.makeCropImageEX(strSourceFileFullPath, targetFileFullPath, 10, 10, 10, 10, false, fJpegQuality);
          	} catch (Exception ex) {
              	String errorMsg = ex.getMessage();
          	}
		}
  	});
	*/
	
	/*
	event.addAfterUploadEventListener(new AfterUploadEventListener() {
		public void afterUploadEvent(EventVo eventVo) {
			//파일을 업로드한 후에 발생하는 이벤트 입니다.
	        //Image Tag의 src에 삽입될 url을 변경할 경우 사용됩니다. (ResponseFileServerPath)
	        //ServerDomain, Parameter, Attribute를 설정할 수 있습니다.
	        
	        //HttpServletRequest request = eventVo.getRequest(); //Request Value
	        //HttpServletResponse response = eventVo.getResponse(); //Response Value
			//String strNewFileLocation = eventVo.getNewFileLocation(); //NewFileLocation Value
	        //String strResponseFileServerPath = eventVo.getResponseFileServerPath(); //ResponseFileServerPath Value
	        
	        //String[] aryParameterValue = eventVo.getEditor().getParameterValue("ParameterName"); //클라이언트에서 AddFormData를 이용하여 추가된 파라미터를 얻습니다.

	        //eventVo.setResponseFileServerPath(strResponseFileServerPath); //Change ResponseFileServerPath Value
	        
	        //eventVo.setServerDomain(""); //Image Tag의 src에 삽입될 url의 domain을 설정합니다. (/ 설정시 도메인 없이 Virtual Patht가 들어갑니다.) 예) eventVo.SetServerDomain("http://www.raonk.com");
        	//eventVo.setParameter(""); //Image Tag의 src에 삽입될 url에 추가할 Parameter를 설정합니다. 예) param1=1&param2=2
        	//eventVo.setAttribute(""); //Image Tag에 추가할 Attribute를 설정합니다.(제일 앞에 구분자를 삽입하여야 하며, Client에서 설정된 image_custom_property_delimiter 값(기본값:|)으로 구분되어야 합니다.) 예) |att1=1|att2=2
        	//eventVo.setCustomError("사용자오류");
	    }
  	});
	*/
	
	///////////////////////////////
	//         서버모듈 설정              //
	///////////////////////////////
	
	//임시파일 물리적 경로설정
  	//editor.settingVo.setTempPath("D:\temp");
	
	//실제 업로드 할 기본경로 설정 (가상경로와 물리적 경로로 설정 가능)
  	//폴더명 제일 뒤에 .과 공백이 있다면 제거하시고 설정해 주세요.(운영체제에서 지원되지 않는 문자열입니다.)
  
  	editor.settingVo.setVirtualPath("/Windchill/netmarkets/raonkeditordata");
  
  	//해당 설정은 PhysicalPath를 RAONK Editor 제품폴더\raonkeditordata\ 를 저장 Root 경로로 설정하는 내용입니다.
  	//String strPathChar = com.raonwiz.keditor.util.StaticVariables.strPathChar;
  	//String strRootFolder = request.getServletPath();
	//strRootFolder = strRootFolder.substring(0,strRootFolder.lastIndexOf("/"));
	//strRootFolder = request.getSession().getServletContext().getRealPath(strRootFolder.substring(0,strRootFolder.lastIndexOf("/")));
	//editor.settingVo.setPhysicalPath(strRootFolder + strPathChar + "raonkeditordata");
	
	///////////////////////////////
	//      SMB Protocol 설정        //
	///////////////////////////////
	
	//1. SMB Protocol 연결 설정 
	//SMB Protocol 사용을 위한 연결설정을 해야 합니다. 
	//editor.settingVo.setNtlmAuthentication("smb://SMB Domain 또는 IP", "ID", "Password"); 
	
	//2. 파일 업로드시 사용되는 Temp Path 설정
	//파일 업로드시 사용되는 Temp Path를 SMB Protocol의 경로로 설정해야 합니다.
	//editor.settingVo.setTempPath("smb://SMB Domain 또는 IP/temp ");
	
	//3. 파일이 업로드 될 최종 Path 설정
	//파일이 업로드 될 최종 Path를 SMB Protocol의 경로로 설정해야 합니다.
	//editor.settingVo.setPhysicalPath("smb://SMB Domain 또는 IP/savePath");
	
	///////////////////////////////
	
	//환경설정파일 물리적 폴더 (서버 환경설정 파일을 사용할 경우)
  	//editor.settingVo.setConfigPhysicalPath("D:\\raonkeditordata\\config");

	//서버 구성정보중 Context Path가 있다며, 아래와 같이 설정해주세요. (SetVirtualPath 사용시만 필요)
	//editor.settingVo.setContextPath("Context Path");
	
	// ***************보안 설정 : 업로드 가능한 경로 설정 - 이외의 경로로 업로드 불가능***************
	String[] allowUploadDirectoryPath 
		= { request.getSession().getServletContext().getRealPath("D:\\ptc\\Windchill_12.0\\Windchill\\codebase\\netmarkets\\raonkeditordata") };
	editor.settingVo.setAllowDirectoryPath(allowUploadDirectoryPath);

  	String result = editor.Process(request, response, application, event);
  
  	if(!result.equals("")) {
		out.print(result);
	}
  
	// Servlet으로 handler 작업을 하시려면 다음과 같이 작성해 주세요.
	// Servlet으로 구성하실 때 해당 Function의 Return Type은 void로 선언 후 return 되는 값은 반드시 없어야합니다.
	/*
	// 만일 getServletContext()가 undefined 이면 request.getSession().getServletContext(); 으로 하시면 됩니다.
	ServletContext application = getServletContext();
	
	String result = "";
	try {
		result = editor.Process(request, response, application, event);
	} catch (Exception e) {
		e.printStackTrace();
	}
		
	if(!result.equals("")) {
		response.setContentType("text/html");
		ServletOutputStream out = response.getOutputStream();
		out.print(result);
		out.close();
	}
	*/
	
	
	
	} catch(Exception e) {
		e.printStackTrace();
	}
	
%>