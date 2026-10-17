public final class AcceptanceChecks {
    private AcceptanceChecks() {
    }

    public static void main(String[] args) {
        MediaConversionFacade facade = new MediaConversionFacade();

        checkSuccessfulConversion(facade.convert("sample.avi", "mp4"));
        checkCaseInsensitiveExtension(facade.convert("SAMPLE.AVI", "mp4"));
        checkInvalidInput("blank file name", () -> facade.convert("   ", "mp4"), "file name");
        checkInvalidInput("null file name", () -> facade.convert(null, "mp4"), "file name");
        checkInvalidInput("missing extension", () -> facade.convert("sample", "mp4"), "extension");
        checkInvalidInput("unsupported source format", () -> facade.convert("sample.mov", "mp4"), "source format");
        checkInvalidInput("unsupported target format", () -> facade.convert("sample.avi", "flv"), "target format");

        System.out.println("PASS: all acceptance checks");
    }

    private static void checkSuccessfulConversion(ConversionResult result) {
        checkEquals("sample.avi", result.fileName(), "file name");
        checkEquals("avi", result.sourceFormat(), "source format");
        checkEquals("mp4", result.targetFormat(), "target format");
        checkEquals("Xvid", result.decoderName(), "decoder");
        checkEquals("H.264", result.encoderName(), "encoder");
        checkEquals(120, result.durationSeconds(), "duration");
        checkEquals(2_500, result.videoBitrateKbps(), "video bitrate");
        checkEquals(160, result.audioBitrateKbps(), "audio bitrate");
        checkEquals(-1.0, result.audioPeakDb(), "normalized audio peak");
        checkEquals(39_900L, result.estimatedSizeKb(), "estimated output size");
        System.out.println("PASS: AVI to MP4 result values");
    }

    private static void checkCaseInsensitiveExtension(ConversionResult result) {
        checkEquals("avi", result.sourceFormat(), "case-insensitive source format");
        System.out.println("PASS: case-insensitive extension");
    }

    private static void checkInvalidInput(String name, Runnable conversion, String expectedMessage) {
        try {
            conversion.run();
            throw new AssertionError(name + " should have failed");
        } catch (IllegalArgumentException exception) {
            if (!exception.getMessage().toLowerCase().contains(expectedMessage)) {
                throw new AssertionError(name + " should mention '" + expectedMessage + "'", exception);
            }
        }
        System.out.println("PASS: " + name);
    }

    private static void checkEquals(Object expected, Object actual, String field) {
        if (!expected.equals(actual)) {
            throw new AssertionError(field + " expected " + expected + " but was " + actual);
        }
    }

    private static void checkEquals(double expected, double actual, String field) {
        if (Double.compare(expected, actual) != 0) {
            throw new AssertionError(field + " expected " + expected + " but was " + actual);
        }
    }
}
