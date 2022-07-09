import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Filter {
	public static void main(String[] args) throws IOException {
		Path p = Paths.get("data.txt");
		Path TCN = Paths.get("TCN.txt");
		Path TCTL = Paths.get("TCTL.txt");
		try(BufferedReader reader = Files.newBufferedReader(p);
			BufferedWriter TCNWriter = Files.newBufferedWriter(TCN);
			BufferedWriter TCTLWriter = Files.newBufferedWriter(TCTL);
		) {
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					break;
				}
				else if (line.contains("TCN")) {
					TCNWriter.write(reader.readLine());
					TCNWriter.newLine();
				}
				else if (line.contains("TCTL")) {
					TCTLWriter.write(reader.readLine());
					TCTLWriter.newLine();
				}
			}
		}
	}
}
