package client;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        while (true) {
            try {
                Socket sock = new Socket();
                sock.connect(new InetSocketAddress("localhost", 3070));
                System.out.println("서버접속성공!");

                Scanner scan = new Scanner(System.in);
                System.out.print("전송할 파일명 입력 : ");

                String fileName = scan.next();
                File f = new File(fileName);


                //데이터를 전송하기 위한 스트림객체 생성하기
                DataOutputStream dos = new DataOutputStream(sock.getOutputStream());

                //제일먼저 파일명 보내기
                dos.writeUTF(f.getName());
                dos.flush();
                byte b[] = new byte[1024];
                int n = 0;

                //파일에서 읽어오기 위한 스트림객체 생성
                FileInputStream fis = new FileInputStream(fileName);
                long fileSize = 0;

                while ((n = fis.read(b)) != -1) {//파일에서 읽어와 b에 저장
                    dos.write(b, 0, n);//서버에 파일에서읽어온 데이터 보내기
                    fileSize += n;//파일크기 구하기
                }
                dos.close();//스트림닫기
                fis.close();//스트림닫기
                System.out.println(fileSize + "bytes 크기의 파일전송 완료!");
                sock.close();//소켓닫기
            } catch (UnknownHostException ue) {
                System.out.println(ue.getMessage());
            } catch (IOException ie) {
                System.out.println(ie.getMessage());
            }
        }
    }

}
