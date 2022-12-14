<%@ WebHandler Language="C#" Class="raonkhandler" %>

using System;
using System.Web;
using Com.Raonwiz.KEditor;
using Com.Raonwiz.KEditor.Common;
using Com.Raonwiz.KEditor.Util;

public class raonkhandler : IHttpHandler {

    RAONKHandler editor = null;
    
    public void ProcessRequest (HttpContext context) {
        
        editor = new RAONKHandler();

        char cPathChar = StaticVariables.cPathChar;
        string strRootFolder = context.Request.PhysicalPath;
        strRootFolder = strRootFolder.Substring(0, context.Request.PhysicalPath.LastIndexOf(cPathChar));
        strRootFolder = strRootFolder.Substring(0, strRootFolder.LastIndexOf(cPathChar));

        editor.settingVo.SetAllowFileExtension("gif, jpg, jpeg, png, bmp, wmv, asf, swf, avi, mpg, mpeg, mp4, txt, doc, docx, xls, xlsx, ppt, pptx, hwp, zip, pdf, flv, webm, ogv");
        
        //디버깅
        //2번째 파라미터의 Log Mode 설명
        //- C : 일반로그 출력, 3번째 파라미터에 로그파일 경로 설정
        //- L : Log4Net 모듈에 의한 로그 출력, 3번째 파라미터에 Log4Net.xml파일의 경로 설정 (기본적으로 배포되는 /handler/NET의 물리적 경로 설정)
        //editor.settingVo.SetDebugMode(true, "C", @"D:\log\");
        //editor.settingVo.SetDebugMode(true, "L", System.IO.Path.Combine(strRootFolder, "handler" + cPathChar + "NET" + cPathChar));

        ///////////////////////////////
        //    이벤트를 등록 처리     //
        ///////////////////////////////
        //업로드 전 이벤트 처리기 등록
        //editor.BeforeUploadEvent += new BeforeUploadEventDelegate(BeforeUploadEvent);

        //이미지 처리 이벤트 처리기 등록
        //editor.ImageEvent += new ImageEventDelegate(ImageEvent);
        
        //업로드 후 이벤트 처리기 등록
        //editor.AfterUploadEvent += new AfterUploadEventDelegate(AfterUploadEvent);

        ///////////////////////////////
        //       서버모듈 설정       //
        ///////////////////////////////

        //임시파일 물리적 경로설정
        //editor.settingVo.SetTempPath(@"D:\temp");
        
        //실제 업로드 할 기본경로 설정 (가상경로와 물리적 경로로 설정 가능)
        //폴더명 제일 뒤에 .과 공백이 있다면 제거하시고 설정해 주세요.(운영체제에서 지원되지 않는 문자열입니다.)

        editor.settingVo.SetVirtualPath("/raonkeditordata");

        //해당 설정은 PhysicalPath를 K Editor 제품폴더\raonkeditordata\ 를 저장 Root 경로로 설정하는 내용입니다.
        //editor.settingVo.SetPhysicalPath(System.IO.Path.Combine(strRootFolder, "raonkeditordata"));

        //editor.settingVo.SetAllowFileExtension("gif, jpg, jpeg, png, bmp, wmv, asf, swf, avi, mpg, mpeg, mp4, txt, doc, docx, xls, xlsx, ppt, pptx, hwp, zip, pdf, flv, webm, ogv");
        
        //editor.settingVo.SetNetworkCredentials("ID", "Password", @"\\Domain");

        //환경설정파일 물리적 폴더 (서버 환경설정 파일을 사용할 경우)
        //editor.settingVo.SetConfigPhysicalPath(@"D:\raonkeditordata\config");

        // ***************보안 설정 : 업로드 가능한 경로 설정 - 이외의 경로로 업로드 불가능***************
        string[] allowUploadDirectoryPath = { context.Request.MapPath("/") };
        editor.settingVo.SetAllowDirectoryPath(allowUploadDirectoryPath);
        
        // 실제 실행부
        editor.Process(context);

        // 메모리 해제
        editor.Dispose();
    }

    private void BeforeUploadEvent(EventVo eventVo)
    {
        //파일을 업로드하기 전에 발생하는 이벤트 입니다.
        //파일에 대한 저장 경로를 변경해야 하는 경우 사용합니다.

        //HttpContext context = eventVo.GetContext(); //Context Value
        //string strNewFileLocation = eventVo.GetNewFileLocation(); //NewFileLocation Value

        //eventVo.SetNewFileLocation(strNewFileLocation); //Change NewFileLocation Value

        //eventVo.SetCustomError("사용자오류");
    }

    private void ImageEvent(EventVo eventVo)
    {
        //이미지 파일을 업로드한 후에 발생하는 이벤트 입니다.
        //업로드된 파일을 변경하는 경우 사용됩니다.(Image API 처리)
        //경로가 변경된 경우 파일경로를 변경해야 합니다.(NewFileLocation)

        //HttpContext context = eventVo.GetContext(); //Context Value
        //string strNewFileLocation = eventVo.GetNewFileLocation(); //NewFileLocation Value

        //eventVo.SetNewFileLocation(strNewFileLocation); //Change NewFileLocation Value

        //이미지 파일 업로드시 bmp 파일이나 디지털 카메라로 찍은 고용량 이미지의 크기를 줄이고자 할 때 사용하는 옵션입니다.
        //기본값은 "빈값" 입니다.
        //값을 jpg로 지정하면 원본 이미지 파일을 jpg 파일로 변환합니다.
        //ImageConvertWidth 와 ImageConvertHeight 둘의 값이 빈값 또는 "0" 이고, ImageConvertFormat 의 값이 빈값이면 bmp 파일만 원본 크기의 jpg로 변환합니다. 
        //eventVo.SetImageConvertFormat("jpg");
        
        //이미지파일 업로드시 저장될 이미지의 너비를 변환 할 때 사용하는 옵션입니다.
        //기본값은 "0" 입니다.
        //ImageConvertWidth 가 빈값 또는 "0" 이고, ImageConvertWidth 의 값이 0 보다 큰 정수 일때 입력 값을 기준으로 원본 비율의 width 값을 계산하여 jpg로 변환합니다. 
        //eventVo.SetImageConvertWidth(0);
        
        //이미지파일 업로드시 저장될 이미지의 높이를 변환 할 때 사용하는 옵션입니다.
        //기본값은 "0" 입니다.
        //ImageConvertHeight 가 빈값 또는 "0" 이고, ImageConvertHeight 의 값이 0 보다 큰 정수 일때 입력 값을 기준으로 원본 비율의 height 값을 계산하여 jpg로 변환 합니다. 
        //eventVo.SetImageConvertHeight(0);
        
        //이미지 처리 관련 API
        try
        {
            //long lJpegQuality = 100; // JPG 품질 (1 ~ 100)

            //string strTempFilePath = string.Empty;
            //string strSourceFileFullPath = strNewFileLocation;

            //동일 폴더에 이미지 썸네일 생성하기
            //strTempFilePath = ImageApi.MakeThumbnail(strSourceFileFullPath, "", 200, 0, true, lJpegQuality);

            //특정위치에 이미지 썸네일 생성하기
            //string strTargetFileFullPath = @"c:\temp\test_thumb.jpg";
            //strTempFilePath = ImageApi.MakeThumbnailEX(strSourceFileFullPath, strTargetFileFullPath, 200, 0, false, lJpegQuality);

            //이미지 포멧 변경
            //strTempFilePath = ImageApi.ConvertImageFormat(strSourceFileFullPath, "", ImageFormatList.png, false, false, lJpegQuality);

            //이미지 크기 변환
            //ImageApi.ConvertImageSize(strSourceFileFullPath, 500, 30, lJpegQuality);

            //비율로 이미지 크기 변환
            //ImageApi.ConvertImageSizeByPercent(strSourceFileFullPath, 50, lJpegQuality);

            //이미지 회전
            //ImageApi.Rotate(strSourceFileFullPath, 90, lJpegQuality);

            //이미지 워터마크
            //string strWaterMarkFilePath = @"c:\temp\raonk_logo.png";
            //ImageApi.SetImageWaterMark(strSourceFileFullPath, strWaterMarkFilePath, "TOP", 10, "RIGHT", 10, 0, lJpegQuality);

            //텍스트 워터마크
            //TextWaterMarkVo txtWaterMarkVo = new TextWaterMarkVo("RAONK Upload", "굴림", 12, "#FF00FF");
            //ImageApi.SetTextWaterMark(strSourceFileFullPath, txtWaterMarkVo, "TOP", 10, "CENTER", 10, 0, 0, lJpegQuality);

            //이미지 크기
            //System.Drawing.Size size = ImageApi.GetImageSize(strSourceFileFullPath);
            //int _width = size.Width;
            //int _height = size.Height;

            //EXIF 추출 (Exif standard 2.2, JEITA CP-2451)
            //Com.Raonwiz.KUpload.Util.Exif.ExifEntity _exif = ImageApi.GetExifEntityData(strSourceFileFullPath);  

            //동일 폴더에 잘라낸 이미지 생성하기 (좌,우,상,하)
            //strTempFilePath = ImageApi.MakeCropImage(strSourceFileFullPath, "", 10, 10, 10, 10, true, lJpegQuality);

            //특정위치에 잘라낸 이미지 생성하기 (좌,우,상,하)
            //string strTargetFileFullPath = @"c:\temp\test_crop.jpg";
            //strTempFilePath = ImageApi.MakeCropImageEX(strSourceFileFullPath, strTargetFileFullPath, 10, 10, 10, 10, false, lJpegQuality);
        }
        catch (Exception ex)
        {
            string errorMsg = ex.Message;
        }
    }
    
    private void AfterUploadEvent(EventVo eventVo)
    {
        //파일을 업로드한 후에 발생하는 이벤트 입니다.
        //Image Tag의 src에 삽입될 url을 변경할 경우 사용됩니다. (ResponseFileServerPath)
        //ServerDomain, Parameter, Attribute를 설정할 수 있습니다.

        //HttpContext context = eventVo.GetContext(); //Context Value
        //string strNewFileLocation = eventVo.GetNewFileLocation(); //NewFileLocation Value
        //string strResponseFileServerPath = eventVo.GetResponseFileServerPath(); //ResponseFileServerPath Value

        //eventVo.SetResponseFileServerPath(strResponseFileServerPath); //Change ResponseFileServerPath Value

        //eventVo.SetServerDomain(""); //Image Tag의 src에 삽입될 url의 domain을 설정합니다. (/ 설정시 도메인 없이 Virtual Patht가 들어갑니다.) 예) eventVo.SetServerDomain("http://www.raonk.com");
        //eventVo.SetParameter(""); //Image Tag의 src에 삽입될 url에 추가할 Parameter를 설정합니다. 예) param1=1&param2=2
        //eventVo.SetAttribute(""); //Image Tag에 추가할 Attribute를 설정합니다.(제일 앞에 구분자를 삽입하여야 하며, Client에서 설정된 image_custom_property_delimiter 값(기본값:|)으로 구분되어야 합니다.) 예) |att1^1|att2^2
        //eventVo.SetCustomError("사용자오류");
    }
    
    public bool IsReusable {
        get {
            return false;
        }
    }

}