package cn.fjcpc.common.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;

public class FtpUtil {

	public static final String Ftp_Host = "192.168.211.130";
	public static final int Ftp_Port = 21;
	private static final String Ftp_Username = "ftpuser";
	private static final String Ftp_password = "ftpuser";
	private static final String Ftp_basePath = "/home/ftpuser/";

	FTPClient client = null;

	/**
	 * 文件上传
	 * @param filePath   文件存放路径
	 * @param remoteFileName  上传后文件名称
	 * @param in         文件输入流
	 * @return
	 */
	public static boolean uploadFile(String filePath,String remoteFileName,InputStream in){
		FTPClient client = new FTPClient();
		try {
			client.connect(Ftp_Host, Ftp_Port);
			client.login(Ftp_Username, Ftp_password);
			//4、指定图片上传的方式为二进制，即字节流
			client.setFileType(FTP.BINARY_FILE_TYPE);
			client.enterLocalPassiveMode();
			//6、指定上传的目录     默认目录 是当前ftpuser用户的家目录
			boolean flag = client.changeWorkingDirectory(Ftp_basePath+filePath);
			//如果切换目录失败，则创建指定的目录
			if(!flag){
				//创建目录失败，则可能是存在没有创建的父目录
				if(!client.makeDirectory(Ftp_basePath+filePath)){
					String tempPath = Ftp_basePath;
					String[] split = filePath.split("/");
					for (String string : split) {
						if(null!=string && !"".equals(string)){
							tempPath = tempPath+"/"+string;
							if(!client.changeWorkingDirectory(tempPath)){
								if(client.makeDirectory(tempPath)){
									if(!client.changeWorkingDirectory(tempPath)){
										return false;
									}
								}else{
									return false;
								}
							}
						}else{
							continue;
						}
					}
				}else{
					//创建成功，则直接切换到指定的目录
					if(!client.changeWorkingDirectory(Ftp_basePath+filePath)){
						return false;
					}
				}
			}
			boolean result = client.storeFile(remoteFileName, in);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				if(client.logout()){
					client.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream in=new FileInputStream(new File("D:/图片diagram/00004.jpg"));
		boolean flag = FtpUtil.uploadFile("111/222", "a.jpg", in);
		System.out.println(flag);
	}

}