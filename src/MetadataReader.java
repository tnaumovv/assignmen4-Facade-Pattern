import java.nio.file.Path;
import java.util.Locale;

public class MetadataReader {
    public MediaMetadata read(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("File name must not be null or blank.");
        }

        String cleanFileName = fileName.trim();
        Path inputPath = Path.of(cleanFileName);
        Path lastPathPart = inputPath.getFileName();
        if (lastPathPart == null) {
            throw new IllegalArgumentException("File name must include a file extension.");
        }

        String name = lastPathPart.toString();
        int dotPosition = name.lastIndexOf('.');
        if (dotPosition <= 0 || dotPosition == name.length() - 1) {
            throw new IllegalArgumentException("File name must include a file extension.");
        }

        String sourceFormat = name.substring(dotPosition + 1).toLowerCase(Locale.ROOT);
        return new MediaMetadata(cleanFileName, sourceFormat, 120, 4_500, 192, -4.0);
    }
}
