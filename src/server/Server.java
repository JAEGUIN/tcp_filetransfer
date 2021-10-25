package server;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void file_receiver() {
		ServerSocket server = null;

		while (true) {
			try {
				server = new ServerSocket(3070);
				System.out.println("클라이언트 접속대기중...");
				Socket sock = server.accept();
				System.out.println("클라이언트 접속!");
				// 클라이언트가 보내온 데이터를 읽어오기위한 스트림객체 생성
				DataInputStream dis = new DataInputStream(sock.getInputStream());
				// 제일먼저 보내온 파일명 읽어오기
				String fileName = dis.readUTF();
				// 전송된 파일명으로 파일생성하기
				FileOutputStream fos = new FileOutputStream("c:\\server\\" + fileName);
				byte[] b = new byte[1024];
				int n = 0;
				long fileSize = 0;
				while ((n = dis.read(b)) != -1) {// 전송된 데이터 읽어와 b배열에 저장
					// b배열의 데이터를 파일로 저장
					fos.write(b, 0, n);
					fileSize += n;// 전송된 파일크기 구하기
				}
				// 스트림닫기
				fos.close();
				dis.close();

				// 소켓닫기
				sock.close();
				server.close();
				System.out.println("c:\\server폴더에 " + fileSize + " bytes 크기의 파일수신 완료!");
			} catch (IOException ie) {
			}
		}
	}

	public static void main(String[] args) {
		file_receiver();
	}
}