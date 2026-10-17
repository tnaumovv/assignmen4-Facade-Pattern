import java.util.Locale;
import java.util.Set;

public class CodecFactory {
    private static final Set<String> SUPPORTED_SOURCE_FORMATS = Set.of("avi", "mkv", "mp4");
    private static final Set<String> SUPPORTED_TARGET_FORMATS = Set.of("mp4", "webm");

    public CodecPair select(String sourceFormat, String targetFormat) {
        String source = sourceFormat == null ? "" : sourceFormat.toLowerCase(Locale.ROOT);
        String target = targetFormat == null ? "" : targetFormat.toLowerCase(Locale.ROOT);

        if (!SUPPORTED_SOURCE_FORMATS.contains(source)) {
            throw new IllegalArgumentException("Unsupported source format: " + sourceFormat);
        }
        if (!SUPPORTED_TARGET_FORMATS.contains(target)) {
            throw new IllegalArgumentException("Unsupported target format: " + targetFormat);
        }

        String decoderName = source.equals("avi") ? "Xvid" : "H.264";
        String encoderName = target.equals("mp4") ? "H.264" : "VP9";
        int targetVideoBitrateKbps = target.equals("mp4") ? 2_500 : 1_800;

        return new CodecPair(decoderName, encoderName, targetVideoBitrateKbps, target);
    }
}
