import java.nio.file.Paths;
import java.util.stream.Stream;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.Files;
import java.util.function.BiPredicate;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.List;
import static java.lang.Integer.MAX_VALUE;
import static java.nio.file.FileVisitOption.FOLLOW_LINKS;

public class Filter {
	public static void main(String[] args) throws IOException {
		Path start = Paths.get(".");

		BiPredicate<Path, BasicFileAttributes> matcher = new BiPredicate<>() {
			@Override
			public boolean test(Path p, BasicFileAttributes b) {
				return !b.isDirectory() && p.getFileName().toString().startsWith("ALL_SC_");
			}
		};
		List<Path> dataFiles;
		try(Stream<Path> res = Files.find(start, MAX_VALUE, matcher, FOLLOW_LINKS)) {
			dataFiles = res.collect(Collectors.toList());
			System.out.println(dataFiles.size());
			for(Path p: dataFiles) {
				String TCNFileName = p.getFileName().toString().replaceFirst("ALL", "TCN");
				String TCTLFileName = p.getFileName().toString().replaceFirst("ALL", "TCTL");
				Path TCN = p.resolveSibling(TCNFileName);
				Path TCTL = p.resolveSibling(TCTLFileName);
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
		catch (Exception e) {
			System.err.println(e);
		}
	}
}
