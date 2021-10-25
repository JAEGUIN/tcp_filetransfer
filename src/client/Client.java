package client;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import static java.lang.Thread.sleep;

public class Client {

	public static void file_detector() {
		while (true) {
			try {
				sleep(3000);

				System.out.println("파일 검색중...");
				File dir = new File("C:\\client");
				File files[] = dir.listFiles();

				if (files.length != 0) {
					System.out.println("새로운 파일이 있습니다.");
					file_sender();
				}
			} catch (InterruptedException | IOException e) {
			}
		}
	}

	public static void file_sender() throws IOException {
		File dir = new File("C:\\client");
		File files[] = dir.listFiles();

		for (int i = 0; i < files.length; i++) {

			Socket sock = new Socket();
			sock.connect(new InetSocketAddress("localhost", 3070));
			System.out.println("서버접속성공!");

			// 데이터를 전송하기 위한 스트림객체 생성하기
			DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
			String filenames = files[i].getName();
			System.out.println("전송할 파일 이름 : " + filenames);

			// 제일먼저 파일명 보내기
			dos.writeUTF(filenames);
			dos.flush();
			System.out.println(files[i] + " 전송 시작");

			int n = 0;
			byte b[] = new byte[1024];

			// 파일에서 읽어오기 위한 스트림객체 생성
			FileInputStream fis = new FileInputStream(files[i]);

			long fileSize = 0;
			while ((n = fis.read(b)) != -1) {// 파일에서 읽어와 b에 저장
				dos.write(b, 0, n);// 서버에 파일에서읽어온 데이터 보내기
				fileSize += n;// 파일크기 구하기
			}
			dos.close();// 스트림닫기
			fis.close();// 스트림닫기
			System.out.println(fileSize + "bytes 크기의 파일전송 완료!\n");

			sock.close();// 소켓닫기
			files[i].delete();
		}
	}

	public static void main(String[] args) {
		file_detector();
	}
}
