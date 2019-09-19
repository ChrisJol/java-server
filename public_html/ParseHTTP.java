package htmlpackage;

public class ParseHTTP {

    public static void outputRequest(Socket client) {

        String line;
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(client.getInputStream())
        );
        while(true) {
            line = reader.readLine();
            System.out.println("> " + line);

            if(line.contains("END")) {
                break;
            }
        }
        outputLineBreak();
    }
}