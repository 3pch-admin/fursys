package platform.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.ChannelSftp.LsEntrySelector;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;

public class SFTPUtils {

	private Session session = null;

	private Channel channel = null;

	private ChannelSftp channelSftp = null;

	public void initialization(String host, String userName, String password, String key) {
		this.initialization(host, userName, password, 22, key);
	}

	public void initialization(String host, String userName, String password, int port, String key) {
		JSch jSch = new JSch();
		try {
			if (key != null) {// 키가 존재한다면
				jSch.addIdentity(key);
			}

			session = jSch.getSession(userName, host, port);

			if (key == null) {// 키가 없다면
				session.setPassword(password);
			}

			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();

			channel = session.openChannel("sftp");
			channel.connect();
		} catch (JSchException e) {
			e.printStackTrace();
		}

		channelSftp = (ChannelSftp) channel;

	}

	public void disconnection() {
		channelSftp.quit();
		session.disconnect();
	}

	public void sendFiles(File f) throws Exception {
		String result = "";
		String distHost = "";
		String distPort = "22";
		String distUser = "ptc";
		String distPrivateKey = "/data/fursys_win_local/sftp/key/fursysPrivatekey";
		String distPutRootPath = "/data/ptc/pdm/distribute";
		String distWorkRootPath = "/data/ptc/distribute";

		this.initialization(distHost, distUser, null, Integer.parseInt(distPort), distPrivateKey);

		/*
		 * 작업 폴더, 파일 위치
		 */
		String foldName = f.getName();
		String zipFileName = f.getName() + ".zip";
		String zipFileFullName = distWorkRootPath + "/" + zipFileName;
		String zipPutFileFullName = distPutRootPath + "/" + zipFileName;

		File orgZipFile = new File(zipFileFullName);

		if (orgZipFile.exists()) {
			orgZipFile.delete();
		}
		/*
		 * zip 파일 생성
		 */
		ZipFile zipFile = new ZipFile(zipFileFullName);
		ZipParameters zipParameters = new ZipParameters();
		zipFile.removeFile(zipFileFullName);
		zipFile.addFolder(f, zipParameters);

		/*
		 * zip upload
		 */

		File _zipFile = new File(zipFileFullName);
		this.upload(distPutRootPath, _zipFile);

		/*
		 * zip 파일 unzip
		 */

		String sDate = DateUtils.getTimeToString(new Date(), "yyyy-MM-dd");

		this.mkdir(distPutRootPath, sDate);

		String disDir = distPutRootPath + "/" + sDate + "/" + foldName;

		result = this.sendCommand("rm -rf " + disDir);

		result = this.sendCommand("unzip " + zipPutFileFullName + " -d" + distPutRootPath + "/" + sDate);

		/*
		 * zip 파일 삭제
		 */
		result = this.sendCommand("rm " + zipPutFileFullName);

		File _orgZipFile = new File(zipFileFullName);
		if (_orgZipFile.exists()) {
			_orgZipFile.delete();
		}

		if (f.exists()) {
			f.delete();
		}

		this.disconnection();

	}

	public void upload(String dir, File file) {

		System.out.println("upload dir : " + dir);
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			channelSftp.cd(dir);
			channelSftp.put(in, file.getName());
		} catch (SftpException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void mkdir(String dir, String mkdirName) {

		try {
			channelSftp.cd(dir);

			 Vector<LsEntry> filelist = new Vector<LsEntry>();

			channelSftp.ls(dir, new LsEntrySelector() {

				@Override
				public int select(LsEntry entry) {
					final String filename = entry.getFilename();

					if (filename.equals(mkdirName)) {
						filelist.add(entry);
					} else {
						return CONTINUE;
					}

					return CONTINUE;
				}
			});

			if (filelist.isEmpty()) {
				channelSftp.mkdir(mkdirName);
			}

		} catch (SftpException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String sendCommand(String command) {
		StringBuilder outputBuffer = new StringBuilder();

		try {
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			InputStream commandOutput = channel.getInputStream();
			channel.connect();
			int readByte = commandOutput.read();

			while (readByte != 0xffffffff) {
				outputBuffer.append((char) readByte);
				readByte = commandOutput.read();
			}

			channel.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (JSchException e) {
			e.printStackTrace();
			return null;
		}

		return outputBuffer.toString();
	}

}
