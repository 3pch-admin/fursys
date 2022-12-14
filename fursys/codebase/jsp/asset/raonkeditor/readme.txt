﻿//////////////////////////////////////////////////////////////////////////////////////////////////////
// K Editor 설치안내
//////////////////////////////////////////////////////////////////////////////////////////////////////

* 설명
	K Editor는 클라이언트 모듈(Javascript)과 파일을 서버쪽에 저장해 주는 서버 모듈로 구성되어 있습니다.
	(서버 모듈은 .NET, JAVA 버전을 제공 하며, hander 하위 폴더에 위치하고 있습니다.)

* 설치규격
	- 지원 운영체제 : Microsoft Windows 2000 Server 이상, Linux, Unix 등
	- 지원 웹 언어 : JSP JDK 1.5 이상 (JDK 1.4 별도문의), ASP, ASP.NET 2.0 이상
	- 지원 웹 서버 : JDK 1.5 이상 (JDK 1.4 별도문의)을 사용하는 WAS, Microsoft .NET Framework 2.0 이상을 사용하는 IIS

* K Editor 폴더 설명
    - agent
	    K Editor Agent 모듈 설치파일을 포함하고 있습니다.
	- config	
		K Editor의 환경설정을 담당하는 raonkeditor.config.xml 을 포함하고 있습니다.
	- css
		K Editor에 필요한 스타일시트 파일을 포함하고 있습니다.
	- raonkeditordata
		저장경로에 대한 별도 설정이 없다면, 파일이 저장되는 ROOT 경로입니다.
	- handler
		파일을 서버에 저장하는 서버 모듈을 포함하고 있습니다. (.NET, JAVA 모듈)
	- images
		K Editor에 필요한 이미지 파일을 포함하고 있습니다.
	- js	
		K Editor에 필요한 자바스크립트 파일을 포함하고 있습니다.
	- sample
		K Editor의 기본 예제들을 포함하고 있습니다. (테스트 완료후 삭제 하셔도 됩니다.)

* 설치방법
	아래의 순서에 따라 설치하십시오.

	1. config\raonkeditor.config.xml 설정
		RaonKEditor > license > product_key, license_key, license_html5 에 발급받은 키를 설정합니다.

	2. K Editor Core 전체 폴더 업로드(하위폴더 및 파일포함)
		WEB서버와 WAS가 분리되어 있는 경우 handler, handler\JAVA, handler\.NET 폴더는 WAS서버에 업로드 합니다.
		나머지 폴더는 WEB서버에 설치합니다.(WEB서버와 경로를 맞춰 WAS에 설치합니다.)

	3. 웹 언어별 설정
		1) ASP, ASP.NET : 
			config\raonkeditor.config.xml 설정 : RaonKEditor > uploader_setting > develop_langage 에 NET 으로 설정합니다.
			(필요에 따라 handler_url을 설정합니다. ex: /handler/raonkhandler.ashx)

			handler\.NET 폴더의 dll 파일들을 해당 사이트 bin 폴더에 복사한 후, 첨부된 web.config의 내용을 사이트 web.config 에 적용합니다.

		2) JSP : 
			config\raonkeditor.config.xml 설정 : RaonKEditor > uploader_setting > develop_langage 에 JAVA 로 설정합니다.
			(필요에 따라 handler_url을 설정합니다. ex: /handler/raonkhandler.jsp)

			handler\JAVA 폴더의 jar 파일들을 해당 사이트 WEB-INF\lib 폴더에 복사합니다.
			아래 환경설정 및 jar 파일 변경시에는 서버를 재기동합니다.

			JEUS
				$JEUS_HOME/config/'hostname'/'hostname'_servlet_engine/webcommon.xml 파일에 아래 MimeType이 설정되어 있지 않으면 추가합니다.

				<mime-mapping>
					<extension>xml</extension>
					<mime-type>text/xml</mime-type>
				</mime-mapping>

			WebLogic
				$WebApplication_Root/WEB-INF/web.xml 파일에 아래 MimeType이 설정되어 있지 않으면 추가합니다.

				<mime-mapping>
					<extension>xml</extension>
					<mime-type>text/xml</mime-type>
				</mime-mapping>

			WebSphere
				WebSphere 관리자 페이지에서 아래 MimeType이 설정되어 있지 않으면 추가합니다.

				extension : xml
				mime-type : text/xml

			iPlanet
				$WebServer 설치 폴더/https-xxx/config/mime.conf 파일에 아래 MimeType이 설정되어 있지 않으면 추가합니다.

				type=text/xml     exts=xml

	4. 권한설정
		아래의 경로에 쓰기 권한을 추가합니다.

		1) 환경 설정 정보가 저장되는 파일
			ex) config\raonkeditor.config.xml

		2) 파일이 업로드 되는 임시 폴더
			ex) handler\temp (만일 SetTempPath로 다른경로로 설정했다면, 해당 폴더에 권한설정)

		3) 파일이 업로드 되는 기본 폴더
			ex) /raonkeditordata

* K Editor 서버 모듈 구성
	기본적으로 파일을 업로드하면, 기본 설치폴더/raonkeditordata 폴더에 파일이 업로드가 되며, WEB 서버와 WAS가 분리된 경우 WAS쪽 폴더(/raonkeditordata)에 업로드 됩니다.

	SetVirtualPath와 SetPhysicalPath 를 통하여 기본 저장 Root 경로를 설정해야 합니다.
	기본저장 Root 경로의 물리적 경로는 실제 존재하고 있는 경로여야 합니다.

* K Editor를 생성하는 방법
	1) <head>...</head> 태그 사이에 아래의 코드를 추가합니다.

		<script type="text/javascript" src="RAONKEditor설치폴더URL/js/raonkeditor.js">

	2) <body>...</body> 태그 사이에 업로드가 들어갈 위치에 업로드 생성 코드를 삽입합니다.

		<script type="text/javascript">
			var upload = new RAONKEditor({Id: "생성할 이름"});
		</script>

* 기타
	추가 문의사항이 있으시면, RAON K 기술지원팀 (raonk@raonwiz.com)으로 문의주시거나, http://www.raonk.com 의 제품문의 페이지로 문의주세요.





