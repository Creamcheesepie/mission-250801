import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class App {
    private Scanner sc = new Scanner(System.in);
    private String command;
    private WiseSaying[] wiseSayings = new WiseSaying[100];
    private WiseSaying wiseSaying;
    private int count = 1;

    public void wiseSayingApp(){
        while(true){
            commandReader();
            if(command.equals("종료")) break;
            if(command.equals("등록")){
                wiseSaying = wiseSayingReader();
                wiseSayingSaver();
                wiseSayingFileSaver();
                setWiseSayingLastIndexSaver();
            }
            if(command.equals("목록")) setWiseSayingListPrinter();
            if(command.startsWith("삭제")){
                int targetNo = commandNumberReader();
                if(targetNo == -1) continue;
                wiseSaying = findWiseSayingByNumber(targetNo);
                setWiseSayingDelete();
            }
            if(command.startsWith("수정")){
                int targetNo = commandNumberReader();
                if(targetNo == -1) continue;
                wiseSaying = findWiseSayingByNumber(targetNo);
                setWiseSayingEditer();
            }
            if(command.equals("빌드")){
                buildJsonFile();
            }
        }
    }

    private void buildJsonFile() {
        try {
            File json = new File("data.json");

            if (!json.exists()){
                json.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(json);

            StringBuilder sb = new StringBuilder();

            sb.append("[\n");
            int commaCounter = 1;
            for(WiseSaying ws : wiseSayings){
                if(ws == null) continue;
                sb.append(
                    "\t{\n" +
                    "\t\t\"id\": " + ws.getNum() + ",\n" +
                    "\t\t\"content\": " + ws.getContent() + ",\n" +
                    "\t\t\"author\": "+ ws.getAuthor() + "\n"
                );
                if(commaCounter++ != count -1) sb.append("\t},\n");
                else sb.append("\t}\n");
            }
            sb.append("]");

            String writeString = sb.toString();
            fos.write(writeString.getBytes());
            fos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void commandReader(){
        System.out.print("명령) ");
        command = sc.nextLine();
    }

    private WiseSaying wiseSayingReader() {
        System.out.print("명언 : ");
        String content = sc.nextLine();
        System.out.print("작가 : ");
        String author = sc.nextLine();
        return new WiseSaying(count++,content,author);
    }

    private void wiseSayingSaver(){
        wiseSayings[count - 1] = wiseSaying;
        System.out.println(wiseSaying.getNum() + "번 명언이 등록되었습니다. ");
    }

    private void wiseSayingFileSaver(){
        try {
            File json = new File(wiseSaying.getNum() + ".json");

            if (!json.exists()){
                json.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(json);

            String writeString = "{\n" +
                    "\t\"id\": " + wiseSaying.getNum() + ",\n" +
                    "\t\"content\": " + wiseSaying.getContent() + ",\n" +
                    "\t\"author\": "+ wiseSaying.getAuthor() + "\n" +
                    "}";
            fos.write(writeString.getBytes());
            fos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void setWiseSayingLastIndexSaver(){
        try {
            File lastid = new File("lastId.txt");
            if(!lastid.exists()){
                lastid.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(lastid);

            String writeString = count - 1 +"";
            fos.write(writeString.getBytes());
            fos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }



    private void setWiseSayingListPrinter(){
        for(int i = wiseSayings.length -1 ; i>-1; i--){
            WiseSaying ws = wiseSayings[i];
            if(ws == null) continue;
            if(ws.isDeleted()) System.out.println(ws.getNum() + "번 명언은 존재하지 않습니다.");
            else System.out.println(ws.toString());
        }
    }

    private int commandNumberReader(){
        String[] targetNoStrings = command.split("=");
        if(targetNoStrings.length < 2) {
            System.out.println("처리하실 명언의 번호를 입력해주세요.");
            return -1;
        }
        return Integer.parseInt(targetNoStrings[1]);
    }

    private WiseSaying findWiseSayingByNumber(int number){
        if(wiseSayings[number - 1] == null || wiseSayings[number -1].isDeleted()){
            System.out.println(number + "번 명언은 존재하지 않습니다.");
            return null;
        } else{
            return wiseSayings[number - 1];
        }
    }


    private void setWiseSayingDelete(){
        wiseSaying.setDeleted(true);
        System.out.println(wiseSaying.getNum() + "번 명언이 삭제되었습니다.");
    }

    private void setWiseSayingEditer(){
            System.out.println("명언(기존) : " + wiseSaying.getContent());
            System.out.print("명언 : ");
            String newContent = sc.nextLine();
            System.out.println("작가(기존) : " + wiseSaying.getAuthor());
            System.out.print("작가 : ");
            String newAuthor = sc.nextLine();
            wiseSaying.setContent(newContent);
            wiseSaying.setAuthor(newAuthor);
    }
}
